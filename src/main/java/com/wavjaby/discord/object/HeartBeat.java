package com.wavjaby.discord.object;

import com.wavjaby.discord.DiscordBot;
import com.wavjaby.discord.values.gateway.Opcode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartBeat {
    private final ScheduledExecutorService heartBeatThread = Executors.newSingleThreadScheduledExecutor();
    private final String heartBeatFrame, heartBeatFrameEnd;
    private final long interval;
    private final DiscordBot gateway;

    public HeartBeat(DiscordBot gateway, long interval) {
        heartBeatFrame = "{\"s\":null,\"t\":null,\"op\":" + Opcode.Heartbeat + ",\"d\":";
        heartBeatFrameEnd = "}";

        this.gateway = gateway;
        this.interval = interval;
    }

    public void startHeartBeat() {
        heartBeatThread.scheduleWithFixedDelay(
                this::heartBeat,
                0, interval, TimeUnit.MILLISECONDS);
    }

    public void heartBeat() {
        gateway.GatewayData(heartBeatFrame + gateway.sequence + heartBeatFrameEnd);
    }

    public void stop() {
        heartBeatThread.shutdown();
        try {
            heartBeatThread.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
