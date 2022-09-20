package com.wavjaby.discord.object.guild;

import com.wavjaby.discord.object.message.embed.Embed;
import com.wavjaby.discord.object.user.User;
import com.wavjaby.discord.values.MessageFormat;
import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.wavjaby.discord.values.MessageFormat.getEmojiMention;

public class Emoji {
    private String id;
    private String name;
    private Map<String, Role> roles;
    private User user;
    private Boolean require_colons;
    private Boolean managed;
    private Boolean animated;
    private Boolean available;

    public Emoji(JsonObject emojiData) {
        id = emojiData.getString("id");
        name = emojiData.getString("name");

        if (emojiData.containsKey("roles")) {
            roles = new HashMap<>();
            for (Object i : emojiData.getArray("roles")) {
                Role role = new Role((JsonObject) i);
                roles.put(role.getID(), role);
            }
        }

        if (emojiData.containsKey("user"))
            user = new User(emojiData.getJson("user"));

        if (emojiData.containsKey("require_colons"))
            require_colons = emojiData.getBoolean("require_colons");
        if (emojiData.containsKey("managed"))
            managed = emojiData.getBoolean("managed");
        if (emojiData.containsKey("animated"))
            animated = emojiData.getBoolean("animated");
        if (emojiData.containsKey("available"))
            available = emojiData.getBoolean("available");

    }

    public Emoji(String name){
        this.name = name;
    }
    
    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (id != null)
            builder.append("id", id);
        if (name != null)
            builder.append("name", name);
        if (require_colons != null)
            builder.append("require_colons", require_colons);
        if (managed != null)
            builder.append("managed", managed);
        if (animated != null)
            builder.append("animated", animated);
        if (available != null)
            builder.append("available", available);
        return builder.toString();
    }

    //getter
    public String asMention(){
        return getEmojiMention(this);
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Role> getRoles() {
        return roles.values();
    }

    public User getUser() {
        return user;
    }

    public boolean isRequire_colons() {
        return require_colons;
    }

    public boolean isManaged() {
        return managed;
    }

    public boolean isAnimated() {
        return animated;
    }

    public boolean isAvailable() {
        return available;
    }
}
