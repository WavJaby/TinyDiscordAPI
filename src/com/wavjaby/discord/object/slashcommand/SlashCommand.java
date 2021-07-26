package com.wavjaby.discord.object.slashcommand;

import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class SlashCommand {
    private String name;
    private String description;
    private List<CommandOption> options;
    private Boolean default_permission;

    public SlashCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addOption(CommandOption option){
        if(options == null)
            options = new ArrayList<>();
        options.add(option);
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (name != null)
            builder.append("name", name);
        if (description != null)
            builder.append("description", description);
        if (options != null)
            builder.append("options", options.toString(), true);
        if (default_permission != null)
            builder.append("default_permission", default_permission);
        return builder.toString();
    }

}
