package be.xrg.evilbotx;

import java.util.ArrayList;

import org.pircbotx.PircBotX;

public class EvilBotX {
	public static String VERSION = "EvilBotX v0.1.0";

	private ArrayList<BotPair> b = new ArrayList<BotPair>();

	public EvilBotX(ArrayList<Network> nets, XListener shared) {
		for (Network a : nets) {
			PircBotX bot = new PircBotX();
			bot.setLogin(a.getLogin());
			bot.setName(a.getNick());
			bot.setVerbose(a.useVerbose());
			bot.setAutoNickChange(a.useAutoNick());
			bot.getListenerManager().addListener(shared);
			if (a.useCustomListener()) {
				bot.getListenerManager().addListener(a.getListener());
			}
			this.b.add(new BotPair(a, bot));
		}
	}

	public void go() {
		for (BotPair c : this.b) {
			try {
				if (c.a.useSSL()) {
					c.b.connect(c.a.getAddress(), c.a.getPort(), c.a.getPass(),
							new org.pircbotx.UtilSSLSocketFactory()
									.trustAllCertificates());
				} else {
					c.b.connect(c.a.getAddress(), c.a.getPort(), c.a.getPass());
				}
				for (String a : c.a.getChannels()) {
					c.b.joinChannel(a);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("You need to create your own runner class to use EvilBotX. Please check the documentation for details.");
	}
}
