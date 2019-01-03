package com.github.dddpaul.marathon.plugin.auth.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class User {
    private String name;
    private String password;
}
