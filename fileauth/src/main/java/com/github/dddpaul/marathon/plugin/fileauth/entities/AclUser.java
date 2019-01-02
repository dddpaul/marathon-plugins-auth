package com.github.dddpaul.marathon.plugin.fileauth.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class AclUser {

    private String login;

    @JsonProperty("acls")
    private List<Acl> acls;

    public String getLogin() {
        return login;
    }

    public List<Acl> getAcls() {
        return acls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AclUser aclUser = (AclUser) o;
        return Objects.equals(login, aclUser.login) &&
                Objects.equals(acls, aclUser.acls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, acls);
    }
}
