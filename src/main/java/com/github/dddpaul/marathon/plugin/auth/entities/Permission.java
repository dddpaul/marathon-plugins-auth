package com.github.dddpaul.marathon.plugin.auth.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mesosphere.marathon.plugin.auth.AuthorizedAction;
import mesosphere.marathon.state.AppDefinition;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Getter
public class Permission {

    @JsonProperty("role")
    private String roleName;

    private String path;

    private Pattern pattern;

    public Permission setPath(String path) {
        this.path = path;
        if (pattern == null) {
            try {
                pattern = Pattern.compile(path);
            } catch (PatternSyntaxException ignored) {
            }
        }
        return this;
    }

    /**
     * Returns <code>true</code> if <code>role</code> has specified by <code>action</code> access to <code>resource</code>
     */
    public <Resource> boolean check(Role role, AuthorizedAction<?> action, Resource resource) {
        if (role.contains(action)) {
            if (resource instanceof AppDefinition) {
                AppDefinition app = (AppDefinition) resource;
                if (app.id().toString().startsWith(path)) {
                    return true;
                }
                if (pattern != null) {
                    return pattern.matcher(app.id().toString()).matches();
                }
            }
        }
        return false;
    }
}
