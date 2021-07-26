package com.wavjaby.discord.object.message;

import com.wavjaby.discord.httpsender.DiscordDataSender;
import com.wavjaby.discord.object.channel.Channel;
import com.wavjaby.discord.object.channel.ChannelMention;
import com.wavjaby.discord.object.guild.Guild;
import com.wavjaby.discord.object.guild.Member;
import com.wavjaby.discord.object.guild.Role;
import com.wavjaby.discord.object.interaction.MessageInteraction;
import com.wavjaby.discord.object.message.component.Component;
import com.wavjaby.discord.object.message.embed.Embed;
import com.wavjaby.discord.object.user.User;
import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageObject {
    //https://discord.com/developers/docs/resources/channel#message-object
    private String id;
    private String channel_id;
    private String guild_id;                            //?
    private User author;
    private Member member;                              //?
    private String content;
    private OffsetDateTime timestamp;
    private OffsetDateTime edited_timestamp;
    private Boolean tts;
    private Boolean mention_everyone;
    private Map<String, Member> mentions;
    private Map<String, Role> mention_roles;
    private List<ChannelMention> mention_channels;      //?
    private List<Attachment> attachments;
    private List<Embed> embeds;
    private List<Reaction> reactions;                   //?
    private String nonce;                               //?
    private Boolean pinned;
    private String webhook_id;                          //?
    private Integer type;
    private MessageActivity activity;                   //?
    private Application application;                    //?
    private String application_id;                      //?
    private MessageReference message_reference;         //?
    private Integer flags;                              //?
    private MessageObject referenced_message;           //?
    private MessageInteraction interaction;             //?
    private Channel thread;                             //?
    private List<Component> components;                 //?
    private List<MessageStickerItem> sticker_items;     //?
    private List<MessageSticker> stickers;              //?

    //send
    private String payload_json;
    private List<AllowedMention> allowed_mentions;

    //other
    private Guild guild;
    private Channel channel;
    private DiscordDataSender dataSender;

    public MessageObject() {
    }

    public MessageObject(JsonObject messageData, Guild guild, DiscordDataSender dataSender) {
        this.dataSender = dataSender;

        id = messageData.getString("id");
        channel_id = messageData.getString("channel_id");
        channel = guild.getChannelByID(channel_id);
        guild_id = messageData.getString("guild_id");
        this.guild = guild;
        author = new User(messageData.get("author"));
        if (messageData.containsKey("member"))
            member = new Member(messageData.get("member"), guild);
        content = messageData.getString("content");
        if (messageData.containsKey("timestamp"))
            timestamp = OffsetDateTime.parse(messageData.getString("timestamp"));
        if (messageData.notNull("edited_timestamp"))
            edited_timestamp = OffsetDateTime.parse(messageData.getString("edited_timestamp"));
        tts = messageData.getBoolean("tts");
        mention_everyone = messageData.getBoolean("mention_everyone");
        mentions = new HashMap<>();
        for (Object i : messageData.getJsonArray("mentions")) {
            Member member = new Member((JsonObject) i, guild);
            mentions.put(member.getID(), member);
        }
        mention_roles = new HashMap<>();
        for (Object i : messageData.getJsonArray("mention_roles")) {
            String roleID = ((JsonObject) i).getString("id");
            mention_roles.put(roleID, guild.getRoleByID(roleID));
        }
        if (messageData.containsKey("mention_channels")) {
            mention_channels = new ArrayList<>();
            for (Object i : messageData.getJsonArray("mention_channels")) {
                mention_channels.add(new ChannelMention((JsonObject) i));
            }
        }
        attachments = new ArrayList<>();
        for (Object i : messageData.getJsonArray("attachments")) {
            attachments.add(new Attachment((JsonObject) i));
        }
        embeds = new ArrayList<>();
        for (Object i : messageData.getJsonArray("embeds")) {
            embeds.add(new Embed((JsonObject) i));
        }
        if (messageData.containsKey("reactions")) {
            reactions = new ArrayList<>();
            for (Object i : messageData.getJsonArray("reactions")) {
                reactions.add(new Reaction((JsonObject) i));
            }
        }
        nonce = messageData.getString("nonce");
        pinned = messageData.getBoolean("pinned");
        webhook_id = messageData.getString("webhook_id");
        type = messageData.getInteger("type");
        if (messageData.containsKey("activity"))
            activity = new MessageActivity(messageData.get("activity"));
        if (messageData.containsKey("application"))
            application = new Application(messageData.get("application"));
        application_id = messageData.getString("application_id");
        if (messageData.containsKey("message_reference"))
            message_reference = new MessageReference(messageData.get("message_reference"));
        flags = messageData.getInteger("flags");
        if (messageData.notNull("referenced_message"))
            referenced_message = channel.getMessageByID(messageData.get("referenced_message").getString("id"));
        if (messageData.containsKey("interaction"))
            interaction = new MessageInteraction(messageData.get("interaction"));
        if (messageData.containsKey("thread"))
            thread = guild.getChannelByID(messageData.get("thread").getString("id"));
        if (messageData.containsKey("components")) {
            components = new ArrayList<>();
            for (Object i : messageData.getJsonArray("components")) {
                components.add(new Component((JsonObject) i));
            }
        }
        if (messageData.containsKey("sticker_items")) {
            sticker_items = new ArrayList<>();
            for (Object i : messageData.getJsonArray("sticker_items")) {
                sticker_items.add(new MessageStickerItem((JsonObject) i));
            }
        }
        if (messageData.containsKey("stickers")) {
            stickers = new ArrayList<>();
            for (Object i : messageData.getJsonArray("stickers")) {
                stickers.add(new MessageSticker((JsonObject) i));
            }
        }
    }

    public MessageObject(String content, boolean tts, List<Embed> embeds, String payload_json, List<AllowedMention> allowed_mentions, MessageReference message_reference, List<Component> components) {
        this.content = content;
        this.tts = tts;
        this.embeds = embeds;
        this.payload_json = payload_json;
        this.allowed_mentions = allowed_mentions;
        this.message_reference = message_reference;
        this.components = components;
    }

    public MessageObject(String content) {
        this.content = content;
    }

    //setter
    public void setContent(String content) {
        this.content = content;
    }

    public void addEmbed(Embed embed) {
        if (embeds == null)
            embeds = new ArrayList<>();
        embeds.add(embed);
    }

    public Embed addEmbed() {
        if (embeds == null)
            embeds = new ArrayList<>();
        Embed embed = new Embed();
        embeds.add(embed);
        return embed;
    }

    public Component addActionRow() {
        if (components == null)
            components = new ArrayList<>();
        Component component = new Component();
        components.add(component);
        return component;
    }

    public void setEmbed(List<Embed> embeds) {
        this.embeds = embeds;
    }

    public void editFirstEmbed(Embed embed) {
        embeds.set(0, embed);
        checkEdit();
    }

    private void checkEdit(){
        if(channel_id != null && id != null)
            dataSender.editMessage(this);
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (content != null)
            builder.append("content", content);
        if (tts != null)
            builder.append("tts", tts);
        if (embeds != null)
            builder.append("embeds", embeds.toString(), true);
        if (payload_json != null)
            builder.append("payload_json", payload_json);
        if (allowed_mentions != null)
            builder.append("allowed_mentions", allowed_mentions.toString(), true);
        if (message_reference != null)
            builder.append("message_reference", message_reference.toString(), true);
        if (components != null)
            builder.append("components", components.toString(), true);
        return builder.toString();
    }

    //getter
    public Channel getChannel() {
        return guild.getChannelByID(channel_id);
    }

    public Embed getEmbed(int index) {
        return embeds.get(index);
    }

    public Embed getFirstEmbed() {
        return embeds.get(0);
    }

    public String getID() {
        return id;
    }

    public String getChannelID() {
        return channel_id;
    }

    public String getGuildID() {
        return guild_id;
    }

    public User getAuthor() {
        return author;
    }

    public Member getMember() {
        return member;
    }

    public String getContent() {
        return content;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public OffsetDateTime getEdited_timestamp() {
        return edited_timestamp;
    }

    public boolean isTts() {
        return tts;
    }

    public boolean isMention_everyone() {
        return mention_everyone;
    }

    public Map<String, Member> getMentions() {
        return mentions;
    }

    public Map<String, Role> getMention_roles() {
        return mention_roles;
    }

    public List<ChannelMention> getMention_channels() {
        return mention_channels;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public List<Embed> getEmbeds() {
        return embeds;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public String getNonce() {
        return nonce;
    }

    public boolean isPinned() {
        return pinned;
    }

    public String getWebhook_id() {
        return webhook_id;
    }

    public int getType() {
        return type;
    }

    public MessageActivity getActivity() {
        return activity;
    }

    public Application getApplication() {
        return application;
    }

    public String getApplication_id() {
        return application_id;
    }

    public MessageReference getMessage_reference() {
        return message_reference;
    }

    public int getFlags() {
        return flags;
    }

    public MessageObject getReferenced_message() {
        return referenced_message;
    }

    public MessageInteraction getInteraction() {
        return interaction;
    }

    public Channel getThread() {
        return thread;
    }

    public List<Component> getComponents() {
        return components;
    }

    public List<MessageStickerItem> getSticker_items() {
        return sticker_items;
    }

    public List<MessageSticker> getStickers() {
        return stickers;
    }
}
