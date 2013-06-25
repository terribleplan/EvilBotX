package be.xrg.evilbotx.components;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;

@SuppressWarnings("rawtypes")
public class SlapCommand extends be.xrg.evilbotx.parents.EBXComponent {

	public SlapCommand(be.xrg.evilbotx.Storage s) {
		super(s, MessageEvent.class);
	}

	public void handleEvent(Event e) {
		if (e instanceof MessageEvent) {
			MessageEvent t = (MessageEvent) e;
			String b = t.getMessage();
			if (b.startsWith("!slap")) {
				String a = "slaps ";
				if (b.contains(" ")) {
					String[] c = b.split(" ");
					if (c.length >= 1) {
						a += c[1];
					} else {
						a += t.getUser().getNick();
					}
				} else {
					a += t.getUser().getNick();
				}
				t.getBot().sendAction(t.getChannel(), a);
			}
		}
	}

	protected boolean wantEventM(Event e) {
		if (e instanceof MessageEvent) {
			return ((MessageEvent) e).getMessage().startsWith("!slap");
		}
		return false;
	}

	@Override
	public String getComponentName() {
		return "SlapCommand";
	}

	@Override
	public int getComponentID() {
		return 462963;
	}
}
