package com.github.dddpaul.marathon.plugin.auth;

import akka.dispatch.ExecutionContexts;
import akka.dispatch.Futures;
import mesosphere.marathon.plugin.auth.Identity;
import mesosphere.marathon.plugin.http.HttpRequest;
import mesosphere.marathon.plugin.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.Executors;

public abstract class BasicAuthenticator implements mesosphere.marathon.plugin.auth.Authenticator {

    protected final ExecutionContext EC = ExecutionContexts.fromExecutorService(Executors.newSingleThreadExecutor());
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public Future<Option<Identity>> authenticate(HttpRequest request) {
        return Futures.future(() -> Option.apply(doAuth(request)), EC);
    }

    protected Identity doAuth(HttpRequest request) {
        try {
            Option<String> header = request.header("Authorization").headOption();
            if (header.isDefined() && header.get().startsWith("Basic ")) {
                String encoded = header.get().replaceFirst("Basic ", "");
                String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
                String[] userPass = decoded.split(":", 2);
                if (userPass.length == 2) {
                    return doAuth(userPass[0], userPass[1]);
                }
            }
        } catch (Exception e) {
            /* do not authenticate in case of exception */
        }
        return null;
    }

    protected abstract Identity doAuth(String username, String password);

    @Override
    public void handleNotAuthenticated(HttpRequest request, HttpResponse response) {
        response.status(401);
        response.header("WWW-Authenticate", "Basic realm=\"Marathon: Username==Password\"");
        response.body("application/json", "{\"problem\": \"Not Authenticated!\"}".getBytes());
    }
}
