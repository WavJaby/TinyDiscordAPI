package com.wavjaby.discord.object.interaction;

public class InteractionResponseType {
    public final static int PONG = 1;                                       // ACK a Ping
    public final static int CHANNEL_MESSAGE_WITH_SOURCE = 4;                // respond to an interaction with a message
    public final static int DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE = 5;       // ACK an interaction and edit a response later, the user sees a loading state
    public final static int DEFERRED_UPDATE_MESSAGE = 6;                    // for components, ACK an interaction and edit the original message later; the user does not see a loading state
    public final static int UPDATE_MESSAGE = 7;                             // for components, edit the message the component was attached to
}
