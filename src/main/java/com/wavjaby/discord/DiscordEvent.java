package com.wavjaby.discord;

import com.wavjaby.discord.object.guild.Guild;
import com.wavjaby.discord.object.interaction.InteractionCommand;
import com.wavjaby.discord.object.interaction.InteractionComponent;
import com.wavjaby.discord.object.interaction.InteractionObject;
import com.wavjaby.discord.object.message.MessageObject;

public class DiscordEvent {

    /**
     * Guild Event
     */
    public void onGuildReady(Guild guild) {
    }

    public void onGuildCreate(Guild guild) {
    }

    /**
     * Message Event
     */

    public void onMessageCreate(MessageObject message) {
    }

    /**
     * Interaction Event
     */
    public void onSlashCommand(InteractionCommand commandInteraction) {
    }

    public void onButtonClick(InteractionComponent componentInteraction) {
    }

    public void onSelectMenu(InteractionComponent componentInteraction) {
    }
}
