package com.github.dddpaul.marathon.plugin.fileauth.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticatorConfigurationHolder {

    @JsonProperty("configuration")
    private AuthenticatorConfiguration configuration;

    public AuthenticatorConfiguration getConfiguration() {
        return configuration;
    }
}
