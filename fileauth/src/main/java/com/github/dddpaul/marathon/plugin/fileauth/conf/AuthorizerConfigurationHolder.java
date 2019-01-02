package com.github.dddpaul.marathon.plugin.fileauth.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dddpaul.marathon.plugin.fileauth.entities.AclUser;
import com.github.dddpaul.marathon.plugin.fileauth.entities.User;

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

        @JsonProperty("users")
        private Map<String, AclUser> users;

        public Map<String, AclUser> getUsers() {
            return users;
        }
    }
}
