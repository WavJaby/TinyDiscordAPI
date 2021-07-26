package com.wavjaby.discord.object.message.component;

public enum ComponentType {
    ACTION_ROW(1),
    BUTTON(2),
    SELECT_MENU(3),

    UNKNOWN(-1);

    public int value;
    ComponentType(int value){
        this.value = value;
    }

    public static ComponentType valueOf(int type) {
        if(type == 1)
            return ACTION_ROW;
        else if(type == 2)
            return BUTTON;
        else if(type == 3)
            return SELECT_MENU;
        return UNKNOWN;
    }
}
