package com.github.dddpaul.marathon.plugin.fileauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import play.api.libs.json.JsObject;
import play.api.libs.json.Json;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


class FileAuthenticatorTest {

    private static String AUTHENTICATOR_CONF_FILENAME = "authenticator.conf.json";
    private JsObject AUTHENTICATOR_CONF_JSON;

    static Stream<Arguments> validUsers() {
        return Stream.of(
                Arguments.of("ernie", "ernie"),
                Arguments.of("paul", "qwerty"),
                Arguments.of("guest", "12345")
        );
    }

    static Stream<Arguments> invalidUsers() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, ""),
                Arguments.of("", null),
                Arguments.of("", ""),
                Arguments.of("", "invalid"),
                Arguments.of("invalid", ""),
                Arguments.of("invalid", "invalid")
        );
    }

    @BeforeEach
    void setUp() throws Exception {
        URL url = Optional.ofNullable(getClass().getClassLoader().getResource(AUTHENTICATOR_CONF_FILENAME))
                .orElseThrow(() -> new RuntimeException(AUTHENTICATOR_CONF_FILENAME + " is not found"));
        AUTHENTICATOR_CONF_JSON = (JsObject) Json.parse(Files.readAllBytes(Paths.get(url.toURI())));
    }

    @ParameterizedTest
    @MethodSource("validUsers")
    void shouldAuthenticateForValidCredentials(String login, String password) {
        FileAuthenticator authenticator = new FileAuthenticator();
        authenticator.initialize(null, AUTHENTICATOR_CONF_JSON);
        assertNotNull(authenticator.doAuth(login, password));
    }

    @ParameterizedTest
    @MethodSource("invalidUsers")
    void shouldNotAuthenticateForInvalidCredentials(String login, String password) {
        FileAuthenticator authenticator = new FileAuthenticator();
        authenticator.initialize(null, AUTHENTICATOR_CONF_JSON);
        assertNull(authenticator.doAuth(login, password));
    }
}
