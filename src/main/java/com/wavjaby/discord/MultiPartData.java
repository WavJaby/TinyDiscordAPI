package com.wavjaby.discord;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MultiPartData {
    private final String newL = "\r\n";
    private final byte[] newLByte = newL.getBytes();
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final String boundary;

    public MultiPartData(String boundary) {
        this.boundary = boundary;
    }

    public MultiPartData appendPayloadJson(String data) {
        writeData("name=\"payload_json\"", "application/json", data.getBytes(StandardCharsets.UTF_8));
        return this;
    }

    public MultiPartData appendPngFile(String imageName, byte[] data) {
        appendFile(imageName, "image/png", data);
        return this;
    }

    public MultiPartData appendFile(String fileName, String contentType, byte[] data) {
        writeData("name=\"file\"; filename=\"" + fileName + "\"",
                contentType, data);
        return this;
    }

    private void writeData(String fileHeader, String contentType, byte[] data) {
        try {
            out.write(("--" + boundary + newL +
                    "Content-Disposition: form-data; " + fileHeader + newL).getBytes(StandardCharsets.UTF_8));
            if (contentType != null)
                out.write(("Content-Type: " + contentType + newL).getBytes());
            out.write(newLByte);
            out.write(data);
            out.write(newLByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getPayload() {
        byte[] data = out.toByteArray();
        byte[] dataClose = ("--" + boundary + "--").getBytes();
        byte[] outByte = new byte[data.length + dataClose.length];
        System.arraycopy(data, 0, outByte, 0, data.length);
        System.arraycopy(dataClose, 0, outByte, data.length, dataClose.length);
        return outByte;
    }

    public String getBoundary() {
        return boundary;
    }
}
