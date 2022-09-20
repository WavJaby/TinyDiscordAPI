package com.wavjaby.discord.object.slashcommand;

import com.wavjaby.discord.object.interaction.InteractionCallback;
import com.wavjaby.discord.object.interaction.InteractionCommand;
import com.wavjaby.discord.object.interaction.InteractionResponse;
import com.wavjaby.json.JsonObject;

import java.util.ArrayList;

import static com.wavjaby.discord.object.interaction.InteractionResponseType.CHANNEL_MESSAGE_WITH_SOURCE;

public class SlashCommandListener extends SlashCommand {
    public SlashCommandListener(String name, String description) {
        super(name, description);
    }

    public void onSlashCommand(InteractionCommand interaction) {
    }
}
