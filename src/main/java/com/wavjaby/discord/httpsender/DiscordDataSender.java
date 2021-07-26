package com.wavjaby.discord.httpsender;

import com.wavjaby.discord.DiscordBot;
import com.wavjaby.discord.object.message.MessageObject;
import com.wavjaby.discord.object.slashcommand.SlashCommand;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DiscordDataSender {
    private final String TAG = "[MessageSender]";
    private final DiscordBot bot;
    private final String botToken;
    private final String httpURL;

    public DiscordDataSender(DiscordBot bot, String botToken, int apiVersion) {
        this.bot = bot;
        this.botToken = botToken;
        httpURL = "https://discordapp.com/api/v" + apiVersion;
    }

    public void addGuildSlashCommand(String guildID, SlashCommand command) {
        sendRequest("/applications/" + bot.application.getID() + "/guilds/" + guildID + "/commands",
                "POST",
                command.toString());
    }

    public void sendMessage(String channelID, MessageObject message) {
        sendRequest("/channels/" + channelID + "/messages",
                "POST",
                message.toString());
    }

//    public List<SlashCommand> getGuildSlashCommand(String id) {
//        List<SlashCommand> slashCommands = new ArrayList<>();
//
//        slashCommands.add();
//        return;
//    }

    public String getGatewayURL() {
        return sendRequest("/gateway/bot", "GET", null);
    }

    public String sendRequest(String link, String method, String payload) {
        try {
            URL url = new URL(httpURL + URLEncoder.encode(link, "UTF8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setRequestProperty("Authorization", "Bot " + botToken);
            if (method.equals("PATCH")) {
                setRequestMethod(conn, "PATCH");
            } else
                conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; Discordbot/2.0; +https://discordapp.com)");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //如果有payload
            if (payload != null && payload.length() > 0) {
                //寫出
                conn.getOutputStream().write(payload.getBytes(StandardCharsets.UTF_8));
            }

            //取得回復
            InputStream in;
            if (conn.getResponseCode() > 299) {
                if (conn.getResponseCode() > 399) {
                    System.err.println(TAG + "request failed, ResponseCode: " + conn.getResponseCode() + ", URL: " + link);
                    in = conn.getErrorStream();
                } else
                    in = conn.getInputStream();
            } else
                in = conn.getInputStream();

            byte[] buff = new byte[1024];
            StringBuilder builder = new StringBuilder();
            int length;
            //when readed it end
            while ((length = in.read(buff)) > 0) {
                builder.append(new String(buff, 0, length));
            }

            conn.disconnect();

            return builder.toString();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setRequestMethod(final HttpURLConnection c, final String value) {
        try {
            final Object target;
            if (c instanceof HttpsURLConnectionImpl) {
                final Field delegate = HttpsURLConnectionImpl.class.getDeclaredField("delegate");
                delegate.setAccessible(true);
                target = delegate.get(c);
            } else {
                target = c;
            }
            final Field f = HttpURLConnection.class.getDeclaredField("method");
            f.setAccessible(true);
            f.set(target, value);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            throw new AssertionError(ex);
        }
    }
}
