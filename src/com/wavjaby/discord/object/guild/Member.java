package com.wavjaby.discord.object.guild;

import com.wavjaby.discord.object.user.User;
import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private User user;                      //?
    private String nick;                    //?
    private List<String> roles;

    private OffsetDateTime joined_at;
    private OffsetDateTime premium_since;   //?
    private boolean deaf;
    private boolean mute;
    private boolean pending;                //?
    private String permissions;             //?

    private Guild memberGuild;

    public Member(JsonObject memberData, Guild memberGuild) {
        this.memberGuild = memberGuild;
        if (memberData.containsKey("user"))
            user = new User(memberData.get("user"));
        nick = memberData.getString("nick");

        roles = new ArrayList<>();
        for (Object i : memberData.getJsonArray("roles")) {
            roles.add((String) i);
        }

        joined_at = OffsetDateTime.parse(memberData.getString("joined_at"));
        if (memberData.notNull("premium_since"))
            premium_since = OffsetDateTime.parse(memberData.getString("premium_since"));
        deaf = memberData.getBoolean("deaf");
        mute = memberData.getBoolean("mute");
        if (memberData.containsKey("pending"))
            pending = memberData.getBoolean("pending");
        permissions = memberData.getString("permissions");
    }

    public Member(JsonObject memberData) {
        if (memberData.containsKey("user"))
            user = new User(memberData.get("user"));
        nick = memberData.getString("nick");

        roles = new ArrayList<>();
        for (Object i : memberData.getJsonArray("roles")) {
            roles.add((String) i);
        }

        joined_at = OffsetDateTime.parse(memberData.getString("joined_at"));
        if (memberData.notNull("premium_since"))
            premium_since = OffsetDateTime.parse(memberData.getString("premium_since"));
        deaf = memberData.getBoolean("deaf");
        mute = memberData.getBoolean("mute");
        if (memberData.containsKey("pending"))
            pending = memberData.getBoolean("pending");
        permissions = memberData.getString("permissions");
    }

    public String getID() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public String getNick() {
        if (nick == null)
            return user.getUsername();
        return nick;
    }


    public List<String> getRoles() {
        return roles;
    }

    public OffsetDateTime getJoined_at() {
        return joined_at;
    }

    public OffsetDateTime getPremium_since() {
        return premium_since;
    }

    public boolean isDeaf() {
        return deaf;
    }

    public boolean isMute() {
        return mute;
    }

    public boolean isPending() {
        return pending;
    }

    public String getPermissions() {
        return permissions;
    }

    public Guild getMemberGuild() {
        return memberGuild;
    }
}
