package com.wavjaby.discord.object.message.embed;

import com.wavjaby.json.JsonObject;

public class EmbedVideo {

    private String url;
    private String proxy_url;
    private int height;
    private int width;

    public EmbedVideo(JsonObject videoData) {
        url = videoData.getString("url");
        proxy_url = videoData.getString("proxy_url");
        if (videoData.containsKey("height"))
            height = videoData.getInteger("height");
        if (videoData.containsKey("width"))
            width = videoData.getInteger("width");
    }

    public String getUrl() {
        return url;
    }

    public String getProxy_url() {
        return proxy_url;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
