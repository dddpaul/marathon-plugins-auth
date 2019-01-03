package com.github.dddpaul.marathon.plugin.auth.entities;

import mesosphere.marathon.plugin.auth.Identity;

import java.util.Objects;

public class JavaIdentity implements Identity {

    private final String name;

    public JavaIdentity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JavaIdentity that = (JavaIdentity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
