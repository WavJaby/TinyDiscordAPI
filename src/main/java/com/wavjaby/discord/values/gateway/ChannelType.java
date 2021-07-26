package com.wavjaby.discord.values.gateway;

public class ChannelType {
    public static final int GUILD_TEXT = 0;     //a text channel within a server
    public static final int DM = 1;             //a direct message between users
    public static final int GUILD_VOICE = 2;    //a voice channel within a server
    public static final int GROUP_DM = 3;       //a direct message between multiple users
    public static final int GUILD_CATEGORY = 4; //an organizational category that contains up to 50 channels
    public static final int GUILD_NEWS = 5;     //a channel that users can follow and crosspost into their own server
    public static final int GUILD_STORE = 6;    //a channel in which game developers can sell their game on Discord
}
