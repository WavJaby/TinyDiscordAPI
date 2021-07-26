package com.wavjaby.discord;

import com.wavjaby.discord.httpsender.DiscordDataSender;
import com.wavjaby.discord.object.HeartBeat;
import com.wavjaby.discord.object.activity.Activity;
import com.wavjaby.discord.object.guild.Guild;
import com.wavjaby.discord.object.message.Application;
import com.wavjaby.discord.object.user.User;
import com.wavjaby.discord.values.gateway.EventIntents;
import com.wavjaby.discord.values.gateway.EventType;
import com.wavjaby.discord.values.gateway.Opcode;
import com.wavjaby.discord.websocket.Client;
import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import static com.wavjaby.discord.object.user.UserStatus.ONLINE;
import static com.wavjaby.discord.values.gateway.Opcode.PresenceUpdate;

public class DiscordBot implements Client.OnMessage {
    private final int apiVersion;
    private final String discordGatewayURL;
    //function
    private final Client gateway;
    private HeartBeat clientHartBeat;
    DiscordDataSender dataSender;
    DiscordEventHandler eventHandler;

    //use for resume
    public int sequence;
    public String session_id;

    //bot
    public User self;
    public Application application;
    private final String botToken;
    public Map<String, Guild> guilds = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    //gateway setting
    private int intent = 0, largeThreshold = -1;

    public DiscordBot(String botToken, int apiVersion) {
        this.botToken = botToken;
        this.apiVersion = apiVersion;

        dataSender = new DiscordDataSender(this, botToken, apiVersion);
        eventHandler = new DiscordEventHandler(this, dataSender);
        //get gatewayInfo
        JsonObject gatewayInfo = new JsonObject(dataSender.getGatewayURL());

        //get url
        this.discordGatewayURL = gatewayInfo.getString("url");

        gateway = new Client();
        //the status of connection
        gateway.onConnectEvent(new Client.ConnectionEvent() {
            @Override
            public void openConnection() {
                System.out.println("server connect!");
            }

            @Override
            public void closeConnection() {
                System.out.println("server disconnect!");
                stopBot();
            }
        });
        //when it receive messeage
        gateway.onMessageEvent(this);
    }

    public void addIntent(EventIntents... intents) {
        for (EventIntents i : intents)
            this.intent += i.value();
    }

    public void setLargeThreshold(int values) {
        largeThreshold = values;
    }

    public void connect() {
        //connect to gateway
        gateway.openConnection(discordGatewayURL, "v=" + apiVersion + "&encoding=json", 443);
    }

    @Override
    public void dataReceive(byte[] dataByte) {
        String dataString = decompressData(dataByte);
        if (dataString == null) return;
        JsonObject jsonData = new JsonObject(dataString);
        if (jsonData.length == 0) {
            System.out.println(dataString);
            gateway.socketClose();
            return;
        }

        //event
        String event = jsonData.getString("t");
        //sequence
        if (jsonData.getObject("s") != null)
            sequence = jsonData.getInteger("s");
        //opcode
        int opCode = -1;
        if (jsonData.getObject("op") != null)
            opCode = jsonData.getInteger("op");
        //data;
        JsonObject data = jsonData.get("d");

        switch (opCode) {
            //連線成功
            case Opcode.Hello:
                //傳送bot認證資訊
                JsonBuilder identifySata = createIdentifyString();
                gateway.sendMessage(createDataFrame(Opcode.Identify, identifySata));
                //init HartBeat system
                clientHartBeat = new HeartBeat(this, data.getLong("heartbeat_interval") - 1500);
                break;
            //request heartbeat
            case Opcode.Heartbeat:
                System.out.println("Want Heartbeat");
                clientHartBeat.heartBeat();
                break;
            //return heartbeat
            case Opcode.HeartbeatACK:
                System.out.println("Discord Get Heartbeat");
                break;
            //the data of the event
            case Opcode.Dispatch: {
                EventType type = EventType.valueOf(event);
                switch (type) {
                    case READY:
                        self = new User(data.get("user"));
                        session_id = data.getString("session_id");
                        // data.getJsonArray("shard");
                        application = new Application(data.get("application"));
                        clientHartBeat.startHeartBeat();
                        break;
                    case RESUMED:
                        break;
                }
                eventHandler.onEvent(type, data);
                break;
            }
            default:
                break;
        }
    }

    public void stopBot() {
        if (gateway.isConnect())
            gateway.socketClose();
        clientHartBeat.stop();
        wait.countDown();
        System.out.println("bot stop");
    }

    public Guild getGuildByID(String guildID) {
        return guilds.get(guildID);
    }

    CountDownLatch wait = new CountDownLatch(1);

    public void waitForTermination() {
        try {
            wait.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * data send
     */
    public void updateActivity(Activity activity) {
        JsonBuilder builder = new JsonBuilder();

        builder.append("since", Instant.now().toEpochMilli());
        builder.append("activities", Collections.singleton(activity).toString(), true);
        builder.append("status", ONLINE);
        builder.append("afk", false);

        gateway.sendMessage(createDataFrame(PresenceUpdate, builder));
    }

    public void GatewayData(String jsonString) {
        gateway.sendMessage(jsonString);
    }

    /**
     * data processes
     */
    private String decompressData(byte[] dataByte) {
        //ZLIB_SUFFIX = 0x00 0x00 0xff 0xff'
        if (dataByte.length < 2 ||
                dataByte[0] != (byte) 0x78 ||
                dataByte[1] != (byte) 0x9C)
            return new String(dataByte, StandardCharsets.UTF_8);

        try {
            Inflater inflater = new Inflater(false);
            InflaterInputStream inflaterIn =
                    new InflaterInputStream(new ByteArrayInputStream(dataByte), inflater);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[32768];
            int length;
            while ((length = inflaterIn.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            String outputString = out.toString("UTF-8");
            out.close();

            return outputString;
        } catch (IOException e) {
            return null;
        }
    }

    private String createDataFrame(int opcode, JsonBuilder data) {
        JsonBuilder builder = new JsonBuilder();
        builder.append("op", opcode);
        builder.append("d", data);
        builder.append("s");
        builder.append("t");
        return builder.toString();
    }

    private JsonBuilder createIdentifyString() {
        JsonBuilder builder = new JsonBuilder();
        JsonBuilder properties = new JsonBuilder();
        builder.append("token", botToken);
        builder.append("intents", intent);
        builder.append("compress", true);//?
        if (largeThreshold > -1)
            builder.append("large_threshold", largeThreshold);//?
//        builder.append("shard", intent);//?
//        builder.append("presence", intent);//?
        properties.append("$os", "windows");
        properties.append("$browser", "tiny discord API");
        properties.append("$device", "tiny discord API");
        builder.append("properties", properties);
        return builder;
    }

    public void addEventListener(DiscordEvent eventListener) {
        this.eventHandler.addListener(eventListener);
    }
}
