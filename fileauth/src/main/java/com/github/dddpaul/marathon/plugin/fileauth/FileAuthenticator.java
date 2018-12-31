package com.github.dddpaul.marathon.plugin.fileauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dddpaul.marathon.plugin.auth.BasicAuthenticator;
import mesosphere.marathon.plugin.auth.Identity;
import mesosphere.marathon.plugin.plugin.PluginConfiguration;
import play.api.libs.json.JsObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileAuthenticator extends BasicAuthenticator implements PluginConfiguration {

    private Map<String, Object> configuration;

    @Override
    public void initialize(scala.collection.immutable.Map<String, Object> marathonInfo, JsObject configuration) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.configuration = mapper.readValue(configuration.toString(), Map.class);
        } catch (IOException e) {
            logger.error("Plugin configuration parsing has been failed", e);
            this.configuration = new HashMap<>();
        }
    }

    @Override
    protected Identity doAuth(String username, String password) {
        logger.info(configuration.toString());
        return null;
    }
}
