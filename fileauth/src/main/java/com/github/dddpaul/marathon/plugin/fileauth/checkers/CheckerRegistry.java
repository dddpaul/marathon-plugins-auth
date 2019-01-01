package com.github.dddpaul.marathon.plugin.fileauth.checkers;

import org.apache.commons.lang3.StringUtils;

public enum CheckerRegistry {

    DEFAULT("", PasswordChecker.Default()),
    MD5("$apr1$", new MD5Checker()),
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
        if (StringUtils.startsWith(password, MD5.getPrefix())) {
            return MD5.getChecker();
        }
        if (StringUtils.startsWith(password, SHA1.getPrefix())) {
            return SHA1.getChecker();
        }
        return DEFAULT.getChecker();
    }
}
