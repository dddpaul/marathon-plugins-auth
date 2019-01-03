package com.github.dddpaul.marathon.plugin.auth;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.OutputFrame;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class FileAuthenticatorFuncTest {

    private static final int MARATHON_PORT = 8080;
    private static final DockerComposeContainer environment =
            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
                    .withLocalCompose(true)
                    .withExposedService("marathon", MARATHON_PORT)
                    .withLogConsumer("marathon", o -> System.out.print(((OutputFrame) o).getUtf8String()));

    @BeforeAll
    static void init() {
        environment.start();
    }

    @AfterAll
    static void shutdown() {
        environment.stop();
    }

    @Test
    void shouldAuthenticateForValidCredentials() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:" + MARATHON_PORT + "/v2/apps/")
                .header("Authorization", Credentials.basic("abc", "qwe"))
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }

    @Test
    void shouldNotAuthenticateForInvalidCredentials() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:" + MARATHON_PORT + "/v2/apps/")
                .header("Authorization", Credentials.basic("jesse", "password"))
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(401, response.code());
    }
}
