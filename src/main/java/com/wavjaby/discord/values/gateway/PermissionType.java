package com.wavjaby.discord.values.gateway;

public enum PermissionType {
    //PERMISSION            VALUE               DESCRIPTION                                                         CHANNEL TYPE
    CREATE_INSTANT_INVITE(0x00000001),  //Allows creation of instant invites	                                T, V
    KICK_MEMBERS(0x00000002),           //Allows kicking members
    BAN_MEMBERS(0x00000004),            //Allows banning members
    ADMINISTRATOR(0x00000008),          //Allows all permissions and bypasses channel permission overwrites
    MANAGE_CHANNELS(0x00000010),        //Allows management and editing of channels                          T, V
    MANAGE_GUILD(0x00000020),           //Allows management and editing of the guild
    ADD_REACTIONS(0x00000040),          //Allows for the addition of reactions to messages		            T
    VIEW_AUDIT_LOG(0x00000080),         //Allows for viewing of audit logs
    PRIORITY_SPEAKER(0x00000100),       //Allows for using priority speaker in a voice channel		        V
    STREAM(0x00000200),                 //Allows the user to go live		                                    V
    VIEW_CHANNEL(0x00000400),           //Allows guild members to view a channel,
    //                                          which includes reading messages in text channels                    T, V
    SEND_MESSAGES(0x00000800),          //Allows for sending messages in a channel		                    T
    SEND_TTS_MESSAGES(0x00001000),      //Allows for sending of /tts messages		                            T
    MANAGE_MESSAGES(0x00002000),        //Allows for deletion of other users messages		                    T
    EMBED_LINKS(0x00004000),            //Links sent by users with this permission will be auto-embedded		T
    ATTACH_FILES(0x00008000),           //Allows for uploading images and files		                        T
    READ_MESSAGE_HISTORY(0x00010000),   //Allows for reading of message history		                        T
    MENTION_EVERYONE(0x00020000),       //Allows for using the @everyone tag to notify all users in a channel,
    //                                          and the @here tag to notify all online users in a channel	        T
    USE_EXTERNAL_EMOJIS(0x00040000),    //Allows the usage of custom emojis from other servers		        T
    VIEW_GUILD_INSIGHTS(0x00080000),    //Allows for viewing guild insights
    CONNECT(0x00100000),                //Allows for joining of a voice channel		                        V
    SPEAK(0x00200000),                  //Allows for speaking in a voice channel		                        V
    MUTE_MEMBERS(0x00400000),           //Allows for muting members in a voice channel		                V
    DEAFEN_MEMBERS(0x00800000),         //Allows for deafening of members in a voice channel		            V
    MOVE_MEMBERS(0x01000000),           //Allows for moving of members between voice channels		            V
    USE_VAD(0x02000000),                //Allows for using voice-activity-detection in a voice channel		V
    CHANGE_NICKNAME(0x04000000),        //Allows for modification of own nickname
    MANAGE_NICKNAMES(0x08000000),       //Allows for modification of other users nicknames
    MANAGE_ROLES(0x10000000),           //Allows management and editing of roles		                        T, V
    MANAGE_WEBHOOKS(0x20000000),        //Allows management and editing of webhooks		                    T, V
    MANAGE_EMOJIS(0x40000000),          //Allows management and editing of emojis
    ;

    public final int value;

    PermissionType(int value) {
        this.value = value;
    }

    public static PermissionType valueOfLabel(int label) {
        for (PermissionType e : values()) {
            if (e.value == label) {
                return e;
            }
        }
        return null;
    }
}
