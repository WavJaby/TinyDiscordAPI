package com.wavjaby.discord.object.guild;

import com.wavjaby.discord.httpsender.DiscordDataSender;
import com.wavjaby.discord.object.channel.Channel;
import com.wavjaby.discord.object.slashcommand.SlashCommand;
import com.wavjaby.discord.object.slashcommand.SlashCommandListener;
import com.wavjaby.json.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Guild extends GuildStruct {
    private DiscordDataSender dataSender;
    private final Map<String, SlashCommand> guildSlashCommand;

    private final Map<String, SlashCommandListener> registeredCommand;

    public Guild(JsonObject guildData, DiscordDataSender dataSender, Map<String, SlashCommandListener> registeredCommand) {
        super(guildData);
        this.dataSender = dataSender;
        this.registeredCommand = registeredCommand;
        if (guildData.containsKey("members")) {
            members = new HashMap<>();
            for (Object i : guildData.getArray("members")) {                            //?
                Member member = new Member((JsonObject) i, this);
                members.put(member.getID(), member);
            }
        }
        if (guildData.containsKey("channels")) {
            channels = new HashMap<>();
            for (Object i : guildData.getArray("channels")) {                           //?
                Channel channel = new Channel((JsonObject) i, this, dataSender);
                channels.put(channel.getID(), channel);
            }
        }
        guildSlashCommand = dataSender.getGuildSlashCommand(id);
    }

    public void registerCommand(SlashCommand slashCommand) {
        SlashCommand guildCommand = guildSlashCommand.get(slashCommand.getName());
        if (!Objects.equals(guildCommand, slashCommand)) {
            slashCommand = dataSender.addGuildSlashCommand(id, slashCommand);
            guildSlashCommand.put(slashCommand.getName(), slashCommand);
        }
    }
    public void registerCommand(SlashCommandListener slashCommandListener) {
        SlashCommand guildCommand = guildSlashCommand.get(slashCommandListener.getName());
        if (!Objects.equals(guildCommand, slashCommandListener)) {
            SlashCommand slashCommand = guildCommand =
                    dataSender.addGuildSlashCommand(id, slashCommandListener);
            guildSlashCommand.put(slashCommand.getName(), slashCommand);
        }
        registeredCommand.put(guildCommand.getID(), slashCommandListener);
    }

    public void removeSlashCommandByID(String commandID) {
        dataSender.removeGuildSlashCommand(id, commandID);
    }

    public void removeSlashCommandByName(String name) {
        dataSender.removeGuildSlashCommand(id, guildSlashCommand.get(name).getID());
    }

    public SlashCommand getSlashCommandByName(String name) {
        return guildSlashCommand.get(name);
    }

    public void sendData(String url, String method, String data) {
        dataSender.sendRequestNoResponse(url, method, data);
    }
}
