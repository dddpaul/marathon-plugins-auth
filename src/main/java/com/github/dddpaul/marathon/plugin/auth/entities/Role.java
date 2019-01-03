package com.github.dddpaul.marathon.plugin.auth.entities;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    private List<Action> actions;
}
