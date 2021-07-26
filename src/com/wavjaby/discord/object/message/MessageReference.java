package com.wavjaby.discord.object.message;

import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

public class MessageReference {
    private String message_id;
    private String channel_id;
    private String guild_id;
    private Boolean fail_if_not_exists;

    MessageReference(JsonObject referenceData) {
        message_id = referenceData.getString("message_id");
        channel_id = referenceData.getString("channel_id");
        guild_id = referenceData.getString("guild_id");
        if (referenceData.containsKey("fail_if_not_exists"))
            fail_if_not_exists = referenceData.getBoolean("fail_if_not_exists");
    }

    public MessageReference(String message_id, String channel_id, String guild_id) {
        this.message_id = message_id;
        this.channel_id = channel_id;
        this.guild_id = guild_id;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        builder.append("message_id", message_id);
        builder.append("channel_id", channel_id);
        builder.append("guild_id", guild_id);
        if (fail_if_not_exists != null)
            builder.append("fail_if_not_exists", fail_if_not_exists);
        return builder.toString();
    }

    public String getMessageID() {
        return message_id;
    }

    public String getChannelID() {
        return channel_id;
    }

    public String getGuildID() {
        return guild_id;
    }

    public boolean isFail_if_not_exists() {
        return fail_if_not_exists;
    }

}
