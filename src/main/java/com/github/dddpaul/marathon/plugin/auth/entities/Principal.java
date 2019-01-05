package com.github.dddpaul.marathon.plugin.auth.entities;

import lombok.*;
import lombok.experimental.Accessors;
import mesosphere.marathon.plugin.auth.Identity;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class Principal implements Identity {
    private final String name;
}
