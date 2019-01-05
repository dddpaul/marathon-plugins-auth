package com.github.dddpaul.marathon.plugin.auth.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mesosphere.marathon.plugin.auth.AuthorizedAction;
import mesosphere.marathon.state.AppDefinition;
import mesosphere.marathon.state.Group;
import mesosphere.marathon.state.PathId;

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
            PathId pathId;
            if (resource instanceof AppDefinition) {
                pathId = ((AppDefinition) resource).id();
            } else if (resource instanceof Group) {
                pathId = ((Group) resource).id();
            } else if ("mesosphere.marathon.plugin.auth.AuthorizedResource$Plugins$".equals(resource.getClass().getName())) {
                // This type of resource has no path and it's unreachable from Java
                return true;
            } else {
                System.out.println(resource.getClass().toString());
                throw new RuntimeException("Unsupported resource: " + resource);
            }
            if (pathId.toString().startsWith(path)) {
                return true;
            }
            if (pattern != null) {
                return pattern.matcher(pathId.toString()).matches();
            }
        }
        return false;
    }
}
