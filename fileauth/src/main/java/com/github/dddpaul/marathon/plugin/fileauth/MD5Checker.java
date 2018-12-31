package com.github.dddpaul.marathon.plugin.fileauth;

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class MD5Checker {

    private static final MD5Checker INSTANCE = new MD5Checker();

    public static MD5Checker getInstance() {
        return INSTANCE;
    }

    private Map<String, String> accounts = new HashMap<>();
    private String prefix = "$apr1$";

    public MD5Checker() {
        accounts.put("abc", "$apr1$iE9/K1ue$J81SB0ojysREtBVlRXLoa/");
    }

    public boolean check(String username, String password) {
        if (!accounts.containsKey(username)) {
            return false;
        }
        String passwd = username + ":" + accounts.get(username);
        String salt = StringUtils.substringBeforeLast(accounts.get(username), "$");
        String crypt = Md5Crypt.md5Crypt(password.getBytes(), salt, prefix);
        return passwd.equals(String.join(":", username, crypt));
    }
}
