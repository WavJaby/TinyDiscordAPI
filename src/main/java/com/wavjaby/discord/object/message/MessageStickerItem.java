package com.wavjaby.discord.object.message;

import com.wavjaby.json.JsonObject;

public class MessageStickerItem {
    public String id;
    public String name;
    public int format_type;

    MessageStickerItem(JsonObject stickerItemData) {
        id = stickerItemData.getString("id");
        name = stickerItemData.getString("name");
        format_type = stickerItemData.getInt("format_type");
    }

}
