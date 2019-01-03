package com.github.dddpaul.marathon.plugin.auth.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mesosphere.marathon.plugin.auth.AuthorizedAction;
import mesosphere.marathon.state.AppDefinition;

@Getter
public class Permission {

    @JsonProperty("role")
    private String roleName;

    private String path;

    /**
     * Returns <code>true</code> if <code>role</code> has specified by <code>action</code> access to <code>resource</code>
     */
    public <Resource> boolean check(Role role, AuthorizedAction<?> action, Resource resource) {
        if (role.contains(action)) {
            if (resource instanceof AppDefinition) {
                AppDefinition app = (AppDefinition) resource;
                return app.id().toString().startsWith(path);
            }
        }

        return false;
    }
}
