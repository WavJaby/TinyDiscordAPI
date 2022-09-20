package com.wavjaby.discord.object.message.embed;

import com.wavjaby.json.JsonObject;

public class EmbedField {
    private String name;
    private String value;
    private boolean inline;

    public EmbedField(JsonObject fieldData) {
        name = fieldData.getString("name");
        value = fieldData.getString("value");
        if (fieldData.containsKey("inline"))
            inline = fieldData.getBoolean("inline");
    }

    public EmbedField(String name, String value, boolean inline) {
        this.name = name;
        this.value = value;
        this.inline = inline;
    }

    public EmbedField(boolean inline) {
        this.inline = inline;
    }

    public EmbedField(String name, String value) {
        this.name = name;
        this.value = value;
        this.inline = false;
    }

    @Override
    public String toString() {
        if (name == null || value == null)
            return "{\"name\":\"\u200b\",\"value\":\"\u200b\",\"inline\":" + inline + "}";
        return "{\"name\":\"" + name + "\",\"value\":\"" + value + "\",\"inline\":" + inline + "}";
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isInline() {
        return inline;
    }
}
