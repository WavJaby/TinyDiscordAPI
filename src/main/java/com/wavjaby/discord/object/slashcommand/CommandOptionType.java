package com.wavjaby.discord.object.slashcommand;

public enum CommandOptionType {
    SUB_COMMAND(1),
    SUB_COMMAND_GROUP(2),
    STRING(3),
    INTEGER(4),
    BOOLEAN(5),
    USER(6),
    CHANNEL(7),
    ROLE(8),
    MENTIONABLE(9),
    NUMBER(10),
    ATTACHMENT(11),

    UNKNOWN(-1);


    public final int value;

    CommandOptionType(int value) {
        this.value = value;
    }

    public static CommandOptionType valueOf(int type) {
        for (CommandOptionType i : values())
            if (i.value == type)
                return i;
        return UNKNOWN;
    }
}
