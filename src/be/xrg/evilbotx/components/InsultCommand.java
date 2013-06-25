package be.xrg.evilbotx.components;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;

@SuppressWarnings("rawtypes")
public class InsultCommand extends be.xrg.evilbotx.parents.EBXComponent {
	public static String[] i = { "Nobody likes you.", "Poq Gai",
			"I do not like your look, I promise thee", "Die in a fire",
			"I desire that we be better strangers",
			"If ignorance is bliss, you must be overjoyed", "GTFO",
			"Besame el culo", "La reputisima madre que te remil pario",
			"Jy pis my af", "Lay da yuen fay gay mm sai sou",
			"Degenerate and base art thou", "Blöde fotze",
			"You will live a long pathetic life", "Kusu o taberu na",
			"You will get hit by a car later on",
			"You are not worth another word, else I'd call you knave",
			"Your face is as a book, where men may read strange matters",
			"Everything you believe in is an illusion" };

	public InsultCommand(be.xrg.evilbotx.Storage s) {
		super(s, MessageEvent.class);
	}

	@Override
	public void handleEvent(Event e) {
		MessageEvent t = (MessageEvent) e;
		String b = t.getMessage();
		if (b.startsWith("!insult")) {
			String a = "";
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
			t.getBot().sendMessage(t.getChannel(),
					a + ": " + InsultCommand.randInsult());
		}
	}

	protected boolean wantEventM(Event e) {
		if (e instanceof MessageEvent) {
			return ((MessageEvent) e).getMessage().startsWith("!insult");
		}
		return false;
	}

	@Override
	public String getComponentName() {
		// TODO Auto-generated method stub
		return "InsultCommand";
	}

	@Override
	public int getComponentID() {
		// TODO Auto-generated method stub
		return 0;
	}

	static String randInsult() {
		return InsultCommand.i[(int) (Math.random() * InsultCommand.i.length)];
	}
}
