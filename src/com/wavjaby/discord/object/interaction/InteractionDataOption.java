package com.wavjaby.discord.object.interaction;

import com.wavjaby.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class InteractionDataOption {
    private String name;
    private Integer type;
    private Object value;                          //?
    private List<InteractionDataOption> options;    //?

    public InteractionDataOption(JsonObject optionData) {
        name = optionData.getString("name");
        type = optionData.getInteger("type");
        if (optionData.containsKey("value"))
            value = optionData.getObject("value");
        if (optionData.containsKey("options")) {
            options = new ArrayList<>();
            for (Object i : optionData.getJsonArray("options"))
                options.add(new InteractionDataOption((JsonObject) i));
        }
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public List<InteractionDataOption> getOptions() {
        return options;
    }
}
