package com.wavjaby.discord.object.interaction;

import com.wavjaby.discord.DiscordBot;
import com.wavjaby.discord.object.message.component.ComponentType;
import com.wavjaby.json.JsonObject;

public class InteractionComponent extends InteractionObject{

    private String custom_id;
    private ComponentType component_type;
    private String values;                  // ?
    public InteractionComponent(JsonObject commandData, DiscordBot bot) {
        super(commandData, bot);
        JsonObject commandOption = commandData.getJson("data");
        custom_id = commandOption.getString("custom_id");
        component_type = ComponentType.valueOf(commandOption.getInt("component_type"));
    }

    public ComponentType getComponentType() {
        return component_type;
    }

    public String getCustomID() {
        return custom_id;
    }
}
