package com.wavjaby.discord.websocket;

import sun.misc.BASE64Encoder;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Client extends Thread {
    private InputStream in;
    private OutputStream out;
    private SSLSocket socket;
    private boolean connect;

    //event
    private ConnectionEvent connectionEvent;
    private OnMessage onMessage;

    public void openConnection(String url, String query, int port) {
        try {
            int hostStart = url.indexOf("://") + 3;
            String host = url.substring(hostStart);

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = (SSLSocket) factory.createSocket(host, port);
            socket.setSoTimeout(Integer.MAX_VALUE);

            in = socket.getInputStream();
            out = socket.getOutputStream();

            boolean handshakeSuccess = doHandshake(host, query, port);
            if (!handshakeSuccess) {
                socketClose();
                return;
            }

            if (connectionEvent != null)
                connectionEvent.openConnection();
            connect = true;

            new Thread(this).start();
        } catch (Exception e) {
            socketClose();
        }
    }

    @Override
    public void run() {
        while (connect) {
            try {
                byte[] data = readData();
                if (onMessage != null)
                    onMessage.dataReceive(data);
            } catch (Exception e) {
                socketClose();
                return;
            }
        }
    }

    public void socketClose() {
        if (!connect)
            return;
        try {
            connect = false;
            in.close();
            out.close();
            socket.close();
            connectionEvent.closeConnection();
        } catch (IOException e) {
            return;
        }
    }

    private byte[] readData() throws IOException {
        //read header
        byte[] headerData = new byte[2];
        in.read(headerData);

        int opcode = ((char) headerData[0]) & 0x0f;

        boolean fin = (headerData[0] >> 7) != 0;
        //遮罩
        boolean mask = (headerData[1] >> 7) != 0;
        //資料大小
        int packetLength = headerData[1] & 0x7f;
        if (packetLength > 125) {
            packetLength = (int) readExtLength(packetLength);
        }

        //mask
        byte[] payload = new byte[packetLength];
        if (mask) {
            byte[] maskByte = new byte[4];
            //read mask byte
            in.read(maskByte);
            //read payload data
            in.read(payload);

            //unmasking
            for (int i = 0; i < packetLength; i++) {
                payload[i] = (byte) ((int) payload[i] ^ (int) maskByte[i % 4]);
            }
        } else {
            //read payload data
            in.read(payload);
        }

        //如果連接關閉
        if (opcode == Opcode.connectionClose) {
            int closeCode = ((payload[0] & 0xFF) << 8) + (payload[1] & 0xFF);
            payload = ("closeCode: " + closeCode).getBytes();
            socketClose();
        }
        return payload;

    }

    private long readExtLength(int length) throws IOException {
        if (length == 126) {
            byte[] extByte = new byte[2];
            in.read(extByte);
            return ((extByte[0] & 0xFF) << 8)
                    + (extByte[1] & 0xFF);
        }
        if (length == 127) {
            byte[] extByte = new byte[8];
            in.read(extByte);
            return ((long) (extByte[0]) << 56)
                    + (((long) extByte[1] & 0xFF) << 48)
                    + ((long) (extByte[2] & 0xFF) << 40)
                    + ((long) (extByte[3] & 0xFF) << 32)
                    + ((long) (extByte[4] & 0xFF) << 24)
                    + ((extByte[5] & 0xFF) << 16)
                    + ((extByte[6] & 0xFF) << 8)
                    + (extByte[7] & 0xFF);
        }
        return 0;
    }

    public void sendMessage(String message) {
        if (!socket.isClosed())
            sendData(message.getBytes(StandardCharsets.UTF_8));
    }

    private void sendData(byte[] payload) {
        int fin = 1, opcode = Opcode.textFrame, mask = 1;
        int dataLength = payload.length;
        int payloadLength;
        byte[] extendedLength = null;

        if (dataLength < 126) {
            payloadLength = dataLength;
        } else if (dataLength < 65535 + 1) {
            payloadLength = 126;
            extendedLength = create2ExtByte(dataLength);
        } else {
            payloadLength = 127;
            extendedLength = create8ExtByte(dataLength);
        }

        //開頭資料
        byte[] frameHead = new byte[2];
        frameHead[0] = (byte) ((fin << 7) + opcode);
        frameHead[1] = (byte) ((mask << 7) + payloadLength);

        //mask data
        byte[] maskByte = new byte[4];
        int maskInt = new Random(System.currentTimeMillis()).nextInt();
        maskByte[0] = (byte) (maskInt >> 24);
        maskByte[1] = (byte) (maskInt >> 16);
        maskByte[2] = (byte) (maskInt >> 8);
        maskByte[3] = (byte) maskInt;
        //masking
        for (int i = 0; i < dataLength; i++) {
            payload[i] = (byte) ((int) payload[i] ^ (int) maskByte[i % 4]);
        }

        //send
        try {
            out.write(frameHead);
            if (extendedLength != null) {
                out.write(extendedLength);
            }
            out.write(maskByte);
            out.write(payload);
            out.flush();

        } catch (IOException e) {
            socketClose();
        }
    }

    private static byte[] create2ExtByte(long number) {
        byte[] array = new byte[2];
        array[0] = (byte) (number >> 8);
        array[1] = (byte) number;
        return array;
    }

    private static byte[] create8ExtByte(long number) {
        byte[] array = new byte[8];
        array[0] = (byte) (number >> 56);
        array[1] = (byte) (number >> 48);
        array[2] = (byte) (number >> 40);
        array[3] = (byte) (number >> 32);
        array[4] = (byte) (number >> 24);
        array[5] = (byte) (number >> 16);
        array[6] = (byte) (number >> 8);
        array[7] = (byte) number;
        return array;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3 - 1];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            if (j < bytes.length - 1)
                hexChars[j * 3 + 2] = ',';
        }
        return new String(hexChars);
    }

    /**
     * do handshake
     */
    private final String LINE_END = "\r\n";

    private boolean doHandshake(String host, String query, int port) {
        //do handshake
        System.out.println("start handshake");
        try {
            String base64Key = randomBase64();
            String message = "GET /?" + query + " HTTP/1.1" + LINE_END +
                    "Connection: Upgrade" + LINE_END +
                    "Host: " + host + ":" + port + LINE_END +
                    "Sec-WebSocket-Key: " + base64Key + LINE_END +
                    "Sec-WebSocket-Version: 13" + LINE_END +
                    "Upgrade: websocket" + LINE_END +
                    LINE_END;
            out.write(message.getBytes());

            //read handshake response
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String buff;
            boolean handShakeDone = false;
            boolean keyCorrect = false;
            while ((buff = reader.readLine()) != null) {
                if (buff.startsWith("HTTP/1.1 101"))
                    handShakeDone = true;
                String[] keyAccept;
                if ((keyAccept = buff.split(": "))[0].equalsIgnoreCase("Sec-WebSocket-Accept")) {
                    String encodeKey = new BASE64Encoder()
                            .encodeBuffer(encryptSHA1(base64Key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11"));
                    keyCorrect = encodeKey.startsWith(keyAccept[1]);
                }
                if (buff.length() == 0)
                    break;
                result.append(buff).append(LINE_END);
            }
            //http code success and key correct
            if (handShakeDone && keyCorrect) {
                System.out.println("handShake successes");
                return true;
            } else {
                System.out.println("handShake failed");
                if (handShakeDone)
                    System.out.println("server response key incorrect");

                System.out.println(result.toString());
                return false;
            }
        } catch (IOException e) {
            System.out.println("handshake send failed");
            return false;
        }
    }

    private String randomBase64() {
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] randomByte = new byte[13];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < randomByte.length; i++) {
            randomByte[i] = (byte) random.nextInt(255);
        }
        return encoder.encode(randomByte);
    }

    private static byte[] encryptSHA1(String inputString) {
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(inputString.getBytes(StandardCharsets.UTF_8));
            return crypt.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * event listener
     */
    public void onConnectEvent(ConnectionEvent connectionEvent) {
        this.connectionEvent = connectionEvent;
    }

    public void onMessageEvent(OnMessage onMessage) {
        this.onMessage = onMessage;
    }

    public boolean isConnect() {
        return connect;
    }

    public interface ConnectionEvent {
        void openConnection();
        void closeConnection();
    }

    public interface OnMessage {
        void dataReceive(byte[] bytes) throws Exception;
    }
}
