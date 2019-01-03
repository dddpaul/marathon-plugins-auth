package com.github.dddpaul.marathon.plugin.fileauth.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dddpaul.marathon.plugin.fileauth.entities.Permission;
import com.github.dddpaul.marathon.plugin.fileauth.entities.Role;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizerConfigurationHolder {

    @JsonProperty("configuration")
    private AuthorizerConfiguration configuration;

    public AuthorizerConfiguration getConfiguration() {
        return configuration;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AuthorizerConfiguration {

        @JsonProperty("roles")
        private Map<String, Role> roles;

        @JsonProperty("permissions")
        private Map<String, List<Permission>> permissions;

        public Map<String, Role> getRoles() {
            return roles;
        }

        public Map<String, List<Permission>> getPermissions() {
            return permissions;
        }
    }
}
