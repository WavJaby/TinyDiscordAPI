package com.wavjaby.discord.object.channel;

import com.wavjaby.discord.MultiPartData;
import com.wavjaby.discord.httpsender.DiscordDataSender;
import com.wavjaby.discord.object.guild.Guild;
import com.wavjaby.discord.object.message.MessageObject;
import com.wavjaby.discord.object.permission.PermissionOverwrite;
import com.wavjaby.discord.object.user.User;
import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class Channel {
    private String id;
    private int type;
    private String guild_id;                        //?
    private int position;                           //?
    private Map<String, PermissionOverwrite> permission_overwrites;       //?
    private String name;                            //?
    private String topic;                           //?
    private boolean nsfw;                           //?
    private String last_message_id;                 //?
    private int bitrate;                            //?
    private int user_limit;                         //?
    private int rate_limit_per_user;                //?
    private Map<String, User> recipients;           //?
    private String icon;                            //?
    private String owner_id;                        //?
    private String application_id;                  //?
    private String parent_id;                       //?
    private OffsetDateTime last_pin_timestamp;      //?
    private String rtc_region;                      //?
    private int video_quality_mode;                 //?
    private int message_count;                      //?
    private int member_count;                       //?
    private ThreadMetadata thread_metadata;         //?
    private ThreadMember member;                    //?
    private int default_auto_archive_duration;      //?

    //other
    private final DiscordDataSender dataSender;
    private Guild guild;
    private Map<String, MessageObject> messages;

    public Channel(JsonObject channelData, Guild guild, DiscordDataSender dataSender) {
        this.dataSender = dataSender;
        this.guild = guild;
        messages = new HashMap<>();

        id = channelData.getString("id");
        type = channelData.getInt("type");
        if (channelData.containsKey("guild_id"))
            guild_id = channelData.getString("guild_id");                        //?
        if (channelData.containsKey("position"))
            position = channelData.getInt("position");                           //?
        permission_overwrites = new HashMap<>();
        if (channelData.containsKey("permission_overwrites"))
            for (Object i : channelData.getArray("permission_overwrites")) {      //?
                PermissionOverwrite overwrite = new PermissionOverwrite((JsonObject) i);
                permission_overwrites.put(overwrite.getId(), overwrite);
            }
        name = channelData.getString("name");                            //?
        topic = channelData.getString("topic");                           //?
        if (channelData.containsKey("nsfw"))
            nsfw = channelData.getBoolean("nsfw");                           //?
        last_message_id = channelData.getString("last_message_id");                 //?
        if (channelData.containsKey("bitrate"))
            bitrate = channelData.getInt("bitrate");                            //?
        if (channelData.containsKey("user_limit"))
            user_limit = channelData.getInt("user_limit");                         //?
        if (channelData.containsKey("rate_limit_per_user"))
            rate_limit_per_user = channelData.getInt("rate_limit_per_user");                //?
        recipients = new HashMap<>();
        if (channelData.containsKey("recipients"))
            for (Object i : channelData.getArray("recipients")) {      //?
                User user = new User((JsonObject) i);
                recipients.put(user.getId(), user);
            }
        icon = channelData.getString("icon");                            //?
        owner_id = channelData.getString("owner_id");                        //?
        application_id = channelData.getString("application_id");                  //?
        parent_id = channelData.getString("parent_id");                       //?
        if (channelData.containsKey("last_pin_timestamp"))
            last_pin_timestamp = OffsetDateTime.parse(channelData.getString("last_pin_timestamp"));      //?
        rtc_region = channelData.getString("rtc_region");                      //?
        if (channelData.containsKey("video_quality_mode"))
            video_quality_mode = channelData.getInt("video_quality_mode");                 //?
        if (channelData.containsKey("message_count"))
            message_count = channelData.getInt("message_count");                      //?
        if (channelData.containsKey("member_count"))
            member_count = channelData.getInt("member_count");                       //?
        if (channelData.containsKey("thread_metadata"))
            thread_metadata = new ThreadMetadata(channelData.getJson("thread_metadata"));         //?
        if (channelData.containsKey("member"))
            member = new ThreadMember(channelData.getJson("member"));                    //?
        if (channelData.containsKey("default_auto_archive_duration"))
            default_auto_archive_duration = channelData.getInt("default_auto_archive_duration");      //?
    }

    public void sendMessage(MessageObject messageObject) {
        dataSender.sendMessage(id, messageObject);
    }

    public MessageObject getMessageByID(String messageID) {
        MessageObject message = messages.get(messageID);
        if (message == null) {
            String result = dataSender.getMessage(id, messageID);
            if (result == null)
                return null;
            message = new MessageObject(new JsonObject(result), guild, dataSender);
            messages.put(messageID, message);
        }
        return message;
    }

    public void sendMultiPart(String endpoint, String method, MultiPartData multiPart) {
        endpoint = endpoint.replace("{channel.id}", id);
        dataSender.sendMultiPart(endpoint, method, multiPart);
    }

    //getter

    public String getID() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getGuildID() {
        return guild_id;
    }

    public int getPosition() {
        return position;
    }

    public Map<String, PermissionOverwrite> getPermission_overwrites() {
        return permission_overwrites;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public String getLast_message_id() {
        return last_message_id;
    }

    public int getBitrate() {
        return bitrate;
    }

    public int getUser_limit() {
        return user_limit;
    }

    public int getRate_limit_per_user() {
        return rate_limit_per_user;
    }

    public Map<String, User> getRecipients() {
        return recipients;
    }

    public String getIcon() {
        return icon;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public String getApplication_id() {
        return application_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public OffsetDateTime getLast_pin_timestamp() {
        return last_pin_timestamp;
    }

    public String getRtc_region() {
        return rtc_region;
    }

    public int getVideo_quality_mode() {
        return video_quality_mode;
    }

    public int getMessage_count() {
        return message_count;
    }

    public int getMember_count() {
        return member_count;
    }

    public ThreadMetadata getThread_metadata() {
        return thread_metadata;
    }

    public ThreadMember getMember() {
        return member;
    }

    public int getDefault_auto_archive_duration() {
        return default_auto_archive_duration;
    }
}
