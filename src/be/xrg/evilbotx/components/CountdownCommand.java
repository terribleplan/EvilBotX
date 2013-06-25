package be.xrg.evilbotx.components;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;

@SuppressWarnings("rawtypes")
public class CountdownCommand extends be.xrg.evilbotx.parents.EBXComponent {

	public CountdownCommand(be.xrg.evilbotx.Storage s) {
		super(s, MessageEvent.class);
	}

	public void handleEvent(Event e) {
		new be.xrg.evilbotx.special.CountdownThread((MessageEvent) e).run();
	}

	protected boolean wantEventM(Event e) {
		if (e instanceof MessageEvent) {
			MessageEvent a = (MessageEvent) e;
			return a.getMessage().equals("!countdown");
		}
		return false;
	}

	public String getComponentName() {
		return "CountdownCommand";
	}

	public int getComponentID() {
		return 98763235;
	}
}
