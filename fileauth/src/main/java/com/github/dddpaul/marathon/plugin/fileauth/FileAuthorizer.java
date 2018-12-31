package com.github.dddpaul.marathon.plugin.fileauth;

import com.github.dddpaul.marathon.plugin.auth.Action;
import com.github.dddpaul.marathon.plugin.auth.JavaIdentity;
import mesosphere.marathon.plugin.auth.AuthorizedAction;
import mesosphere.marathon.plugin.auth.AuthorizedResource;
import mesosphere.marathon.plugin.auth.Authorizer;
import mesosphere.marathon.plugin.auth.Identity;
import mesosphere.marathon.plugin.http.HttpResponse;
import mesosphere.marathon.state.AppDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileAuthorizer implements Authorizer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public <Resource> boolean isAuthorized(Identity principal, AuthorizedAction<Resource> action, Resource resource) {
        logger.info("Principal = {}, action = {}, path = {}", principal.toString(), action.toString(), resource.toString());
        if (principal instanceof JavaIdentity) {
            JavaIdentity identity = (JavaIdentity) principal;
            if (resource instanceof AppDefinition) {
                return isAuthorizedForApp(identity, Action.byAction(action), (AppDefinition) resource);
            }
            if (resource instanceof AuthorizedResource) {
                return isAuthorizedForResource(identity, Action.byAction(action), (AuthorizedResource) resource);
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
