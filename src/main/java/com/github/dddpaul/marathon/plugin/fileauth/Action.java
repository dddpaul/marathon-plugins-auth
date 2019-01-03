package com.github.dddpaul.marathon.plugin.fileauth;

import mesosphere.marathon.plugin.auth.*;

import java.util.stream.Stream;

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
        return Stream.of(values())
                .filter(a -> action.equals(a.getAction()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Action: " + action));
    }
}