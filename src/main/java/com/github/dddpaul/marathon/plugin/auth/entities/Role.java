package com.github.dddpaul.marathon.plugin.auth.entities;

import lombok.Getter;

import java.util.List;

@Getter
public class Role {
    private List<Action> actions;
}
