package com.wavjaby.discord.object.message.component;

import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

import static com.wavjaby.discord.object.message.component.ComponentType.ACTION_ROW;

public class Component {
    private ComponentType type;
    private List<Component> components;

    public Component(JsonObject componentData) {
        type = ComponentType.valueOf(componentData.getInt("type"));
        if (componentData.containsKey("components")) {
            components = new ArrayList<>();
            for (Object i : componentData.getArray("components")) {
                JsonObject data = (JsonObject) i;
                ComponentType thisType = ComponentType.valueOf(data.getInt("type"));
                if (thisType == ComponentType.BUTTON)
                    components.add(new Button(data));
                else if (thisType == ComponentType.SELECT_MENU)
                    components.add(new SelectMenu(data));
            }
        }
    }

    public Component() {
        this.type = ACTION_ROW;
        components = new ArrayList<>();
    }

    public void addButton(String label, String customID) {
        components.add(new Button(ButtonStyle.Success, label, customID));
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        builder.append("type", type.value);
        if (components != null)
            builder.append("components", components.toString(), true);
        return builder.toString();
    }
}
