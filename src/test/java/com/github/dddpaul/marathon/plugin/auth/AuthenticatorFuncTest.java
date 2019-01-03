package com.github.dddpaul.marathon.plugin.auth;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.OutputFrame;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisabledIfEnvironmentVariable(named = "TESTCONTAINERS_DISABLED", matches = ".+")
class AuthenticatorFuncTest {

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

    @ParameterizedTest
    @MethodSource("com.github.dddpaul.marathon.plugin.auth.AuthenticatorTest#validUsers")
    void shouldAuthenticateForValidCredentials(String login, String password) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:" + MARATHON_PORT + "/v2/apps/")
                .header("Authorization", Credentials.basic(login, password))
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }

    @ParameterizedTest
    @MethodSource("com.github.dddpaul.marathon.plugin.auth.AuthenticatorTest#invalidUsers")
    void shouldNotAuthenticateForInvalidCredentials(String login, String password) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:" + MARATHON_PORT + "/v2/apps/")
                .header("Authorization", Credentials.basic(login, password))
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(401, response.code());
    }
}
