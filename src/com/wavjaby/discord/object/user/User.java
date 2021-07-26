package com.wavjaby.discord.object.user;

import com.wavjaby.discord.values.CDNEndpoints;
import com.wavjaby.json.JsonObject;

public class User {
    private String id;
    private String username;
    private String discriminator;
    private String avatar;

    //?
    private boolean bot;
    private boolean system;
    private boolean mfa_enabled;
    private String locale;
    private boolean verified;
    private String email;
    private int flags;
    private int premium_type;
    private int public_flags;

    public User(JsonObject userData) {
        id = userData.getString("id");
        username = userData.getString("username");
        discriminator = userData.getString("discriminator");
        avatar = CDNEndpoints.getUserAvatar(id,
                userData.getString("avatar"));

        if (userData.containsKey("bot"))
            bot = userData.getBoolean("bot");
        if (userData.containsKey("system"))
            system = userData.getBoolean("system");
        if (userData.containsKey("mfa_enabled"))
            mfa_enabled = userData.getBoolean("mfa_enabled");
        if (userData.containsKey("locale"))
            locale = userData.getString("locale");
        if (userData.containsKey("verified"))
            verified = userData.getBoolean("verified");
        if (userData.containsKey("email"))
            email = userData.getString("email");
        if (userData.containsKey("flags"))
            flags = userData.getInteger("flags");
        if (userData.containsKey("premium_type"))
            premium_type = userData.getInteger("premium_type");
        if (userData.containsKey("public_flags"))
            public_flags = userData.getInteger("public_flags");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        if (((User) obj).getId().equals(id))
            return true;
        return false;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isBot() {
        return bot;
    }

    public boolean isSystem() {
        return system;
    }

    public boolean isMfa_enabled() {
        return mfa_enabled;
    }

    public String getLocale() {
        return locale;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getEmail() {
        return email;
    }

    public int getFlags() {
        return flags;
    }

    public int getPremium_type() {
        return premium_type;
    }

    public int getPublic_flags() {
        return public_flags;
    }
}
