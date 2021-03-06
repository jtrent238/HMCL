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
package org.jackhuang.hellominecraft.svrmgr.installer.cauldron;

import java.io.File;

/**
 *
 * @author huangyuhui
 */
public class MinecraftLibrary {

    public String url, formatted = null, name;
    //public boolean serverreq=true, clientreq=true;
    public String[] checksums;

    public void init() {
        String str = name;
        String[] s = str.split(":");
        str = s[0];
        str = str.replace('.', File.separatorChar);
        str += File.separator + s[1] + File.separator + s[2]
                + File.separator + s[1] + '-' + s[2] + ".jar";
        formatted = str;
    }
}
