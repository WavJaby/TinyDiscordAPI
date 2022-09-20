package com.wavjaby.discord.object.interaction;

import com.wavjaby.discord.DiscordBot;
import com.wavjaby.discord.object.channel.Channel;
import com.wavjaby.discord.object.guild.Guild;
import com.wavjaby.discord.object.guild.Member;
import com.wavjaby.discord.object.message.MessageObject;
import com.wavjaby.discord.object.user.User;
import com.wavjaby.json.JsonObject;

import static com.wavjaby.discord.object.interaction.InteractionResponseType.DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE;

public class InteractionObject {
    //discord give
    private String id;
    private String application_id;
    private InteractionType interactionType;
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

        version = commandData.getInt("version");
        interactionType = InteractionType.valueOf(commandData.getInt("type"));
        token = commandData.getString("token");
        member = new Member(commandData.getJson("member"));
        user = new User(commandData.getJson("member"));
        id = commandData.getString("id");
        if (commandData.containsKey("guild_id"))
            guild = bot.getGuildByID(commandData.getString("guild_id"));
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

    public void replyLater(boolean ephemeral) {
        InteractionResponse response = new InteractionResponse(DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE);
        if (ephemeral) response.setEphemeral(true);
        reply(response);
    }

    public void replyLater() {
        InteractionResponse response = new InteractionResponse(DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE);
        response.setEphemeral(false);
        reply(response);
    }

    public Channel getChannel() {
        return channel;
    }

    public String getInteractionID() {
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

    public InteractionType getInteractionType() {
        return interactionType;
    }
}
