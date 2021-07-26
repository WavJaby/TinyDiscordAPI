package com.wavjaby.discord.object.message.embed;

import com.wavjaby.json.JsonObject;

public class EmbedImage {
    private String url;
    private String proxy_url;
    private int height;
    private int width;

    public EmbedImage(JsonObject imageData) {
        url = imageData.getString("url");
        proxy_url = imageData.getString("proxy_url");
        if (imageData.containsKey("height"))
            height = imageData.getInteger("height");
        if (imageData.containsKey("width"))
            width = imageData.getInteger("width");
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
