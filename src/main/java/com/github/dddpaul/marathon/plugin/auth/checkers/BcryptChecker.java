package com.github.dddpaul.marathon.plugin.auth.checkers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.dddpaul.marathon.plugin.auth.entities.Principal;
import com.github.dddpaul.marathon.plugin.auth.entities.User;
import mesosphere.marathon.plugin.auth.Identity;

public class BcryptChecker implements PasswordChecker {

    @Override
    public Identity check(User user, String password) {
        if (password == null) {
            return null;
        }
        return BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified
                ? new Principal(user.getName())
                : null;
    }
}
