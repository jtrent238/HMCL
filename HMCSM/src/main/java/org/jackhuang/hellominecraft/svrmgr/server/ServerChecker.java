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
package org.jackhuang.hellominecraft.svrmgr.server;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;
import org.jackhuang.hellominecraft.HMCLog;

/**
 *
 * @author huangyuhui
 */
public class ServerChecker {

    public static boolean isServerJar(File f) {
        ZipFile file;
        try {
            file = new ZipFile(f);
        } catch (IOException ex) {
            HMCLog.warn("", ex);
            return false;
        }
        if (file.getEntry("org/bukkit/craftbukkit/Main.class") != null)
            return true;
        if (file.getEntry("net/minecraft/server/MinecraftServer.class") != null)
            return true;
        return false;
    }

}
