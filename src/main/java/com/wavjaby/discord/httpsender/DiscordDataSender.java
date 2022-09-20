package com.wavjaby.discord.httpsender;

import com.wavjaby.discord.MultiPartData;
import com.wavjaby.discord.DiscordBot;
import com.wavjaby.discord.object.message.MessageObject;
import com.wavjaby.discord.object.slashcommand.SlashCommand;
import com.wavjaby.json.JsonArray;
import com.wavjaby.json.JsonObject;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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

    public SlashCommand addGuildSlashCommand(String guildID, SlashCommand command) {
        HttpURLConnection connection = sendRequestNoResponse("/applications/" + bot.application.getID() + "/guilds/" + guildID + "/commands",
                "POST",
                command.toString());
        try {
            String responseText = readResponse(connection.getInputStream());
            if (responseText != null)
                return new SlashCommand(new JsonObject(responseText));
            else
                throw new IOException("Failed to read response");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String channelID, MessageObject message) {
        sendRequestNoResponse("/channels/" + channelID + "/messages",
                "POST",
                message.toString());
    }

    public void editMessage(MessageObject message) {
        sendRequestNoResponse("/channels/" + message.getChannelID() + "/messages/" + message.getID(), "PATCH", message.toString());
    }

    public String getMessage(String channelID, String messageID) {
        return sendRequest("/channels/" + channelID + "/messages/" + messageID, "GET");
    }

    public Map<String, SlashCommand> getGuildSlashCommand(String guildID) {
        Map<String, SlashCommand> slashCommands = new HashMap<>();
        for (Object i : new JsonArray(
                sendRequest("/applications/" + bot.application.getID() + "/guilds/" + guildID + "/commands", "GET")
        )) {
            SlashCommand command = new SlashCommand((JsonObject) i);
            slashCommands.put(command.getName(), command);
        }

        return slashCommands;
    }

    public void removeGuildSlashCommand(String guildID, String commandID) {
        sendRequest(
                "/applications/" + bot.application.getID() +
                        "/guilds/" + guildID +
                        "/commands/" + commandID,
                "DELETE");
    }

    public String getGatewayURL() {
        return sendRequest("/gateway/bot", "GET");
    }

    public String sendRequest(String endPoint, String method) {
        try {
            HttpURLConnection connection = sendRequestNoResponse(endPoint, method, null, "application/json");
            if (connection == null) return null;
            return readResponse(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String sendRequest(String endPoint, String method, String payload) {
        try {
            HttpURLConnection connection = sendRequestNoResponse(
                    endPoint,
                    method,
                    payload == null ? null : payload.getBytes(StandardCharsets.UTF_8),
                    "application/json"
            );
            if (connection == null) return null;
            return readResponse(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendMultiPart(String endPoint, String method, MultiPartData multiPart) {
        sendRequestNoResponse(endPoint, method,
                multiPart.getPayload(),
                "multipart/form-data; boundary=" + multiPart.getBoundary());
    }

    public HttpURLConnection sendRequestNoResponse(String endPoint, String method, String payload) {
        return sendRequestNoResponse(endPoint, method, payload.getBytes(StandardCharsets.UTF_8), "application/json");
    }

    public HttpURLConnection sendRequestNoResponse(String endPoint, String method, byte[] payload, String contentType) {
        try {
            URL url = new URL(httpURL + endPoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (method.equals("PATCH")) {
                setRequestMethod(conn, method);
            } else
                conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization", "Bot " + botToken);
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("User-Agent", "DiscordBot (v1.0; https://github.com/WavJaby)");
            conn.setUseCaches(false);
            conn.setDoInput(true);

            //have payload
            if (payload != null && payload.length > 0) {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Length", String.valueOf(payload.length));
                conn.getOutputStream().write(payload);
            }
            //get response code
            if (conn.getResponseCode() > 299) {
                if (conn.getResponseCode() > 399) {
                    System.err.println(TAG + "request failed, ResponseCode: " + conn.getResponseCode() + ", URL: " + endPoint);
                    String errorString = readResponse(conn.getErrorStream());
                    System.out.println(errorString);
                    conn.disconnect();

                    return null;
                }
                System.out.println(TAG + "warn! ResponseCode: " + conn.getResponseCode() + ", URL: " + endPoint);
                String warnString = readResponse(conn.getInputStream());
                System.out.println(warnString);
                conn.disconnect();
                return null;
            }
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
            bot.stopBot();
            return null;
        }
    }

    private String readResponse(InputStream in) {
        try {
            StringBuilder builder = new StringBuilder();
            byte[] buff = new byte[1024];
            int length;
            //when readed it end
            while ((length = in.read(buff)) > 0) {
                builder.append(new String(buff, 0, length));
            }
            in.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
