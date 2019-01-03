package com.github.dddpaul.marathon.plugin.auth.entities;

import lombok.Data;

@Data
public class User extends JavaIdentity {
    private String password;
}
