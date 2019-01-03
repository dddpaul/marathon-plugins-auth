package com.github.dddpaul.marathon.plugin.fileauth.entities;

import com.github.dddpaul.marathon.plugin.auth.Action;
import lombok.Data;

import java.util.List;

@Data
public class Role {
    private List<Action> actions;
}
