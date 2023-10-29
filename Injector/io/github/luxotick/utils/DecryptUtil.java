package io.github.luxotick.utils;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class DecryptUtil {
    public static String decrypt(byte[] encryptedData, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = Arrays.copyOfRange(encryptedData, 3, 15);
            byte[] payload = Arrays.copyOfRange(encryptedData, 15, encryptedData.length);
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            cipher.init(2, keySpec, spec);
            return new String(cipher.doFinal(payload));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
