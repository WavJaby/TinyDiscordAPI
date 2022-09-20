package com.wavjaby.discord.object.message.component;

import com.wavjaby.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class SelectMenu extends Component {
    private String custom_id;
    private Boolean disabled;
    private List<SelectOptions> options;
    private String placeholder;
    private Integer min_value;
    private Integer max_value;

    public SelectMenu(JsonObject menuData) {
        custom_id = menuData.getString("custom_id");
        disabled = menuData.getBoolean("disabled");
        options = new ArrayList<>();
        for (Object i : menuData.getArray("options")) {
            options.add(new SelectOptions((JsonObject) i));
        }
        placeholder = menuData.getString("placeholder");
        if (menuData.containsKey("min_value"))
            min_value = menuData.getInt("min_value");
        if (menuData.containsKey("max_value"))
            max_value = menuData.getInt("max_value");
    }
}
