package com.github.dddpaul.marathon.plugin.auth.checkers;

import com.github.dddpaul.marathon.plugin.auth.entities.JavaIdentity;
import com.github.dddpaul.marathon.plugin.auth.entities.User;
import mesosphere.marathon.plugin.auth.Identity;

import java.util.Base64;

import static com.github.dddpaul.marathon.plugin.auth.checkers.CheckerRegistry.SHA1;
import static org.apache.commons.codec.digest.DigestUtils.sha1;

public class SHA1Checker implements PasswordChecker {

    @Override
    public Identity check(User user, String password) {
        if (password == null) {
            return null;
        }
        String crypt = SHA1.getPrefix() + Base64.getEncoder().encodeToString(sha1(password));
        return crypt.equals(user.getPassword())
                ? new JavaIdentity(user.getName())
                : null;
    }
}
