package com.github.dddpaul.marathon.plugin.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dddpaul.marathon.plugin.auth.checkers.CheckerRegistry;
import com.github.dddpaul.marathon.plugin.auth.conf.AuthenticatorConfigurationHolder;
import com.github.dddpaul.marathon.plugin.auth.conf.AuthenticatorConfigurationHolder.AuthenticatorConfiguration;
import mesosphere.marathon.plugin.auth.Identity;
import mesosphere.marathon.plugin.plugin.PluginConfiguration;
import play.api.libs.json.JsObject;

import java.io.IOException;
import java.util.Optional;

public class Authenticator extends BasicAuthenticator implements PluginConfiguration {

    private AuthenticatorConfiguration configuration;

    @Override
    public void initialize(scala.collection.immutable.Map<String, Object> marathonInfo, JsObject json) {
        logger.info("Authenticator initialize has been called with: " + json);
        ObjectMapper mapper = new ObjectMapper();
        try {
            configuration = mapper.readValue(json.toString(), AuthenticatorConfigurationHolder.class).getConfiguration();
        } catch (IOException e) {
            logger.error("Plugin authenticator configuration parsing has been failed", e);
            configuration = new AuthenticatorConfiguration();
        }
    }

    @Override
    protected Identity doAuth(String username, String password) {
        return Optional.ofNullable(configuration.getUsers().get(username))
                .map(user -> CheckerRegistry.get(user.getPassword()).check(user, password))
                .orElse(null);
    }
}
