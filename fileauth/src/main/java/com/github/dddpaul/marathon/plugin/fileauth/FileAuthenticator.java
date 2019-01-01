package com.github.dddpaul.marathon.plugin.fileauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dddpaul.marathon.plugin.auth.BasicAuthenticator;
import com.github.dddpaul.marathon.plugin.fileauth.conf.AuthenticatorConfigurationHolder;
import com.github.dddpaul.marathon.plugin.fileauth.conf.AuthenticatorConfigurationHolder.AuthenticatorConfiguration;
import com.github.dddpaul.marathon.plugin.fileauth.entities.PasswordChecker;
import com.github.dddpaul.marathon.plugin.fileauth.entities.User;
import mesosphere.marathon.plugin.auth.Identity;
import mesosphere.marathon.plugin.plugin.PluginConfiguration;
import play.api.libs.json.JsObject;

import java.io.IOException;

public class FileAuthenticator extends BasicAuthenticator implements PluginConfiguration {

    private AuthenticatorConfiguration configuration;

    @Override
    public void initialize(scala.collection.immutable.Map<String, Object> marathonInfo, JsObject json) {
        logger.info("Authenticator initialize has been called with: " + json);
        ObjectMapper mapper = new ObjectMapper();
        try {
            configuration = mapper.readValue(json.toString(), AuthenticatorConfigurationHolder.class).getConfiguration();
        } catch (IOException e) {
            logger.error("Plugin configuration parsing has been failed", e);
            configuration = new AuthenticatorConfiguration();
        }
    }

    @Override
    protected Identity doAuth(String username, String password) {
        User user = configuration.getUsers().get(username);
        return PasswordChecker.Default().check(user, password);
    }
}
