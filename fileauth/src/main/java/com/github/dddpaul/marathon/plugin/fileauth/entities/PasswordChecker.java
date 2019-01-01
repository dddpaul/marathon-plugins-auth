package com.github.dddpaul.marathon.plugin.fileauth.entities;

import com.github.dddpaul.marathon.plugin.auth.JavaIdentity;
import mesosphere.marathon.plugin.auth.Identity;

import java.util.Objects;

@FunctionalInterface
public interface PasswordChecker {

    Identity check(User user, String password);

    static PasswordChecker Default() {
        return (user, password) -> user != null && Objects.equals(password, user.getPassword())
                ? new JavaIdentity(user.getUser())
                : null;
    }
}
