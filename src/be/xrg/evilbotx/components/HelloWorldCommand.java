package be.xrg.evilbotx.components;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;

@SuppressWarnings("rawtypes")
public class HelloWorldCommand extends be.xrg.evilbotx.parents.EBXComponent {

	public HelloWorldCommand(be.xrg.evilbotx.Storage s) {
		super(s, MessageEvent.class);
	}

	public void handleEvent(Event e) {
		MessageEvent t = (MessageEvent) e;
		if (t.getMessage().startsWith("!hello")) {
			t.getBot().sendMessage(t.getChannel(), "Hello world");
		}
	}

	protected boolean wantEventM(Event e) {
		if (e instanceof MessageEvent) {
			return ((MessageEvent) e).getMessage().equals("!hello");
		}
		return false;
	}

	public String getComponentName() {
		return "HelloWorldCommand";
	}

	public int getComponentID() {
		return 68752;
	}

}
