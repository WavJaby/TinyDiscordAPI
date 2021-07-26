package com.wavjaby.discord.object.message;

import com.wavjaby.discord.object.user.User;
import com.wavjaby.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private String id;
    private String name;
    private String icon;
    private String description;
    private List<String> rpc_origins;       //?
    private boolean bot_public;
    private boolean bot_require_code_grant;
    private String terms_of_service_url;    //?
    private String privacy_policy_url;      //?
    private User owner;                     //?
    private String summary;
    private String verify_key;
    private Team team;
    private String guild_id;                //?
    private String primary_sku_id;          //?
    private String slug;                    //?
    private String cover_image;             //?
    private int flags;                      //?

    public Application(JsonObject applicationData) {
        id = applicationData.getString("id");
        name = applicationData.getString("name");
        icon = applicationData.getString("icon");
        description = applicationData.getString("description");

        if (applicationData.containsKey("rpc_origins")) {
            rpc_origins = new ArrayList<>();
            for (Object i : applicationData.getJsonArray("rpc_origins"))
                rpc_origins.add((String) i);                       //?
        }

        if (applicationData.containsKey("bot_public"))
            bot_public = applicationData.getBoolean("bot_public");
        if (applicationData.containsKey("bot_require_code_grant"))
            bot_require_code_grant = applicationData.getBoolean("bot_require_code_grant");
        terms_of_service_url = applicationData.getString("terms_of_service_url");    //?
        privacy_policy_url = applicationData.getString("privacy_policy_url");      //?
        if (applicationData.containsKey("owner"))
            owner = new User(applicationData.get("owner"));        //?
        summary = applicationData.getString("summary");
        verify_key = applicationData.getString("verify_key");
        if (applicationData.containsKey("team"))
            team = new Team(applicationData.get("team"));
        guild_id = applicationData.getString("guild_id");                //?
        primary_sku_id = applicationData.getString("primary_sku_id");          //?
        slug = applicationData.getString("slug");                    //?
        cover_image = applicationData.getString("cover_image");             //?
        if (applicationData.containsKey("flags"))
            flags = applicationData.getInteger("flags");      //?
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getRpc_origins() {
        return rpc_origins;
    }

    public boolean isBot_public() {
        return bot_public;
    }

    public boolean isBot_require_code_grant() {
        return bot_require_code_grant;
    }

    public String getTerms_of_service_url() {
        return terms_of_service_url;
    }

    public String getPrivacy_policy_url() {
        return privacy_policy_url;
    }

    public User getOwner() {
        return owner;
    }

    public String getSummary() {
        return summary;
    }

    public String getVerify_key() {
        return verify_key;
    }

    public Team getTeam() {
        return team;
    }

    public String getGuild_id() {
        return guild_id;
    }

    public String getPrimary_sku_id() {
        return primary_sku_id;
    }

    public String getSlug() {
        return slug;
    }

    public String getCover_image() {
        return cover_image;
    }

    public int getFlags() {
        return flags;
    }
}
