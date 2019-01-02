package com.github.dddpaul.marathon.plugin.auth;

import mesosphere.marathon.plugin.auth.*;

/**
 * Enumeration for handling AuthorizedActions more easily in Java.
 */
public enum Action {

    CreateGroup(CreateGroup$.MODULE$),
    UpdateGroup(UpdateGroup$.MODULE$),
    DeleteGroup(DeleteGroup$.MODULE$),
    ViewGroup(ViewGroup$.MODULE$),
    CreateApp(CreateRunSpec$.MODULE$),
    UpdateApp(UpdateRunSpec$.MODULE$),
    DeleteApp(DeleteRunSpec$.MODULE$),
    ViewApp(ViewRunSpec$.MODULE$),
    CreateResource(CreateResource$.MODULE$),
    UpdateResource(UpdateResource$.MODULE$),
    DeleteResource(DeleteResource$.MODULE$),
    ViewResource(ViewResource$.MODULE$);

    private final AuthorizedAction<?> action;

    Action(AuthorizedAction<?> action) {
        this.action = action;
    }

    public AuthorizedAction<?> getAction() {
        return action;
    }

    public static Action byAction(AuthorizedAction<?> action) {
        for (Action a : values()) {
            if (a.action.equals(action)) {
                return a;
            }
        }
        throw new IllegalArgumentException("Unknown Action: " + action);
    }
}