package com.wavjaby.discord.object.message.component;

import com.wavjaby.discord.object.guild.Emoji;
import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

public class Button extends Component {
    private ComponentType type;
    private Integer style;
    private String label;
    private Emoji emoji;
    private String custom_id;
    private String url;
    private Boolean disabled;

    public Button(JsonObject buttonData) {
        type = ComponentType.BUTTON;
        style = buttonData.getInt("style");
        label = buttonData.getString("label");
        if (buttonData.containsKey("emoji"))
            emoji = new Emoji(buttonData.getJson("emoji"));
        custom_id = buttonData.getString("custom_id");
        url = buttonData.getString("url");
        if (buttonData.containsKey("disabled"))
        disabled = buttonData.getBoolean("disabled");
    }

    public Button(Integer style, String label, Emoji emoji, String custom_id, String url, Boolean disabled) {
        this.type = ComponentType.BUTTON;
        this.style = style;
        this.label = label;
        this.emoji = emoji;
        this.custom_id = custom_id;
        this.url = url;
        this.disabled = disabled;
    }

    public Button(Integer style, String label, String custom_id) {
        this.type = ComponentType.BUTTON;
        this.style = style;
        this.label = label;
        this.custom_id = custom_id;
    }


    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        builder.append("type", type.value);
        if (style != null)
            builder.append("style", style);
        builder.append("label", label);
        if (emoji != null)
            builder.append("emoji", emoji.toString());
        builder.append("custom_id", custom_id);
        if (url != null)
            builder.append("url", url);
        if (disabled != null)
            builder.append("disabled", disabled);
        return builder.toString();
    }

}
