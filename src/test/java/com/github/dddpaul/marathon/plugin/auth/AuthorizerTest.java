package com.github.dddpaul.marathon.plugin.auth;

import com.github.dddpaul.marathon.plugin.auth.entities.Action;
import com.github.dddpaul.marathon.plugin.auth.entities.Principal;
import mesosphere.marathon.Protos;
import mesosphere.marathon.state.AppDefinition;
import mesosphere.marathon.state.Group;
import mesosphere.marathon.state.PathId;
import org.apache.mesos.Protos.CommandInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import play.api.libs.json.JsObject;
import play.api.libs.json.Json;
import scala.collection.immutable.Set;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static com.github.dddpaul.marathon.plugin.auth.entities.Action.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AuthorizerTest {

    private static String FILENAME = "authorizer.conf.json";
    private JsObject JSON;

    static Stream<Arguments> validRequests() {
        return Stream.of(
                Arguments.of("guest", "/", ViewRunSpec),
                Arguments.of("guest", "/", ViewGroup),
                Arguments.of("guest", "/", ViewResource),
                Arguments.of("guest", "/some/group/app", ViewRunSpec),
                Arguments.of("guest", "/some/group", ViewGroup),
                Arguments.of("guest", "/some/resource", ViewResource),
                Arguments.of("ernie", "/", CreateRunSpec),
                Arguments.of("ernie", "/", UpdateGroup),
                Arguments.of("ernie", "/", DeleteResource),
                Arguments.of("ernie", "/some/group", CreateRunSpec),
                Arguments.of("ernie", "/some/group", UpdateGroup),
                Arguments.of("ernie", "/some/resource", DeleteResource),
                Arguments.of("ernie", "/some/group", CreateRunSpec),
                Arguments.of("ernie", "/some/group", UpdateGroup),
                Arguments.of("corp-admin", "/", ViewRunSpec),
                Arguments.of("corp-admin", "/corp-app1", CreateRunSpec),
                Arguments.of("wildcard-operator", "/some/app-server1", UpdateRunSpec),
                Arguments.of("wildcard-operator", "/some/app-server2", UpdateRunSpec),
                Arguments.of("wildcard-guest", "/some/app1", ViewRunSpec),
                Arguments.of("wildcard-guest", "/some/app2", ViewRunSpec),
                Arguments.of("roles_conflict", "/some/app", CreateGroup),
                Arguments.of("roles_conflict", "/some/app", UpdateRunSpec),
                Arguments.of("roles_conflict", "/some/app", ViewResource)
        );
    }

    static Stream<Arguments> invalidRequests() {
        return Stream.of(
                Arguments.of("guest", "/", CreateRunSpec),
                Arguments.of("guest", "/", UpdateGroup),
                Arguments.of("guest", "/", DeleteResource),
                Arguments.of("corp-admin", "/", CreateRunSpec),
                Arguments.of("some-app-operator", "/some/app", CreateRunSpec),
                Arguments.of("some-app-operator", "/some/other-app", ViewRunSpec),
                Arguments.of("wildcard-operator", "/some/app_server", UpdateRunSpec),
                Arguments.of("wildcard-guest", "/some/app3", ViewRunSpec)
        );
    }

    @BeforeEach
    void setUp() throws Exception {
        URL url = Optional.ofNullable(getClass().getClassLoader().getResource(FILENAME))
                .orElseThrow(() -> new RuntimeException(FILENAME + " is not found"));
        JSON = (JsObject) Json.parse(Files.readAllBytes(Paths.get(url.toURI())));
    }

    @ParameterizedTest
    @MethodSource("validRequests")
    void shouldAuthorizeForValidPermissions(String login, String path, Action action) {
        // given
        Authorizer authorizer = new Authorizer();

        // when
        authorizer.initialize(null, JSON);

        // then
        assertTrue(authorizer.isAuthorized(new Principal(login), action.getAction(), newApp(path)));
        assertTrue(authorizer.isAuthorized(new Principal(login), action.getAction(), newGroup(path)));
    }

    @ParameterizedTest
    @MethodSource("invalidRequests")
    void shouldNotAuthorizeForInvalidPermissions(String login, String path, Action action) {
        // given
        Authorizer authorizer = new Authorizer();

        // when
        authorizer.initialize(null, JSON);

        // then
        assertFalse(authorizer.isAuthorized(new Principal(login), action.getAction(), newApp(path)));
        assertFalse(authorizer.isAuthorized(new Principal(login), action.getAction(), newGroup(path)));
    }

    private AppDefinition newApp(String path) {
        return AppDefinition.fromProto(
                Protos.ServiceDefinition.newBuilder()
                        .setId(path)
                        .setCmd(CommandInfo.newBuilder().build())
                        .setInstances(1)
                        .setExecutor("")
                        .build()
        );
    }

    private Group newGroup(String path) {
        return Group.empty(new PathId(new Set.Set1<>(path).<String>toBuffer().toList(), false));
    }
}
