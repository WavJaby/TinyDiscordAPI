package com.wavjaby.discord.object.message.embed;

import com.wavjaby.json.JsonObject;

public class EmbedProvider {
    private String name;
    private String url;

    public EmbedProvider(JsonObject providerData){
        name = providerData.getString("name");
        url = providerData.getString("url");
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
