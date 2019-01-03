package com.github.dddpaul.marathon.plugin.auth.entities;

import lombok.Data;

@Data
public class Permission {
    private String role;
    private String path;
}
