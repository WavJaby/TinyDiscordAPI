package com.wavjaby.discord.object.activity;

import com.wavjaby.json.JsonBuilder;

public class ActivitySecrets {
    private String join;
    private String spectate;
    private String match;

    public ActivitySecrets(String join, String spectate, String match) {
        this.join = join;
        this.spectate = spectate;
        this.match = match;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (join != null)
            builder.append("join", join);
        if (spectate != null)
            builder.append("spectate", spectate);
        if (match != null)
            builder.append("match", match);
        return builder.toString();
    }
}
