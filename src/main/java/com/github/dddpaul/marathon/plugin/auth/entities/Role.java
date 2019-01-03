package com.github.dddpaul.marathon.plugin.auth.entities;

import lombok.Getter;
import mesosphere.marathon.plugin.auth.AuthorizedAction;

import java.util.List;

@Getter
public class Role {
    private List<Action> actions;

    public boolean contains(Action action) {
        return actions.contains(action);
    }

    public boolean contains(AuthorizedAction<?> action) {
        return contains(Action.byAction(action));
    }

}
