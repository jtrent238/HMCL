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
package org.jackhuang.hellominecraft.launcher.utils.auth;

import org.jackhuang.hellominecraft.C;
import org.jackhuang.hellominecraft.utils.StrUtils;
import org.jackhuang.hellominecraft.utils.code.DigestUtils;

/**
 *
 * @author huangyuhui
 */
public final class OfflineAuthenticator extends IAuthenticator {

    public OfflineAuthenticator(String clientToken) {
        super(clientToken);
    }

    @Override
    public UserProfileProvider login(LoginInfo info) {
        UserProfileProvider result = new UserProfileProvider();
        result.setSuccess(StrUtils.isNotBlank(info.username));
        result.setUserName(info.username);
        String uuid = getUUIDFromUserName(info.username);
        result.setSession(uuid);
        result.setUserId(uuid);
        result.setAccessToken(uuid);
        result.setUserType("Legacy");
        result.setErrorReason(C.i18n("login.no_Player007"));
        return result;
    }

    public static String getUUIDFromUserName(String str) {
        return DigestUtils.md5Hex(str);
    }

    @Override
    public String getName() {
        return C.i18n("login.methods.offline");
    }

    @Override
    public boolean isHidePasswordBox() {
        return true;
    }

    @Override
    public UserProfileProvider loginBySettings() {
        return null;
    }

    @Override
    public void logout() {
    }

}
