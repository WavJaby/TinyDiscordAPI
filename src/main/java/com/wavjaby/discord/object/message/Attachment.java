package com.wavjaby.discord.object.message;

import com.wavjaby.json.JsonObject;

public class Attachment {
    private String id;
    private String filename;
    private String content_type;
    private int size;
    private String url;
    private String proxy_url;
    private int height;
    private int width;

    Attachment(JsonObject attachmentData) {
        id = attachmentData.getString("id");
        filename = attachmentData.getString("filename");
        content_type = attachmentData.getString("content_type");
        size = attachmentData.getInt("size");
        url = attachmentData.getString("url");
        proxy_url = attachmentData.getString("proxy_url");
        if(attachmentData.containsKey("height"))
            height = attachmentData.getInt("height");
        if(attachmentData.containsKey("width"))
            width = attachmentData.getInt("width");
    }

    public String getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getContent_type() {
        return content_type;
    }

    public int getSize() {
        return size;
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
