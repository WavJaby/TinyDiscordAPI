package com.wavjaby.discord.object.slashcommand;

import com.wavjaby.discord.object.interaction.InteractionCommand;
import com.wavjaby.json.JsonObject;

import java.util.ArrayList;

public class SlashCommand extends SlashCommandStruct {

    public SlashCommand(JsonObject commandData) {
        super(commandData);
    }

    public SlashCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addOption(CommandOption option) {
        if (options == null)
            options = new ArrayList<>();
        options.add(option);
    }
}
