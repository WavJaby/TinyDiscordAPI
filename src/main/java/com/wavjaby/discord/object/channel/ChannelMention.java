package com.wavjaby.discord.object.channel;

import com.wavjaby.json.JsonObject;

public class ChannelMention {
    private String id;
    private String guild_id;
    private int type;
    private String mane;

    public ChannelMention(JsonObject mentionData) {
        id = mentionData.getString("id");
        guild_id = mentionData.getString("guild_id");
        type = mentionData.getInteger("type");
        mane = mentionData.getString("mane");
    }
}
