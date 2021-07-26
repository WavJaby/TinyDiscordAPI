package com.wavjaby.discord.object.activity;

import com.wavjaby.discord.object.guild.Emoji;
import com.wavjaby.json.JsonBuilder;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private String name;
    private int type;
    private String url;                     //?
    private long created_at;
    private OffsetDateTime timestamps;      //?
    private String application_id;          //?
    private String details;                 //?
    private String state;                   //?
    private Emoji emoji;                    //?
    private ActivityParty party;            //?
    private ActivityAssets assets;          //?
    private ActivitySecrets secrets;        //?
    private Boolean instance;               //?
    private Integer flags;                  //?
    private List<ActivityButton> buttons;   //?

    public Activity(int type, String name) {
        this.type = type;
        this.name = name;
        this.created_at = Instant.now().toEpochMilli();
    }

    public Activity(int type, String name, Emoji emoji) {
        this.type = type;
        this.name = name;
        this.emoji = emoji;
        this.created_at = Instant.now().toEpochMilli();
    }

    public Activity(int type, String name, String url, String details, ActivityAssets assets) {
        this.type = type;
        this.name = name;
        this.url = url;
        this.details = details;
        this.assets = assets;
        this.created_at = Instant.now().toEpochMilli();
    }

    public void addButton(ActivityButton button) {
        if (buttons == null)
            buttons = new ArrayList<>();
        buttons.add(button);
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        builder.append("name", name);
        builder.append("type", type);
        if (url != null)
            builder.append("url", url);
        builder.append("created_at", created_at);
        if (timestamps != null)
            builder.append("timestamps", timestamps.toString());
        if (application_id != null)
            builder.append("application_id", application_id);
        if (details != null)
            builder.append("details", details);
        if (state != null)
            builder.append("state", state);
        if (emoji != null)
            builder.append("emoji", emoji.toString(), true);
        if (party != null)
            builder.append("party", party.toString(), true);
        if (assets != null)
            builder.append("assets", assets.toString(), true);
        if (secrets != null)
            builder.append("secrets", secrets.toString(), true);
        if (instance != null)
            builder.append("instance", instance);
        if (flags != null)
            builder.append("flags", flags);
        if (buttons != null)
            builder.append("buttons", buttons.toString(), true);
        return builder.toString();
    }
}
