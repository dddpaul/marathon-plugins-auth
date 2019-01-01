package com.github.dddpaul.marathon.plugin.fileauth.entities;

import com.github.dddpaul.marathon.plugin.auth.JavaIdentity;
import mesosphere.marathon.plugin.auth.Identity;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;

public class MD5Checker implements PasswordChecker {

    private static String PREFIX = "$apr1$";

    @Override
    public Identity check(User user, String password) {
        if (!user.getPassword().startsWith(PREFIX)) {
            return null;
        }

        String salt = StringUtils.substringBeforeLast(user.getPassword(), "$");
        String crypt = Md5Crypt.md5Crypt(password.getBytes(), salt, PREFIX);
        return crypt.equals(user.getPassword())
                ? new JavaIdentity(user.getUser())
                : null;
    }
}
