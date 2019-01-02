package com.github.dddpaul.marathon.plugin.fileauth.entities;

import java.util.Objects;

public class User {
    private String login;
    private String password;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user1 = (User) o;
        return Objects.equals(login, user1.login) &&
                Objects.equals(password, user1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
