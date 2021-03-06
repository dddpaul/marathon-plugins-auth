package com.github.dddpaul.marathon.plugin.auth.checkers;

import com.github.dddpaul.marathon.plugin.auth.entities.Principal;
import com.github.dddpaul.marathon.plugin.auth.entities.User;
import mesosphere.marathon.plugin.auth.Identity;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;

import static com.github.dddpaul.marathon.plugin.auth.checkers.CheckerRegistry.MD5;

public class MD5Checker implements PasswordChecker {

    @Override
    public Identity check(User user, String password) {
        if (password == null) {
            return null;
        }
        String salt = StringUtils.substringBeforeLast(user.getPassword(), "$");
        String crypt = Md5Crypt.md5Crypt(password.getBytes(), salt, MD5.getPrefix());
        return crypt.equals(user.getPassword())
                ? new Principal(user.getName())
                : null;
    }
}
