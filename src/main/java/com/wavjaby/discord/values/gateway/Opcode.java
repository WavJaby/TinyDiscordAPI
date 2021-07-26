package com.wavjaby.discord.values.gateway;

public class Opcode {
    public static final int Dispatch = 0;
    public static final int Heartbeat = 1;
    public static final int Identify = 2;
    public static final int PresenceUpdate = 3;
    public static final int VoiceStateUpdate = 4;
    public static final int VoiceServerPing = 5;
    public static final int Resume = 6;
    public static final int Reconnect = 7;
    public static final int RequestGuildMembers = 8;
    public static final int InvalidSession = 9;
    public static final int Hello = 10;
    public static final int HeartbeatACK = 11;
}

//0	    Dispatch	                Receive	        An event was dispatched.
//1 	Heartbeat	                Send/Receive	Fired periodically by the client to keep the connection alive.
//2	    Identify	                Send	        Starts a new session during the initial handshake.
//3	    Presence Update	            Send	        Update the client's presence.
//4	    Voice State Update	        Send	        Used to join/leave or move between voice channels.
//5	    Voice Server Ping	        Send            Used for voice ping checking
//6	    Resume	                    Send	        Resume a previous session that was disconnected.
//7	    Reconnect	                Receive	        You should attempt to reconnect and resume immediately.
//8	    Request Guild Members	    Send	        Request information about offline guild members in a large guild.
//9	    Invalid Session	            Receive	        The session has been invalidated. You should reconnect and identify/resume accordingly.
//10	Hello	                    Receive	        Sent immediately after connecting, contains the heartbeat_interval to use.
//11	Heartbeat ACK	            Receive	        Sent in response to receiving a heartbeat to acknowledge that it has been received.


//import java.util.Arrays;
//import java.util.Optional;

//public enum dataTransform.Opcode{
//    Dispatch(0),
//    dataTransform.Heartbeat(1),
//    Identify(2),
//    PresenceUpdate(3),
//    VoiceStateUpdate(4),
//    Resume(6),
//    Reconnect(7),
//    RequestGuildMembers(8),
//    InvalidSession(9),
//    Hello(10),
//    HeartbeatACK(11);
//
//    private final int value;
//    dataTransform.Opcode(int value) {
//        this.value = value;
//    }
//
//    public static String valueOf(int value) {
//        return Arrays.stream(values())
//                .filter(legNo -> legNo.value == value)
//                .findFirst().toString();
//    }
//}

