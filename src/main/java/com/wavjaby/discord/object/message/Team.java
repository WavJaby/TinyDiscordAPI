package com.wavjaby.discord.object.message;

import com.wavjaby.json.JsonObject;

public class Team {
    private String icon;
    private String id;
    private TeamMembers members;
    private String name;
    private String owner_user_id;

    Team(JsonObject teamData){
        icon = teamData.getString("icon");
        id = teamData.getString("id");
        members = new TeamMembers(teamData.getJson("members"));
        name = teamData.getString("name");
        owner_user_id = teamData.getString("owner_user_id");
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public TeamMembers getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    public String getOwner_user_id() {
        return owner_user_id;
    }
}
