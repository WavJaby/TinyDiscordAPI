package com.wavjaby.discord.object.message;

import com.wavjaby.discord.object.user.User;
import com.wavjaby.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class TeamMembers {
    private int membership_state;
    private List<String> permissions;
    private String team_id;
    private User user;

    TeamMembers(JsonObject teamMemberData) {
        membership_state = teamMemberData.getInt("membership_state");
        permissions = new ArrayList<>();
        for (Object i : teamMemberData.getArray("permissions")) {
            permissions.add((String) i);
        }
        team_id = teamMemberData.getString("team_id");
        user = new User(teamMemberData.getJson("user"));
    }

    public int getMembership_state() {
        return membership_state;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public String getTeam_id() {
        return team_id;
    }

    public User getUser() {
        return user;
    }
}
