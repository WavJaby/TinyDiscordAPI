package com.wavjaby.discord.object.slashcommand;

import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

import java.util.List;

public class CommandOption {
    private Integer type;
    private String name;
    private String description;
    private Boolean required;                       //?
    private List<CommandOptionChoice> choices;      //?
    private List<CommandOption> options;            //?

    public CommandOption(Integer type, String name, String description, boolean required, List<CommandOptionChoice> choices, List<CommandOption> options) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
        this.choices = choices;
        this.options = options;
    }

    public CommandOption(Integer type, String name, String description, boolean required) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
    }

    public CommandOption(JsonObject optionData) {
        type = optionData.getInteger("type");
        name = optionData.getString("name");
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (type != null)
            builder.append("type", type);
        if (name != null)
            builder.append("name", name);
        if (description != null)
            builder.append("description", description);
        if (required != null)
            builder.append("required", required);
        if (choices != null)
            builder.append("choices", choices.toString(), true);
        if (options != null)
            builder.append("options", options.toString(), true);
        return builder.toString();
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return description;
    }
}
