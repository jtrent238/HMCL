/*
 * Copyright 2013 huangyuhui <huanghongxun2008@126.com>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.
 */
package org.jackhuang.hellominecraft.launcher.launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import org.jackhuang.hellominecraft.C;
import org.jackhuang.hellominecraft.HMCLog;
import org.jackhuang.hellominecraft.tasks.download.FileDownloadTask;
import org.jackhuang.hellominecraft.utils.system.IOUtils;
import org.tukaani.xz.XZInputStream;

/**
 *
 * @author huangyuhui
 */
public class LibraryDownloadTask extends FileDownloadTask {

    GameLauncher.DownloadLibraryJob job;

    public LibraryDownloadTask(GameLauncher.DownloadLibraryJob job) {
        super();
        this.job = job;
    }

    @Override
    public boolean executeTask() {
        try {
            File packFile = new File(job.path.getParentFile(), job.path.getName() + ".pack.xz");
            if (job.name.contains("typesafe") && download(new URL(job.url + ".pack.xz"), packFile)) {
                unpackLibrary(job.path, packFile);
                packFile.delete();
                return true;
            } else {
                if (job.name.startsWith("net.minecraftforge:forge:")) {
                    String[] s = job.name.split(":");
                    if (s.length == 3)
                        job.url = "http://files.minecraftforge.net/maven/net/minecraftforge/forge/" + s[2] + "/forge-" + s[2] + "-universal.jar";
                }
                if (job.name.startsWith("com.mumfrey:liteloader:")) {
                    String[] s = job.name.split(":");
                    if (s.length == 3 && s[2].length() > 3)
                        job.url = "http://dl.liteloader.com/versions/com/mumfrey/liteloader/" + s[2].substring(0, s[2].length() - 3) + "/liteloader-" + s[2] + ".jar";
                }
                return download(new URL(job.url), job.path);
            }
        } catch (Exception ex) {
            setFailReason(ex);
            return false;
        }
    }
    
    boolean download(URL url, File filePath) {
        this.url = url;
        this.filePath = filePath;
        return super.executeTask();
    }

    public static void unpackLibrary(File output, File input)
            throws IOException {
        HMCLog.log("Unpacking " + input);
        if (output.exists())
            output.delete();

        byte[] decompressed = IOUtils.readFully(new XZInputStream(new FileInputStream(input)));

        String end = new String(decompressed, decompressed.length - 4, 4);
        if (!end.equals("SIGN")) {
            HMCLog.log("Unpacking failed, signature missing " + end);
            return;
        }

        int x = decompressed.length;
        int len = decompressed[(x - 8)] & 0xFF | (decompressed[(x - 7)] & 0xFF) << 8 | (decompressed[(x - 6)] & 0xFF) << 16 | (decompressed[(x - 5)] & 0xFF) << 24;

        File temp = File.createTempFile("art", ".pack");
        HMCLog.log("  Signed");
        HMCLog.log("  Checksum Length: " + len);
        HMCLog.log("  Total Length:    " + (decompressed.length - len - 8));
        HMCLog.log("  Temp File:       " + temp.getAbsolutePath());

        byte[] checksums = Arrays.copyOfRange(decompressed, decompressed.length - len - 8, decompressed.length - 8);

        try (OutputStream out = new FileOutputStream(temp)) {
            out.write(decompressed, 0, decompressed.length - len - 8);
        }
        decompressed = null;
        System.gc();

        try (FileOutputStream jarBytes = new FileOutputStream(output); JarOutputStream jos = new JarOutputStream(jarBytes)) {

            Pack200.newUnpacker().unpack(temp, jos);

            JarEntry checksumsFile = new JarEntry("checksums.sha1");
            checksumsFile.setTime(0L);
            jos.putNextEntry(checksumsFile);
            jos.write(checksums);
            jos.closeEntry();
        }
        temp.delete();
    }

    @Override
    public String getInfo() {
        return C.i18n("download") + ": " + job.name;
    }

}
