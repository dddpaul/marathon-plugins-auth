package com.github.dddpaul.marathon.plugin.auth.checkers;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

/**
 * See <a href="https://httpd.apache.org/docs/2.4/misc/password_encryptions.html">Password Formats</a>
 * for implementation details
 */
public enum CheckerRegistry {

    MD5("$apr1$", new MD5Checker()),
    BCRYPT("$2y$", new BcryptChecker()),
    SHA1("{SHA}", new SHA1Checker());

    private String prefix;
    private PasswordChecker checker;

    public String getPrefix() {
        return prefix;
    }

    public PasswordChecker getChecker() {
        return checker;
    }

    CheckerRegistry(String prefix, PasswordChecker checker) {
        this.prefix = prefix;
        this.checker = checker;
    }

    public static PasswordChecker get(String password) {
        return Stream.of(values())
                .filter(r -> StringUtils.startsWith(password, r.getPrefix()))
                .findFirst()
                .map(CheckerRegistry::getChecker)
                .orElse(PasswordChecker.Default());
    }
}
