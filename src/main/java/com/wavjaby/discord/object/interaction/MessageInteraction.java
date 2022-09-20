package com.wavjaby.discord.object.interaction;

import com.wavjaby.discord.object.user.User;
import com.wavjaby.json.JsonObject;

public class MessageInteraction {
    private String id;
    private int type;
    private String name;
    private User user;

    public MessageInteraction(JsonObject interactionData){
        id = interactionData.getString("id");
        type = interactionData.getInt("type");
        name = interactionData.getString("name");
        user = new User(interactionData.getJson("user"));
    }

}
