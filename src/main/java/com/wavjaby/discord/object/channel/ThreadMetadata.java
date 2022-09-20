package com.wavjaby.discord.object.channel;

import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;

public class ThreadMetadata {
    private boolean archived;
    private int auto_archive_duration;
    private OffsetDateTime archive_timestamp;
    private boolean locked;

    ThreadMetadata(JsonObject metadataData){
        archived = metadataData.getBoolean("archived");
        auto_archive_duration = metadataData.getInt("auto_archive_duration");
        archive_timestamp = OffsetDateTime.parse(metadataData.getString("archive_timestamp"));
        locked = metadataData.getBoolean("locked");
    }
}
