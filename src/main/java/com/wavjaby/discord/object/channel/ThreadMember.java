package com.wavjaby.discord.object.channel;

import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;

public class ThreadMember {
    private String id;
    private String user_id;
    private OffsetDateTime join_timestamp;
    private int flags;

    public ThreadMember(JsonObject memberData) {
        id = memberData.getString("id");
        user_id = memberData.getString("user_id");
        join_timestamp = OffsetDateTime.parse(memberData.getString("join_timestamp"));
        flags = memberData.getInteger("flags");
    }
}
