package com.wavjaby.discord.object.activity;

import com.wavjaby.json.JsonBuilder;

public class ActivityAssets {
    private String large_image;
    private String large_text;
    private String small_image;
    private String small_text;

    public ActivityAssets(String large_text, String small_text) {
        this.large_text = large_text;
        this.small_text = small_text;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (large_image != null)
            builder.append("large_image", large_image);
        if (large_text != null)
            builder.append("large_text", large_text);
        if (small_image != null)
            builder.append("small_image", small_image);
        if (small_text != null)
            builder.append("small_text", small_text);
        return builder.toString();
    }
}
