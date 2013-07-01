package be.xrg.evilbotx.components;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;

import be.xrg.evilbotx.Storage;
import be.xrg.evilbotx.Utilities;

@SuppressWarnings("rawtypes")
public class UrbanCommand extends be.xrg.evilbotx.parents.EBXComponent {

	public UrbanCommand(Storage s) {
		super(s, MessageEvent.class);
	}

	public void handleEvent(Event e) {
		MessageEvent t = (MessageEvent) e;
		String b = t.getMessage();
		boolean response = false;
		if (b.startsWith("!urban")) {
			if (b.contains(" ")) {
				String[] c = b.split(" ", 2);
				JSONObject d = new JSONObject(
						Utilities
								.getHTMLPage("http://api.urbandictionary.com/v0/define?term="
										+ Utilities.urlEncode(c[1]))[1]);
				if (d.has("result_type")) {
					Object g = d.get("result_type");
					if (g instanceof String) {
						if (((String) g).equals("exact")) {
							if (d.has("list")) {
								JSONArray h = d.getJSONArray("list");
								d = h.getJSONObject(0);
								if (d.has("word") && d.has("definition")) {
									Object i = d.get("word"), j = d
											.get("definition");
									if ((i instanceof String)
											&& (j instanceof String)) {
										t.getBot().sendMessage(
												t.getChannel(),
												((String) i) + ": "
														+ ((String) j));
										response = true;
									}
								}
							}
						} else {
							t.respond("Unable to find exact match.");
							response = true;
						}
					}
				}
			}
		}
		if (!response) {
			t.respond("Unable to perform lookup.");
		}
	}

	protected boolean wantEventM(Event e) {
		if (e instanceof MessageEvent) {
			return ((MessageEvent) e).getMessage().startsWith("!urban");
		}
		return false;
	}

	public String getComponentName() {
		return "UrbanCommand";
	}

	public int getComponentID() {
		return 4823303;
	}

}
