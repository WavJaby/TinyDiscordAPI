package com.wavjaby.discord.object.activity;

import com.wavjaby.json.JsonBuilder;

public class ActivityButton {
    private String label;
    private String url;

    public ActivityButton(String label, String url) {
        this.label = label;
        this.url = url;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        builder.append("label", label);
        builder.append("url", url);
        return builder.toString();
    }
}
