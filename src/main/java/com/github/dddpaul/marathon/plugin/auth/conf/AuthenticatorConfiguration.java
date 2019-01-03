package com.github.dddpaul.marathon.plugin.auth.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.dddpaul.marathon.plugin.auth.entities.User;

import java.io.IOException;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticatorConfiguration {

    @JsonProperty("users")
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private Map<String, User> users;

    public Map<String, User> getUsers() {
        return users;
    }

    public static class UserDeserializer extends JsonDeserializer<User> {

        @Override
        public User deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
            return p.readValueAs(User.class)
                    .setName(ctx.getParser()
                            .getCurrentName());
        }
    }
}
