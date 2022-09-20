package com.wavjaby.discord.object.message;

import com.wavjaby.json.JsonObject;

public class MessageActivity {
    private int type;
    private String party_id;

    MessageActivity(JsonObject activityData){
        type = activityData.getInt("type");
        party_id = activityData.getString("party_id");
    }

    public int getType() {
        return type;
    }

    public String getParty_id() {
        return party_id;
    }
}
