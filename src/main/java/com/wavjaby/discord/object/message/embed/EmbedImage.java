package com.wavjaby.discord.object.message.embed;

import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

public class EmbedImage {
    private String url;
    private String proxy_url;
    private Integer width;
    private Integer height;

    public EmbedImage(JsonObject imageData) {
        url = imageData.getString("url");
        proxy_url = imageData.getString("proxy_url");
        if (imageData.containsKey("width"))
            width = imageData.getInt("width");
        if (imageData.containsKey("height"))
            height = imageData.getInt("height");
    }

    public EmbedImage(String url, int width, int height) {
        this.url = url;
        this.height = height;
        this.width = width;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        builder.append("url", url);
        if (proxy_url != null)
            builder.append("proxy_url", proxy_url);
        if (width != null)
            builder.append("width", width);
        if (height != null)
            builder.append("height", height);
        return builder.toString();
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
