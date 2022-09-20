package com.wavjaby.discord.object.interaction;

import com.wavjaby.discord.DiscordBot;
import com.wavjaby.discord.object.slashcommand.CommandType;
import com.wavjaby.json.JsonObject;

import java.util.HashMap;

public class InteractionCommand extends InteractionObject {

    private String commandID;
    private String name;
    private CommandType callType;
    private String resolved;                                //?
    private HashMap<String, InteractionOption> options;     //?
    private String guild_id;                                //?
    private String target_id;                               //?

    public InteractionCommand(JsonObject commandData, DiscordBot bot) {
        super(commandData, bot);
        JsonObject commandOption = commandData.getJson("data");

        commandID = commandOption.getString("id");
        name = commandOption.getString("name");
        callType = CommandType.values()[commandOption.getInt("type") - 1];
        resolved = commandOption.getString("resolved");
        if (commandOption.containsKey("options")) {
            options = new HashMap<>();
            for (Object i : commandOption.getArray("options")) {
                InteractionOption option = new InteractionOption((JsonObject) i);
                options.put(option.getName(), option);
            }
        }
        guild_id = commandOption.getString("guild_id");
        target_id = commandOption.getString("target_id");
    }

    public String getCommandID() {
        return commandID;
    }

    public CommandType getCallType() {
        return callType;
    }

    public HashMap<String, InteractionOption> getOptions() {
        return options;
    }

    public String getOptionAsString(String name) {
        return (String) options.get(name).getValue();
    }

    public boolean getOptionAsBoolean(String name) {
        return (boolean) options.get(name).getValue();
    }

    public int getOptionAsInteger(String name) {
        return (int) options.get(name).getValue();
    }

    public boolean isOptionInclude(String name) {
        return options != null && options.get(name) != null;
    }

    public String getCommandName() {
        return name;
    }
}
