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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FileAuthorizerTest {

    private static String FILENAME = "authorizer.conf.json";
    private JsObject JSON;

    static Stream<Arguments> validUsers() {
        return Stream.of(
                Arguments.of("ernie", "ernie"),
                Arguments.of("paul", "qwerty"),
                Arguments.of("guest", "12345"),
                Arguments.of("md5_user", "qwe"),
                Arguments.of("sha1_user", "qwe"),
                Arguments.of("bcrypt_user", "qwe")
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
                Arguments.of("invalid", "invalid"),
                Arguments.of("ernie", null),
                Arguments.of("ernie", ""),
                Arguments.of("ernie", "invalid"),
                Arguments.of("md5_user", null),
                Arguments.of("md5_user", ""),
                Arguments.of("md5_user", "invalid"),
                Arguments.of("sha1_user", null),
                Arguments.of("sha1_user", ""),
                Arguments.of("sha1_user", "invalid"),
                Arguments.of("bcrypt_user", null),
                Arguments.of("bcrypt_user", ""),
                Arguments.of("bcrypt_user", "invalid")
        );
    }

    @BeforeEach
    void setUp() throws Exception {
        URL url = Optional.ofNullable(getClass().getClassLoader().getResource(FILENAME))
                .orElseThrow(() -> new RuntimeException(FILENAME + " is not found"));
        JSON = (JsObject) Json.parse(Files.readAllBytes(Paths.get(url.toURI())));
    }

    @ParameterizedTest
    @MethodSource("validUsers")
    void shouldAuthorizeForValidPermissions(String login, String password) {
        FileAuthorizer authorizer = new FileAuthorizer();
        authorizer.initialize(null, JSON);
        assertTrue(authorizer.isAuthorized(null, null, null));
    }

    @ParameterizedTest
    @MethodSource("invalidUsers")
    void shouldNotAuthorizeForInvalidPermissions(String login, String password) {
        FileAuthorizer authorizer = new FileAuthorizer();
        authorizer.initialize(null, JSON);
        assertFalse(authorizer.isAuthorized(null, null, null));
    }

}
