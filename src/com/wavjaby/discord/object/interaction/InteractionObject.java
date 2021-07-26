package com.wavjaby.discord.object.interaction;

import com.wavjaby.discord.DiscordBot;
import com.wavjaby.discord.object.channel.Channel;
import com.wavjaby.discord.object.guild.Guild;
import com.wavjaby.discord.object.guild.Member;
import com.wavjaby.discord.object.message.MessageObject;
import com.wavjaby.discord.object.message.component.ComponentType;
import com.wavjaby.discord.object.slashcommand.InteractionData;
import com.wavjaby.discord.object.user.User;
import com.wavjaby.json.JsonObject;

import static com.wavjaby.discord.object.interaction.InteractionResponseType.DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE;

public class InteractionObject {
    //discord give
    private String id;
    private String application_id;
    private InteractionType type;
    private InteractionData data;    //?
    private Guild guild;                    //?
    private Channel channel;                //?
    private Member member;                  //?
    private User user;                      //?
    private String token;
    private Integer version;
    private MessageObject message;          //?

    //other
    private DiscordBot bot;

    public InteractionObject(JsonObject commandData, DiscordBot bot) {
        this.bot = bot;

        version = commandData.getInteger("version");
        type = InteractionType.valueOf(commandData.getInteger("type"));
        token = commandData.getString("token");
        member = new Member(commandData.get("member"));
        id = commandData.getString("id");
        if(commandData.containsKey("guild_id"))
            guild = bot.getGuildByID(commandData.getString("guild_id"));
        if (commandData.containsKey("data"))
            data = new InteractionData(commandData.get("data"));
        channel = guild.getChannelByID(commandData.getString("channel_id"));
        application_id = commandData.getString("application_id");

    }

    public void editReply(MessageObject message) {
        guild.sendData("/webhooks/" + bot.application.getID() + "/" + token + "/messages/@original", "PATCH",
                message.toString());
    }

    public void reply(InteractionResponse interactionResponse) {
        guild.sendData("/interactions/" + id + "/" + token + "/callback", "POST",
                interactionResponse.toString());
    }

    public void replyLater() {
        reply(new InteractionResponse(DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE));
    }

    public Channel getChannel() {
        return channel;
    }

    public String getID() {
        return id;
    }

    public Guild getGuild() {
        return guild;
    }

    public String getToken() {
        return token;
    }

    public Member getMember() {
        return member;
    }

    public InteractionData getOption() {
        return data;
    }

    public ComponentType getComponentType() {
        return data.getType();
    }

    public String getCommand() {
        return data.getName();
    }

    public InteractionType getType() {
        return type;
    }
}
