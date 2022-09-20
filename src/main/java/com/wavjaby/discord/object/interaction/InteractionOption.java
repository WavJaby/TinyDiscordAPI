package com.wavjaby.discord.object.interaction;

import com.wavjaby.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class InteractionOption {
    private String name;
    private Integer type;
    private Object value;                          //?
    private List<InteractionOption> options;    //?

    public InteractionOption(JsonObject optionData) {
        name = optionData.getString("name");
        type = optionData.getInt("type");
        if (optionData.containsKey("value"))
            value = optionData.getObject("value");
        if (optionData.containsKey("options")) {
            options = new ArrayList<>();
            for (Object i : optionData.getArray("options"))
                options.add(new InteractionOption((JsonObject) i));
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

    public List<InteractionOption> getOptions() {
        return options;
    }
}
