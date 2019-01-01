package com.github.dddpaul.marathon.plugin.fileauth.checkers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.dddpaul.marathon.plugin.auth.JavaIdentity;
import com.github.dddpaul.marathon.plugin.fileauth.entities.User;
import mesosphere.marathon.plugin.auth.Identity;

public class BcryptChecker implements PasswordChecker {

    @Override
    public Identity check(User user, String password) {
        return BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified
                ? new JavaIdentity(user.getUser())
                : null;
    }
}
