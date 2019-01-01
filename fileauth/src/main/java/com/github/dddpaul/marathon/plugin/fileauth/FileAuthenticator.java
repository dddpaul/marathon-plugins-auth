package com.github.dddpaul.marathon.plugin.fileauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dddpaul.marathon.plugin.auth.BasicAuthenticator;
import com.github.dddpaul.marathon.plugin.auth.JavaIdentity;
import com.github.dddpaul.marathon.plugin.fileauth.conf.AuthenticatorConfiguration;
import com.github.dddpaul.marathon.plugin.fileauth.conf.AuthenticatorConfigurationHolder;
import com.github.dddpaul.marathon.plugin.fileauth.entities.User;
import mesosphere.marathon.plugin.auth.Identity;
import mesosphere.marathon.plugin.plugin.PluginConfiguration;
import play.api.libs.json.JsObject;

import java.io.IOException;

public class FileAuthenticator extends BasicAuthenticator implements PluginConfiguration {

    private AuthenticatorConfiguration configuration;

    @Override
    public void initialize(scala.collection.immutable.Map<String, Object> marathonInfo, JsObject configuration) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.configuration = mapper.readValue(configuration.toString(), AuthenticatorConfigurationHolder.class).getConfiguration();
        } catch (IOException e) {
            logger.error("Plugin configuration parsing has been failed", e);
            this.configuration = new AuthenticatorConfiguration();
        }
    }

    @Override
    protected Identity doAuth(String username, String password) {
        if (configuration.getUsers().contains(new User(username, password))) {
            return new JavaIdentity(username);
        } else {
            return null;
        }
    }
}
