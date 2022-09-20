package com.wavjaby.discord;

import com.wavjaby.discord.httpsender.DiscordDataSender;
import com.wavjaby.discord.object.guild.Guild;
import com.wavjaby.discord.object.interaction.InteractionCommand;
import com.wavjaby.discord.object.interaction.InteractionComponent;
import com.wavjaby.discord.object.interaction.InteractionType;
import com.wavjaby.discord.object.message.component.ComponentType;
import com.wavjaby.discord.object.slashcommand.SlashCommand;
import com.wavjaby.discord.object.slashcommand.SlashCommandListener;
import com.wavjaby.discord.values.gateway.EventType;
import com.wavjaby.json.JsonArray;
import com.wavjaby.json.JsonObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

public class DiscordEventHandler {
    private final DiscordBot bot;
    private final DiscordDataSender dataSender;

    private int length = 0;
    private DiscordEvent[] eventHandlers = new DiscordEvent[2];
    private final Map<String, SlashCommandListener> registeredCommand = new HashMap<>();
    private final HashSet<String> readyGuild = new HashSet<>();

    DiscordEventHandler(DiscordBot bot, DiscordDataSender dataSender) {
        this.bot = bot;
        this.dataSender = dataSender;
    }

    void onEvent(EventType type, JsonObject data) {
        switch (type) {
            case READY:
                break;
            case GUILD_CREATE:
                Guild guild = new Guild(data, dataSender, registeredCommand);
                bot.guilds.put(guild.getID(), guild);
                if (readyGuild.contains(guild.getID())) {
                    forEach(i -> i.onGuildReady(guild));
                    readyGuild.remove(guild.getID());
                } else
                    forEach(i -> i.onGuildCreate(guild));
                break;
            case INTERACTION_CREATE:
                InteractionType interactionType = InteractionType.valueOf(data.getInt("type"));
                if (interactionType == InteractionType.APPLICATION_COMMAND) {
                    InteractionCommand commandInteraction = new InteractionCommand(data, bot);
                    forEach(i -> i.onSlashCommand(commandInteraction));
                    SlashCommandListener slashCommand = registeredCommand.get(commandInteraction.getCommandID());
                    if (slashCommand != null) slashCommand.onSlashCommand(commandInteraction);
                } else if (interactionType == InteractionType.MESSAGE_COMPONENT) {
                    InteractionComponent componentInteraction = new InteractionComponent(data, bot);
                    if (componentInteraction.getComponentType() == ComponentType.BUTTON)
                        forEach(i -> i.onButtonClick(componentInteraction));
                    else if (componentInteraction.getComponentType() == ComponentType.SELECT_MENU)
                        forEach(i -> i.onSelectMenu(componentInteraction));
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
            eventHandlers = cache;
        }
        eventHandlers[length++] = event;
    }

    public void setReadyGuild(JsonArray guilds) {
        readyGuild.clear();
        for (Object i : guilds)
            readyGuild.add(((JsonObject) i).getString("id"));
    }
}
