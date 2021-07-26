package com.wavjaby.discord.object.guild;

import com.wavjaby.json.JsonObject;

public class Role {
    private String id;
    private String name;
    private int color;
    private boolean hoist;
    private int position;
    private String permissions;
    private boolean managed;
    private boolean mentionable;
    private RoleTag tags;           //?

    public Role(JsonObject roleData) {
        id = roleData.getString("id");
        name = roleData.getString("name");
        color = roleData.getInteger("color");
        hoist = roleData.getBoolean("hoist");
        position = roleData.getInteger("position");
        permissions = roleData.getString("permissions");
        managed = roleData.getBoolean("managed");
        mentionable = roleData.getBoolean("mentionable");
        if (roleData.containsKey("tags"))
            tags = new RoleTag(roleData.get("tags"));
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public boolean isHoist() {
        return hoist;
    }

    public int getPosition() {
        return position;
    }

    public String getPermissions() {
        return permissions;
    }

    public boolean isManaged() {
        return managed;
    }

    public boolean isMentionable() {
        return mentionable;
    }

    public RoleTag getTags() {
        return tags;
    }
}
