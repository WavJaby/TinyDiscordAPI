package com.wavjaby.discord.object.message.embed;

import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

public class EmbedAuthor {
    private String name;
    private String url;
    private String icon_url;
    private String proxy_icon_url;

    public EmbedAuthor(JsonObject providerData) {
        name = providerData.getString("name");
        url = providerData.getString("url");
        icon_url = providerData.getString("icon_url");
        proxy_icon_url = providerData.getString("proxy_icon_url");
    }

    public EmbedAuthor(String name, String icon_url) {
        this.name = name;
        this.icon_url = icon_url;
    }

    public EmbedAuthor(String name, String url, String icon_url, String proxy_icon_url) {
        this.name = name;
        this.url = url;
        this.icon_url = icon_url;
        this.proxy_icon_url = proxy_icon_url;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (name != null)
            builder.append("name", name);
        if (url != null)
            builder.append("url", url);
        if (icon_url != null)
            builder.append("icon_url", icon_url);
        if (proxy_icon_url != null)
            builder.append("proxy_icon_url", proxy_icon_url);
        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getProxy_icon_url() {
        return proxy_icon_url;
    }
}
