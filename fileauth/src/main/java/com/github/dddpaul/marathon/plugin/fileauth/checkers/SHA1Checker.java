package com.github.dddpaul.marathon.plugin.fileauth.checkers;

import com.github.dddpaul.marathon.plugin.fileauth.entities.User;
import mesosphere.marathon.plugin.auth.Identity;

public class SHA1Checker implements PasswordChecker {

    private static String PREFIX = "{SHA}";

    @Override
    public Identity check(User user, String password) {
        return null;
    }
}
