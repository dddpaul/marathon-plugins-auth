package com.github.dddpaul.marathon.plugin.auth.entities;

import lombok.RequiredArgsConstructor;
import mesosphere.marathon.plugin.auth.*;

import java.util.stream.Stream;

/**
 * Enumeration for handling AuthorizedActions more easily in Java.
 */
@RequiredArgsConstructor
public enum Action {

    CreateGroup(CreateGroup$.MODULE$),
    UpdateGroup(UpdateGroup$.MODULE$),
    DeleteGroup(DeleteGroup$.MODULE$),
    ViewGroup(ViewGroup$.MODULE$),
    CreateRunSpec(CreateRunSpec$.MODULE$),
    UpdateRunSpec(UpdateRunSpec$.MODULE$),
    DeleteRunSpec(DeleteRunSpec$.MODULE$),
    ViewRunSpec(ViewRunSpec$.MODULE$),
    CreateResource(CreateResource$.MODULE$),
    UpdateResource(UpdateResource$.MODULE$),
    DeleteResource(DeleteResource$.MODULE$),
    ViewResource(ViewResource$.MODULE$);

    private final AuthorizedAction<?> action;

    @SuppressWarnings("unchecked")
    public <Resource> AuthorizedAction<Resource> getAction() {
        return (AuthorizedAction<Resource>) action;
    }

    public static Action byAction(AuthorizedAction<?> action) {
        return Stream.of(values())
                .filter(a -> action.equals(a.getAction()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Action: " + action));
    }
}