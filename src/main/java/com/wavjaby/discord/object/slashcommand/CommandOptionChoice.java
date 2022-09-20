package com.wavjaby.discord.object.slashcommand;

import com.wavjaby.json.JsonObject;

public class CommandOptionChoice {
    private String name;
    private Object value; //string or integer

    public CommandOptionChoice(JsonObject choiceData) {
        name = choiceData.getString("name");
        value = choiceData.getObject("value");
    }

    public CommandOptionChoice(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public CommandOptionChoice(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        String valueStr;
        if (value instanceof String)
            valueStr = '\"' + (String) value + '\"';
        else
            valueStr = String.valueOf(value);

        return "{\"name\":\"" + name + "\",\"value\":" + valueStr + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof CommandOptionChoice))
            return false;
        CommandOptionChoice target = (CommandOptionChoice) obj;
        return name.equals(target.name) && value.equals(target.value);
    }
}
