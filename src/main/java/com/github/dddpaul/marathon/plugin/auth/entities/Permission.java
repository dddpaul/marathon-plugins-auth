package com.github.dddpaul.marathon.plugin.auth.entities;

import lombok.Getter;

@Getter
public class Permission {
    private String role;
    private String path;
}
