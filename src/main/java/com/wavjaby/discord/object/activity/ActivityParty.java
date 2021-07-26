package com.wavjaby.discord.object.activity;

public class ActivityParty {
    private String id;
    private Integer currentSize;
    private Integer maxSize;

    public ActivityParty(String id, int currentSize, int maxSize) {
        this.id = id;
        this.currentSize = currentSize;
        this.maxSize = maxSize;
    }

    @Override
    public String toString() {
        String arr = null;
        if (currentSize != null) {
            arr = "[" + currentSize + "," + maxSize + "]";
        }

        if (id != null && arr != null) {
            return "{\"id\":\"" + id + "\",\"size\":" + arr + '}';
        }
        if (arr != null) {
            return "{\"size\":" + arr + '}';
        }
        if (id != null) {
            return "{\"id\":\"" + id + "\"}";
        }
        return null;
    }
}
