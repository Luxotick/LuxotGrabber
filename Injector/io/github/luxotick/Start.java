package io.github.luxotick;

import io.github.luxotick.impl.DiscordInjector;
import io.github.luxotick.impl.web;

public class Start {

	public static void main(String[] argument) throws Exception {
		if (DiscordInjector.instance == null){
			DiscordInjector.instance = new DiscordInjector();
		}
             // DiscordInjector.instance.initialize();
		web.main(argument);
	}
	
}
