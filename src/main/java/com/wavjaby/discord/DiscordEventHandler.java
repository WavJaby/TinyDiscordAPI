package com.wavjaby.discord;

import com.wavjaby.discord.httpsender.DiscordDataSender;
import com.wavjaby.discord.object.guild.Guild;
import com.wavjaby.discord.object.interaction.InteractionObject;
import com.wavjaby.discord.object.interaction.InteractionType;
import com.wavjaby.discord.object.message.component.ComponentType;
import com.wavjaby.discord.values.gateway.EventType;
import com.wavjaby.json.JsonObject;

import java.util.function.Consumer;

import static com.wavjaby.discord.values.gateway.EventType.GUILD_CREATE;
import static com.wavjaby.discord.values.gateway.EventType.READY;

public class DiscordEventHandler {
    private final DiscordBot bot;
    private final DiscordDataSender httpSender;

    private int length = 0;
    private DiscordEvent[] eventHandlers = new DiscordEvent[2];

    boolean guildReady;

    DiscordEventHandler(DiscordBot bot, DiscordDataSender httpSender) {
        this.bot = bot;
        this.httpSender = httpSender;
    }

    void onEvent(EventType type, JsonObject data) {
        //判斷是不是guild 初始化
        if (type == READY) {
            guildReady = true;
        } else if (guildReady && type != GUILD_CREATE)
            guildReady = false;

        switch (type) {
            case READY:
                break;
            case GUILD_CREATE:
                Guild guild = new Guild(data, httpSender);
                bot.guilds.put(guild.getID(), guild);
                if (guildReady)
                    forEach(i -> i.onGuildReady(guild));
                else
                    forEach(i -> i.onGuildCreate(guild));
                break;
            case INTERACTION_CREATE:
                InteractionObject interaction = new InteractionObject(data, bot);
                if (interaction.getType() == InteractionType.APPLICATION_COMMAND)
                    forEach(i -> i.onSlashCommand(interaction));
                if (interaction.getType() == InteractionType.MESSAGE_COMPONENT) {
                    if (interaction.getComponentType() == ComponentType.BUTTON)
                        forEach(i -> i.onButtonClick(interaction));
                    else if (interaction.getComponentType() == ComponentType.SELECT_MENU)
                        forEach(i -> i.onSelectMenu(interaction));
                }
                break;
            default:
                break;
        }
    }

    private void forEach(Consumer<DiscordEvent> action) {
        for (int i = 0; i < length; i++) {
            action.accept(eventHandlers[i]);
        }
    }

    public void addListener(DiscordEvent event) {
        if (length + 1 == eventHandlers.length) {
            DiscordEvent[] cache = new DiscordEvent[(int) (eventHandlers.length * 1.5)];
            System.arraycopy(eventHandlers, 0, cache, 0, length);
        }
        eventHandlers[length++] = event;
    }
}
