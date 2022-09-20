package com.wavjaby.discord;

import com.wavjaby.discord.DiscordBot;
import com.wavjaby.discord.values.gateway.Opcode;
import com.wavjaby.websocket.client.SocketClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HeartBeat {
    private final ScheduledExecutorService heartBeatThread = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> future;
    private final String heartBeatFrame;
    private final SocketClient gateway;
    private final DiscordBot bot;

    public HeartBeat(SocketClient gateway, DiscordBot bot, long interval) {
        heartBeatFrame = "{\"s\":null,\"t\":null,\"op\":" + Opcode.Heartbeat + ",\"d\":";

        this.gateway = gateway;
        this.bot = bot;

        future = heartBeatThread.scheduleWithFixedDelay(
                this::heartBeat,
                interval / 10, interval, TimeUnit.MILLISECONDS);
    }

    public void heartBeat() {
        gateway.sendMessage(heartBeatFrame + bot.sequence + '}');
    }

    public void stop() {
        future.cancel(true);
        heartBeatThread.shutdown();
        try {
            heartBeatThread.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
