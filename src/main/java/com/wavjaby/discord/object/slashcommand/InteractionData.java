package com.wavjaby.discord.object.slashcommand;

import com.wavjaby.discord.object.interaction.InteractionOption;
import com.wavjaby.discord.object.interaction.InteractionObject;
import com.wavjaby.discord.object.message.component.ComponentType;
import com.wavjaby.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class InteractionData {
    private String id;
    private String name;
    private Object resolved;                                //? not set
    private Map<String, InteractionOption> options;     //?
    private String custom_id;
    private ComponentType type;

    public InteractionData(JsonObject interactionData) {
        id = interactionData.getString("id");
        name = interactionData.getString("name");
        resolved = interactionData.getString("resolved");
        if (interactionData.containsKey("options")) {
            options = new HashMap<>();
            for (Object i : interactionData.getArray("options")) {
                InteractionOption option = new InteractionOption((JsonObject) i);
                options.put(option.getName(), option);
            }
        }
        custom_id = interactionData.getString("custom_id");
        if(interactionData.containsKey("component_type"))
        type = ComponentType.valueOf(interactionData.getInt("component_type"));
    }

    /**
     * get option
     */
    public InteractionOption getOption(String name) {
        return options.get(name);
    }

    public String asString(String name) {
        return (String) options.get(name).getValue();
    }

    public boolean asBoolean(String name) {
        return (boolean) options.get(name).getValue();
    }

    public int asInteger(String name) {
        return (int) options.get(name).getValue();
    }

    public boolean has(String name) {
        return options != null && options.get(name) != null;
    }

    public InteractionObject getMember() {
        return getMember();
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCustomID() {
        return custom_id;
    }

    public ComponentType getType() {
        return type;
    }
}
