package com.wavjaby.discord.object.slashcommand;

import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.wavjaby.discord.object.slashcommand.CommandOptionType.valueOf;

public class CommandOption {
    private CommandOptionType type;
    private String name;
    private String description;
    private Boolean required = false;               //?
    private List<CommandOptionChoice> choices;      //?
    private List<CommandOption> options;            //?

    public CommandOption(JsonObject optionData) {
        type = valueOf(optionData.getInt("type"));
        name = optionData.getString("name");
        description = optionData.getString("description");
        if (optionData.containsKey("required"))
            required = optionData.getBoolean("required");
        if (optionData.containsKey("choices")) {
            choices = new ArrayList<>();
            for (Object i : optionData.getArray("choices"))
                choices.add(new CommandOptionChoice((JsonObject) i));
        }
        if (optionData.containsKey("options")) {
            options = new ArrayList<>();
            for (Object i : optionData.getArray("options"))
                options.add(new CommandOption((JsonObject) i));
        }
    }

    public CommandOption(CommandOptionType type, String name, String description, boolean required, List<CommandOptionChoice> choices, List<CommandOption> options) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
        this.choices = choices;
        this.options = options;
    }

    public CommandOption(CommandOptionType type, String name, String description, boolean required) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (type != null)
            builder.append("type", type.value);
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof CommandOption))
            return false;
        CommandOption target = (CommandOption) obj;
        return name.equals(target.name) &&
                type == target.type &&
                Objects.equals(description, target.description) &&
                required == target.required &&
                Objects.equals(choices, target.choices) &&
                Objects.equals(options, target.options);
    }

    public CommandOptionType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return description;
    }
}
