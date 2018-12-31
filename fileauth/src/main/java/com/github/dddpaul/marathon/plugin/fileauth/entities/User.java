package com.github.dddpaul.marathon.plugin.fileauth.entities;

import lombok.Data;

@Data
public class User {
    private String login;
    private String password;
}
