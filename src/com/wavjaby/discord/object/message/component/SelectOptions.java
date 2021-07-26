package com.wavjaby.discord.object.message.component;

import com.wavjaby.discord.object.guild.Emoji;
import com.wavjaby.json.JsonObject;

public class SelectOptions {
    private String label;
    private String value;
    private String description;
    private Emoji emoji;
    private Boolean isDefault;

    public SelectOptions(JsonObject optionData) {
        label = optionData.getString("label");
        value = optionData.getString("value");
        description = optionData.getString("description");
        if (optionData.containsKey("emoji"))
            emoji = new Emoji(optionData.get("emoji"));
        if (optionData.containsKey("default"))
            isDefault = optionData.getBoolean("default");
    }
}
