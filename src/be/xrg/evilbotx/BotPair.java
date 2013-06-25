package be.xrg.evilbotx;

import org.pircbotx.PircBotX;

public class BotPair {
	public Network a;
	public PircBotX b;
	
	public BotPair(Network a, PircBotX b) {
		this.a = a;
		this.b = b;
	}
}
