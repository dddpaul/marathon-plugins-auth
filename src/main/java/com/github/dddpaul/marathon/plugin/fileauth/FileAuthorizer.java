package com.github.dddpaul.marathon.plugin.fileauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dddpaul.marathon.plugin.fileauth.conf.AuthorizerConfigurationHolder;
import com.github.dddpaul.marathon.plugin.fileauth.conf.AuthorizerConfigurationHolder.AuthorizerConfiguration;
import com.github.dddpaul.marathon.plugin.fileauth.entities.Permission;
import com.github.dddpaul.marathon.plugin.fileauth.entities.Role;
import mesosphere.marathon.plugin.auth.AuthorizedAction;
import mesosphere.marathon.plugin.auth.AuthorizedResource;
import mesosphere.marathon.plugin.auth.Authorizer;
import mesosphere.marathon.plugin.auth.Identity;
import mesosphere.marathon.plugin.http.HttpResponse;
import mesosphere.marathon.plugin.plugin.PluginConfiguration;
import mesosphere.marathon.state.AppDefinition;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.api.libs.json.JsObject;

import java.io.IOException;
import java.util.List;

public class FileAuthorizer implements Authorizer, PluginConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private AuthorizerConfiguration configuration;

    @Override
    public void initialize(scala.collection.immutable.Map<String, Object> marathonInfo, JsObject json) {
        logger.info("Authorizer initialize has been called with: " + json);
        ObjectMapper mapper = new ObjectMapper();
        try {
            configuration = mapper.readValue(json.toString(), AuthorizerConfigurationHolder.class).getConfiguration();
        } catch (IOException e) {
            logger.error("Plugin authorizer configuration parsing has been failed", e);
            configuration = new AuthorizerConfiguration();
        }
    }

    @Override
    public <Resource> boolean isAuthorized(Identity principal, AuthorizedAction<Resource> action, Resource resource) {
        logger.info("Principal = {}, action = {}, path = {}", principal.toString(), action.toString(), resource.toString());

        if (!(principal instanceof JavaIdentity)) {
            return false;
        }
        JavaIdentity identity = (JavaIdentity) principal;

        List<Permission> permissions = configuration.getPermissions().get(identity.getName());
        if (CollectionUtils.isEmpty(permissions)) {
            return false;
        }

        for (Permission p : permissions) {
            Role role = configuration.getRoles().get(p.getRole());
            if (role == null) {
                // User role is not defined
                return false;
            }

            if (role.getActions().contains(Action.byAction(action))) {
                if (resource instanceof AppDefinition) {
                    if ((((AppDefinition) resource).id().toString()).startsWith(p.getPath())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isAuthorizedForApp(JavaIdentity principal, Action action, AppDefinition appInfo) {
        logger.info("Principal = {}, action = {}, path = {}", principal.getName(), action.toString(), appInfo.id().toString());
        switch (action) {
            case ViewGroup:
            case ViewApp:
            case CreateGroup:
            case UpdateGroup:
                return true;
            case CreateApp:
            case UpdateApp:
            case DeleteApp:
                return principal.getName().contains("ernie");
            case DeleteGroup:
                return appInfo.id().toString().startsWith("/test");
            default:
                return false;
        }
    }

    private boolean isAuthorizedForResource(JavaIdentity principal, Action action, AuthorizedResource resource) {
        logger.info("Principal = {}, action = {}, resource = {}", principal.getName(), action.toString(), resource.toString());
        switch (action) {
            case ViewResource:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void handleNotAuthorized(Identity principal, HttpResponse response) {
        response.status(403);
        response.body("application/json", "{\"problem\": \"Not Authorized to perform this action!\"}".getBytes());
    }
}
