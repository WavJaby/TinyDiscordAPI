package com.wavjaby.discord.object.slashcommand;

import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SlashCommandStruct {
    // https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-structure
    protected String id;
    protected CommandType type;                         //?
    protected String application_id;
    protected String guild_id;                          //?
    protected String name;
    protected String name_localizations;                //?
    protected String description;
    protected String description_localizations;         //?
    protected List<CommandOption> options;              //?
    protected String default_member_permissions;        //?
    protected Boolean dm_permission;                    //?
    protected String version;


    protected SlashCommandStruct() {
    }

    public SlashCommandStruct(JsonObject commandData) {
        id = commandData.getString("id");
        if (commandData.containsKey("type"))
            type = CommandType.values()[commandData.getInt("type") - 1];
        else
            type = CommandType.CHAT_INPUT;
        application_id = commandData.getString("application_id");
        guild_id = commandData.getString("guild_id");
        name = unicodeDecode(commandData.getString("name"));
        description = unicodeDecode(commandData.getString("description"));
        if (commandData.containsKey("options")) {
            options = new ArrayList<>();
            for (Object i : commandData.getArray("options"))
                options.add(new CommandOption((JsonObject) i));
        }
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (name != null)
            builder.append("name", name);
        if (guild_id != null)
            builder.append("guild_id", guild_id);
        if (description != null)
            builder.append("description", description);
        if (options != null)
            builder.append("options", options.toString(), true);
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof SlashCommandStruct))
            return false;
        SlashCommandStruct target = (SlashCommandStruct) obj;
        return name.equals(target.name) &&
                description.equals(target.description) &&
                Objects.equals(options, target.options)
                ;
    }

    public static String unicodeDecode(String input) {
        StringBuilder processed = new StringBuilder();

        boolean unicode = false;
        int offset = 0, position;
        while ((position = input.indexOf("\\u", offset)) != -1) {
            unicode = true;
            if (position != 0)
                processed.append(input, offset, position);
            String token = input.substring(position + 2, position + 6);
            processed.append((char) Integer.parseInt(token, 16));
            offset = position + 6;
        }
        if (!unicode) return input;
        processed.append(input, offset, input.length());

        return processed.toString();
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }
}
