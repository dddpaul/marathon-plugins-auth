package com.github.dddpaul.marathon.plugin.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mesosphere.marathon.plugin.auth.Identity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JavaIdentity implements Identity {
    private String name;
}
