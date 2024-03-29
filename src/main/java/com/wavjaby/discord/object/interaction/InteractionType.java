package com.wavjaby.discord.object.interaction;

public enum InteractionType {
    PING(1),
    APPLICATION_COMMAND(2),
    MESSAGE_COMPONENT(3),
    APPLICATION_COMMAND_AUTOCOMPLETE(4),
    MODAL_SUBMIT(5),

    UNKNOWN(-1);

    private final int value;
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
        else if(type == 4)
            return APPLICATION_COMMAND_AUTOCOMPLETE;
        else if(type == 5)
            return MODAL_SUBMIT;
        return UNKNOWN;
    }
}
