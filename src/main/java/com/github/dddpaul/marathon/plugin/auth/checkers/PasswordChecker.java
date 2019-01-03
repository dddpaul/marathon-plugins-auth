package com.github.dddpaul.marathon.plugin.auth.checkers;

import com.github.dddpaul.marathon.plugin.auth.entities.Principal;
import com.github.dddpaul.marathon.plugin.auth.entities.User;
import mesosphere.marathon.plugin.auth.Identity;

import java.util.Objects;

@FunctionalInterface
public interface PasswordChecker {

    Identity check(User user, String password);

    static PasswordChecker Default() {
        return (user, password) -> user != null && Objects.equals(password, user.getPassword())
                ? new Principal(user.getName())
                : null;
    }
}
