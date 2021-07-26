package com.wavjaby.discord.object.interaction;

public enum InteractionType {
    PING(1),
    APPLICATION_COMMAND(2),
    MESSAGE_COMPONENT(3),

    UNKNOWN(-1);

    private int value;
    InteractionType(int value){
        this.value = value;
    }

    public static InteractionType valueOf(int type) {
        if(type == 1)
            return PING;
        else if(type == 2)
            return APPLICATION_COMMAND;
        else if(type == 3)
            return MESSAGE_COMPONENT;
        return UNKNOWN;
    }
}
