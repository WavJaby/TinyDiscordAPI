package com.wavjaby.discord.object.guild;

import com.wavjaby.discord.object.channel.Channel;
import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class GuildStruct {
    //https://discord.com/developers/docs/resources/guild#guild-object
    protected String id;
    protected String name;
    protected String icon;
    protected String icon_hash;                   //?
    protected String splash;
    protected String discovery_splash;
    protected boolean owner;                      //?
    protected String owner_id;
    protected String permissions;                 //?
    protected String region;                      //?
    protected String afk_channel_id;
    protected int afk_timeout;
    protected boolean widget_enabled;             //?
    protected String widget_channel_id;           //?
    protected int verification_level;
    protected int default_message_notifications;
    protected int explicit_content_filter;
    protected Map<String, Role> roles;
    protected Map<String, Emoji> emojis;
    protected List<Features> features;
    protected int mfa_level;
    protected String application_id;
    protected String system_channel_id;
    protected int system_channel_flags;
    protected String rules_channel_id;
    protected OffsetDateTime joined_at;           //?
    protected boolean large;                      //?
    protected boolean unavailable;                //?
    protected int member_count;                   //?
    protected List<VoiceState> voice_states;      //?
    protected Map<String, Member> members;        //?
    protected Map<String, Channel> channels;      //?
    protected List<Channel> threads;              //?
    protected List<PresenceUpdate> presences;     //?
    protected int max_presences;                  //?
    protected int max_members;                    //?
    protected String vanity_url_code;
    protected String description;
    protected String banner;
    protected int premium_tier;
    protected int premium_subscription_count;     //?
    protected String preferred_locale;
    protected String public_updates_channel_id;
    protected int max_video_channel_users;        //?
    protected int approximate_member_count;       //?
    protected int approximate_presence_count;     //?
    protected WelcomeScreen welcome_screen;       //?
    protected int nsfw_level;
    protected List<Instance> stage_instances;     //?

    public GuildStruct(JsonObject guildData) {
        id = guildData.getString("id");
        name = guildData.getString("name");
        icon = guildData.getString("icon");
        icon_hash = guildData.getString("icon_hash");                                       //?
        splash = guildData.getString("splash");
        discovery_splash = guildData.getString("discovery_splash");
        if (guildData.containsKey("owner"))
            owner = guildData.getBoolean("owner");                                          //?
        owner_id = guildData.getString("owner_id");
        permissions = guildData.getString("permissions");                                   //?
        region = guildData.getString("region");                                             //?
        afk_channel_id = guildData.getString("afk_channel_id");
        afk_timeout = guildData.getInt("afk_timeout");
        if (guildData.containsKey("widget_enabled"))
            widget_enabled = guildData.getBoolean("widget_enabled");                        //?
        widget_channel_id = guildData.getString("widget_channel_id");                       //?
        verification_level = guildData.getInt("verification_level");
        default_message_notifications = guildData.getInt("default_message_notifications");
        explicit_content_filter = guildData.getInt("explicit_content_filter");

        //add roles
        roles = new HashMap<>();
        for (Object i : guildData.getArray("roles")) {
            Role role = new Role((JsonObject) i);
            roles.put(role.getID(), role);
        }

        emojis = new HashMap<>();
        for (Object i : guildData.getArray("emojis")) {
            Emoji emoji = new Emoji((JsonObject) i);
            emojis.put(emoji.getID(), emoji);
        }

        guildData.getArray("features");

        mfa_level = guildData.getInt("mfa_level");
        application_id = guildData.getString("application_id");
        system_channel_id = guildData.getString("system_channel_id");
        system_channel_flags = guildData.getInt("system_channel_flags");
        rules_channel_id = guildData.getString("rules_channel_id");
        if (guildData.containsKey("joined_at"))
            joined_at = OffsetDateTime.parse(guildData.getString("joined_at"));             //?
        if (guildData.containsKey("large"))
            large = guildData.getBoolean("large");                                          //?
        if (guildData.containsKey("unavailable"))
            unavailable = guildData.getBoolean("unavailable");                              //?
        if (guildData.containsKey("member_count"))
            member_count = guildData.getInt("member_count");                                //?

        if (guildData.containsKey("voice_states"))
            guildData.getArray("voice_states");                                             //?
        if (guildData.containsKey("channels"))
            guildData.getArray("channels");                                                 //?
        if (guildData.containsKey("threads"))
            guildData.getArray("threads");                                                  //?
        if (guildData.containsKey("presences"))
            guildData.getArray("presences");                                                //?

        if (guildData.containsKey("max_presences"))
            max_presences = guildData.getInt("max_presences");                              //?
        if (guildData.containsKey("max_members"))
            max_members = guildData.getInt("max_members");                                  //?
        vanity_url_code = guildData.getString("vanity_url_code");
        description = guildData.getString("description");
        banner = guildData.getString("banner");
        premium_tier = guildData.getInt("premium_tier");
        if (guildData.containsKey("premium_subscription_count"))
            premium_subscription_count = guildData.getInt("premium_subscription_count");    //?
        preferred_locale = guildData.getString("preferred_locale");
        public_updates_channel_id = guildData.getString("public_updates_channel_id");
        if (guildData.containsKey("max_video_channel_users"))
            max_video_channel_users = guildData.getInt("max_video_channel_users");          //?
        if (guildData.containsKey("approximate_member_count"))
            approximate_member_count = guildData.getInt("approximate_member_count");        //?
        if (guildData.containsKey("approximate_presence_count"))
            approximate_presence_count = guildData.getInt("approximate_presence_count");    //?
        if (guildData.containsKey("welcome_screen"))
            welcome_screen = new WelcomeScreen(guildData.getJson("welcome_screen"));        //?
        nsfw_level = guildData.getInt("nsfw_level");
        if (guildData.containsKey("stage_instances"))
            guildData.getArray("stage_instances");     //?
    }

    public Emoji getEmojiByID(String id) {
        return emojis.get(id);
    }

    public Role getRoleByID(String roleID) {
        return roles.get(roleID);
    }

    public Channel getChannelByID(String channelID) {
        return channels.get(channelID);
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
