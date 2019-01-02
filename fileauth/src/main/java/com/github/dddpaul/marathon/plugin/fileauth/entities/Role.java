package com.github.dddpaul.marathon.plugin.fileauth.entities;

import com.github.dddpaul.marathon.plugin.auth.Action;

import java.util.List;

public class Role {
    private List<Action> actions;

    public List<Action> getActions() {
        return actions;
    }
}
