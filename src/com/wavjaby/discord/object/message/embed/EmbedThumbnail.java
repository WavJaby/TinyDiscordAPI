package com.wavjaby.discord.object.message.embed;

import com.wavjaby.json.JsonObject;

public class EmbedThumbnail{
    private String url;
    private String proxy_url;
    private int height;
    private int width;

    public EmbedThumbnail(JsonObject thumbnailData) {
        url = thumbnailData.getString("url");
        proxy_url = thumbnailData.getString("proxy_url");
        if (thumbnailData.containsKey("height"))
            height = thumbnailData.getInteger("height");
        if (thumbnailData.containsKey("width"))
            width = thumbnailData.getInteger("width");
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
