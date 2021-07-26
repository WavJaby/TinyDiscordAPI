package com.wavjaby.discord.object.message;

import com.wavjaby.discord.object.user.User;
import com.wavjaby.json.JsonObject;

public class MessageSticker {
    private String id;
    private String pack_id;     //?
    private String name;
    private String description;
    private String tags;
    private String asset;
    private int format_type;
    private boolean available;  //?
    private String guild_id;    //?
    private User user;          //?
    private int sort_value;     //?

    MessageSticker(JsonObject stickerData) {
        id = stickerData.getString("id");
        pack_id = stickerData.getString("pack_id");
        name = stickerData.getString("name");
        description = stickerData.getString("description");
        tags = stickerData.getString("tags");
        asset = stickerData.getString("asset");
        format_type = stickerData.getInteger("format_type");
        if (stickerData.containsKey("available"))
            available = stickerData.getBoolean("available");
        guild_id = stickerData.getString("guild_id");
        if (stickerData.containsKey("user"))
            user = new User(stickerData.get("user"));
        if (stickerData.containsKey("sort_value"))
            sort_value = stickerData.getInteger("sort_value");
    }
}
