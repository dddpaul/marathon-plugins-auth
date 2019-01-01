package com.github.dddpaul.marathon.plugin.fileauth.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dddpaul.marathon.plugin.fileauth.entities.User;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticatorConfiguration {

    @JsonProperty("users")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}
