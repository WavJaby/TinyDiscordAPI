package com.wavjaby.discord.object.permission;

import com.wavjaby.json.JsonObject;

public class PermissionOverwrite {
    private String id;
    private int type;
    private String allow;
    private String deny;

    public PermissionOverwrite(JsonObject overwriteData) {
        id = overwriteData.getString("id");
        type = overwriteData.getInteger("type");
        allow = overwriteData.getString("allow");
        deny = overwriteData.getString("deny");
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getAllow() {
        return allow;
    }

    public String getDeny() {
        return deny;
    }
}
