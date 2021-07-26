package com.wavjaby.discord.object.slashcommand;

public class CommandOptionChoice {
    private String name;
    private Object value; //string or integer

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
}
