package com.wavjaby.discord;

import com.wavjaby.discord.object.guild.Guild;
import com.wavjaby.discord.object.interaction.InteractionObject;

public class DiscordEvent {

    /**
     * Guild Event
     * @param guild
     */
    public void onGuildReady(Guild guild) {
    }

    public void onGuildCreate(Guild guild) {
    }

    public void onSlashCommand(InteractionObject interaction) {
    }

    public void onButtonClick(InteractionObject interaction) {
    }

    public void onSelectMenu(InteractionObject interaction) {
    }
}
