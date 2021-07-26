package com.wavjaby.discord.object.message.embed;

import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

public class EmbedFooter {
    private String text;
    private String icon_url;
    private String proxy_icon_url;

    public EmbedFooter(JsonObject footerData) {
        text = footerData.getString("text");
        icon_url = footerData.getString("icon_url");
        proxy_icon_url = footerData.getString("proxy_icon_url");
    }

    public EmbedFooter(String text) {
        this.text = text;
    }

    public EmbedFooter(String text, String icon_url) {
        this.text = text;
        this.icon_url = icon_url;
    }

    public EmbedFooter(String text, String icon_url, String proxy_icon_url) {
        this.text = text;
        this.icon_url = icon_url;
        this.proxy_icon_url = proxy_icon_url;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (text != null)
            builder.append("text", text);
        if (icon_url != null)
            builder.append("icon_url", icon_url);
        if (proxy_icon_url != null)
            builder.append("proxy_icon_url", proxy_icon_url);
        return builder.toString();
    }

    public String getText() {
        return text;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getProxy_icon_url() {
        return proxy_icon_url;
    }
}
