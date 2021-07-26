package com.wavjaby.discord.object.guild;

import com.wavjaby.json.JsonObject;

public class RoleTag {
    private String bot_id;
    private String integration_id;
    private Object premium_subscriber;

    public RoleTag(JsonObject tagData) {
        bot_id = tagData.getString("bot_id");
        integration_id = tagData.getString("integration_id");
        premium_subscriber = tagData.getObject("premium_subscriber");
    }

    public String getBot_id() {
        return bot_id;
    }

    public String getIntegration_id() {
        return integration_id;
    }

    public Object getPremium_subscriber() {
        return premium_subscriber;
    }
}
