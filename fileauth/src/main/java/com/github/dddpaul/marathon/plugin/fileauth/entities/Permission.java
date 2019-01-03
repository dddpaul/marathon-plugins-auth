package com.github.dddpaul.marathon.plugin.fileauth.entities;

import lombok.Data;

@Data
public class Permission {
    private String role;
    private String path;
}
