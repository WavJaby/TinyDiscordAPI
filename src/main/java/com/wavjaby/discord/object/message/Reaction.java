package com.wavjaby.discord.object.message;

import com.wavjaby.discord.object.guild.Emoji;
import com.wavjaby.json.JsonObject;

public class Reaction {
    private int count;
    private boolean me;
    private Emoji emoji;

    public Reaction(JsonObject reactionData) {
        count = reactionData.getInt("count");
        me = reactionData.getBoolean("me");
        emoji = new Emoji(reactionData.getJson("emoji"));
    }

    public int getCount() {
        return count;
    }

    public boolean isMe() {
        return me;
    }

    public Emoji getEmoji() {
        return emoji;
    }
}
