package be.xrg.evilbotx.components;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;

import be.xrg.evilbotx.Utilities;

@SuppressWarnings("rawtypes")
public class YoutubeParser extends be.xrg.evilbotx.parents.EBXComponent {

	public static String yt = "1,16[You4,16Tube1,16] ";

	public YoutubeParser(be.xrg.evilbotx.Storage s) {
		super(s, MessageEvent.class);
	}

	@Override
	public void handleEvent(Event e) {
		MessageEvent a = (MessageEvent) e;
		String[] b = a.getMessage().split(" ");
		for (String c : b) {
			if (this.isYTLink(c)) {
				String[] d = Utilities.getHTMLPage(c);
				if (d[0].equals("200")) {
					String f = Utilities.decodeHTMLEntities(Utilities
							.getPageTitle(d[1]));
					f = f.substring(0, f.length() - 10);
					a.getBot()
							.sendMessage(a.getChannel(), YoutubeParser.yt + f);
				}
			}
		}
	}

	@Override
	public String getComponentName() {
		// TODO Auto-generated method stub
		return "YoutubeParser";
	}

	@Override
	public int getComponentID() {
		return 438329;
	}

	protected boolean wantEventM(Event e) {
		if (e instanceof MessageEvent) {
			MessageEvent a = (MessageEvent) e;
			return a.getMessage().contains("youtu");
		}
		return false;
	}

	private boolean isYTLink(String p) {
		return ((p.startsWith("http://") || p.startsWith("https://")) && (p
				.contains("://youtu.be/") || ((p
				.contains("://youtube.com/watch?") || p
				.contains("://www.youtube.com/watch?")) && p.contains("v="))));
	}
}
