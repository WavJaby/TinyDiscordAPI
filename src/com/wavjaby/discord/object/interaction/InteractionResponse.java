package com.wavjaby.discord.object.interaction;

import static com.wavjaby.discord.object.interaction.InteractionCommandCallbackDataFlags.EPHEMERAL;

public class InteractionResponse {
    private Integer type;
    private InteractionCallback data;

    public InteractionResponse(Integer type, InteractionCallback data) {
        this.type = type;
        this.data = data;
    }

    public InteractionResponse(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if (data != null)
            return "{\"type\":" + type + ",\"data\":" + data.toString() + '}';
        return "{\"type\":" + type + '}';
    }

    public void setEphemeral(boolean enable) {
        if (enable) {
            if (data == null)
                data = new InteractionCallback();
            data.addFlag(EPHEMERAL);
        }
    }
}
