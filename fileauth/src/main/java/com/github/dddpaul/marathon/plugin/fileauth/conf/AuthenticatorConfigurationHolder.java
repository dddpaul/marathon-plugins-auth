package com.github.dddpaul.marathon.plugin.fileauth.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dddpaul.marathon.plugin.fileauth.entities.User;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticatorConfigurationHolder {

    @JsonProperty("configuration")
    private AuthenticatorConfiguration configuration;

    public AuthenticatorConfiguration getConfiguration() {
        return configuration;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AuthenticatorConfiguration {

        @JsonProperty("users")
        private Map<String, User> users;

        public Map<String, User> getUsers() {
            return users;
        }
    }
}
