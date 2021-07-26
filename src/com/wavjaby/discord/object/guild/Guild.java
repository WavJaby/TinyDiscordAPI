package com.wavjaby.discord.object.guild;

import com.wavjaby.discord.httpsender.DiscordDataSender;
import com.wavjaby.discord.object.channel.Channel;
import com.wavjaby.discord.object.slashcommand.SlashCommand;
import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Guild {
    //https://discord.com/developers/docs/resources/guild#guild-object
    private String id;
    private String name;
    private String icon;
    private String icon_hash;                   //?
    private String splash;
    private String discovery_splash;
    private boolean owner;                      //?
    private String owner_id;
    private String permissions;                 //?
    private String region;                      //?
    private String afk_channel_id;
    private int afk_timeout;
    private boolean widget_enabled;             //?
    private String widget_channel_id;           //?
    private int verification_level;
    private int default_message_notifications;
    private int explicit_content_filter;
    private Map<String, Role> roles;
    private Map<String, Emoji> emojis;
    private List<Features> features;
    private int mfa_level;
    private String application_id;
    private String system_channel_id;
    private int system_channel_flags;
    private String rules_channel_id;
    private OffsetDateTime joined_at;           //?
    private boolean large;                      //?
    private boolean unavailable;                //?
    private int member_count;                   //?
    private List<VoiceState> voice_states;      //?
    private Map<String, Member> members;        //?
    private Map<String, Channel> channels;      //?
    private List<Channel> threads;              //?
    private List<PresenceUpdate> presences;     //?
    private int max_presences;                  //?
    private int max_members;                    //?
    private String vanity_url_code;
    private String description;
    private String banner;
    private int premium_tier;
    private int premium_subscription_count;     //?
    private String preferred_locale;
    private String public_updates_channel_id;
    private int max_video_channel_users;        //?
    private int approximate_member_count;       //?
    private int approximate_presence_count;     //?
    private WelcomeScreen welcome_screen;       //?
    private int nsfw_level;
    private List<Instance> stage_instances;     //?

    //other
    private final DiscordDataSender dataSender;

    public Guild(JsonObject guildData, DiscordDataSender dataSender) {
        this.dataSender = dataSender;

        id = guildData.getString("id");
        name = guildData.getString("name");
        icon = guildData.getString("icon");
        icon_hash = guildData.getString("icon_hash");                   //?
        splash = guildData.getString("splash");
        discovery_splash = guildData.getString("discovery_splash");
        if (guildData.containsKey("owner"))
            owner = guildData.getBoolean("owner");                      //?
        owner_id = guildData.getString("owner_id");
        permissions = guildData.getString("permissions");                 //?
        region = guildData.getString("region");                      //?
        afk_channel_id = guildData.getString("afk_channel_id");
        afk_timeout = guildData.getInteger("afk_timeout");
        if (guildData.containsKey("widget_enabled"))
            widget_enabled = guildData.getBoolean("widget_enabled");             //?
        widget_channel_id = guildData.getString("widget_channel_id");           //?
        verification_level = guildData.getInteger("verification_level");
        default_message_notifications = guildData.getInteger("default_message_notifications");
        explicit_content_filter = guildData.getInteger("explicit_content_filter");

        //add roles
        roles = new HashMap<>();
        for (Object i : guildData.getJsonArray("roles")) {
            Role role = new Role((JsonObject) i);
            roles.put(role.getID(), role);
        }

        emojis = new HashMap<>();
        for (Object i : guildData.getJsonArray("emojis")) {
            Emoji emoji = new Emoji((JsonObject) i);
            emojis.put(emoji.getID(), emoji);
        }

        guildData.getJsonArray("features");

        mfa_level = guildData.getInteger("mfa_level");
        application_id = guildData.getString("application_id");
        system_channel_id = guildData.getString("system_channel_id");
        system_channel_flags = guildData.getInteger("system_channel_flags");
        rules_channel_id = guildData.getString("rules_channel_id");
        if (guildData.containsKey("joined_at"))
            joined_at = OffsetDateTime.parse(guildData.getString("joined_at"));     //?
        if (guildData.containsKey("large"))
            large = guildData.getBoolean("large");                              //?
        if (guildData.containsKey("unavailable"))
            unavailable = guildData.getBoolean("unavailable");                  //?
        if (guildData.containsKey("member_count"))
            member_count = guildData.getInteger("member_count");                //?

        if (guildData.containsKey("voice_states"))
            guildData.getJsonArray("voice_states");         //?
        members = new HashMap<>();
        if (guildData.containsKey("members"))
            for (Object i : guildData.getJsonArray("members")) {              //?
                Member member = new Member((JsonObject) i, this);
                members.put(member.getID(), member);
            }
        channels = new HashMap<>();
        if (guildData.containsKey("channels"))
            for (Object i : guildData.getJsonArray("channels")) {              //?
                Channel channel = new Channel((JsonObject) i, this, dataSender);
                channels.put(channel.getID(), channel);
            }
        if (guildData.containsKey("channels"))
            guildData.getJsonArray("channels");             //?
        if (guildData.containsKey("threads"))
            guildData.getJsonArray("threads");              //?
        if (guildData.containsKey("presences"))
            guildData.getJsonArray("presences");            //?

        if (guildData.containsKey("max_presences"))
            max_presences = guildData.getInteger("max_presences");                  //?
        if (guildData.containsKey("max_members"))
            max_members = guildData.getInteger("max_members");                    //?
        vanity_url_code = guildData.getString("vanity_url_code");
        description = guildData.getString("description");
        banner = guildData.getString("banner");
        premium_tier = guildData.getInteger("premium_tier");
        if (guildData.containsKey("premium_subscription_count"))
            premium_subscription_count = guildData.getInteger("premium_subscription_count");     //?
        preferred_locale = guildData.getString("preferred_locale");
        public_updates_channel_id = guildData.getString("public_updates_channel_id");
        if (guildData.containsKey("max_video_channel_users"))
            max_video_channel_users = guildData.getInteger("max_video_channel_users");        //?
        if (guildData.containsKey("approximate_member_count"))
            approximate_member_count = guildData.getInteger("approximate_member_count");       //?
        if (guildData.containsKey("approximate_presence_count"))
            approximate_presence_count = guildData.getInteger("approximate_presence_count");     //?
        if (guildData.containsKey("welcome_screen"))
            welcome_screen = new WelcomeScreen(guildData.get("welcome_screen"));       //?
        nsfw_level = guildData.getInteger("nsfw_level");
        if (guildData.containsKey("stage_instances"))
            guildData.getJsonArray("stage_instances");     //?
    }

    public Role getRoleByID(String roleID) {
        return roles.get(roleID);
    }

    public Channel getChannelByID(String channelID) {
        return channels.get(channelID);
    }

    public void addSlashCommand(SlashCommand slashCommand) {
        dataSender.addGuildSlashCommand(id, slashCommand);
    }

    public void removeSlashCommand(SlashCommand state) {
//        dataSender.getGuildSlashCommand(id);
    }

    public void sendData(String url, String method, String data) {
        dataSender.sendRequest(url, method, data);
    }

    //getter

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getIcon_hash() {
        return icon_hash;
    }

    public String getSplash() {
        return splash;
    }

    public String getDiscovery_splash() {
        return discovery_splash;
    }

    public boolean isOwner() {
        return owner;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public String getPermissions() {
        return permissions;
    }

    public String getRegion() {
        return region;
    }

    public String getAfk_channel_id() {
        return afk_channel_id;
    }

    public int getAfk_timeout() {
        return afk_timeout;
    }

    public boolean isWidget_enabled() {
        return widget_enabled;
    }

    public String getWidget_channel_id() {
        return widget_channel_id;
    }

    public int getVerification_level() {
        return verification_level;
    }

    public int getDefault_message_notifications() {
        return default_message_notifications;
    }

    public int getExplicit_content_filter() {
        return explicit_content_filter;
    }

    public Map<String, Emoji> getEmojis() {
        return emojis;
    }

    public List<Features> getFeatures() {
        return features;
    }

    public int getMfa_level() {
        return mfa_level;
    }

    public String getApplication_id() {
        return application_id;
    }

    public String getSystem_channel_id() {
        return system_channel_id;
    }

    public int getSystem_channel_flags() {
        return system_channel_flags;
    }

    public String getRules_channel_id() {
        return rules_channel_id;
    }

    public OffsetDateTime getJoined_at() {
        return joined_at;
    }

    public boolean isLarge() {
        return large;
    }

    public boolean isUnavailable() {
        return unavailable;
    }

    public int getMember_count() {
        return member_count;
    }

    public List<VoiceState> getVoice_states() {
        return voice_states;
    }

    public Map<String, Member> getMembers() {
        return members;
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    public List<Channel> getThreads() {
        return threads;
    }

    public List<PresenceUpdate> getPresences() {
        return presences;
    }

    public int getMax_presences() {
        return max_presences;
    }

    public int getMax_members() {
        return max_members;
    }

    public String getVanity_url_code() {
        return vanity_url_code;
    }

    public String getDescription() {
        return description;
    }

    public String getBanner() {
        return banner;
    }

    public int getPremium_tier() {
        return premium_tier;
    }

    public int getPremium_subscription_count() {
        return premium_subscription_count;
    }

    public String getPreferred_locale() {
        return preferred_locale;
    }

    public String getPublic_updates_channel_id() {
        return public_updates_channel_id;
    }

    public int getMax_video_channel_users() {
        return max_video_channel_users;
    }

    public int getApproximate_member_count() {
        return approximate_member_count;
    }

    public int getApproximate_presence_count() {
        return approximate_presence_count;
    }

    public WelcomeScreen getWelcome_screen() {
        return welcome_screen;
    }

    public int getNsfw_level() {
        return nsfw_level;
    }

    public List<Instance> getStage_instances() {
        return stage_instances;
    }
}
