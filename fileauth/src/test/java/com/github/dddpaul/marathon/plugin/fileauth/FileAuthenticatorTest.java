package com.github.dddpaul.marathon.plugin.fileauth;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import play.api.libs.json.JsObject;
import play.api.libs.json.JsValue;
import play.api.libs.json.Json;

import java.io.IOException;

class FileAuthenticatorTest {

    String json = "{\n" +
            "  \"configuration\": {\n" +
            "    \"users\": [\n" +
            "      {\n" +
            "        \"user\": \"ernie\",\n" +
            "        \"password\": \"ernie\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"user\": \"paul\",\n" +
            "        \"password\": \"qwerty\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"user\": \"guest\",\n" +
            "        \"password\": \"12345\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    @BeforeAll
    static void init() {

    }

    @AfterAll
    static void shutdown() {

    }

    @Test
    void shouldAuthenticateForValidCredentials() throws Exception {
        JsObject js = (JsObject) Json.parse(json);
        FileAuthenticator authenticator = new FileAuthenticator();
        authenticator.initialize(null, js);
        authenticator.doAuth("a", "b");
    }
}
