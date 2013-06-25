package be.xrg.evilbotx.components;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.KickEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NickChangeEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.QuitEvent;

import be.xrg.evilbotx.Storage;
import be.xrg.evilbotx.Utilities;

/*
 * 0 = first seen
 * 1 = recently seen
 */
@SuppressWarnings("rawtypes")
public class SeenCommand extends be.xrg.evilbotx.parents.EBXComponent {
	static Class[] q = { JoinEvent.class, KickEvent.class, MessageEvent.class,
			NickChangeEvent.class, PartEvent.class, QuitEvent.class };
	private Map<String, int[]> d;
	private boolean l = false;

	public SeenCommand(Storage s) {
		super(s, SeenCommand.q);
	}

	public String getComponentName() {
		return "SeenCommand";
	}

	public int getComponentID() {
		return 83463;
	}

	public void handleEvent(Event e) {
		String j = Utilities.crc32(e.getBot().getLogin());
		if (e instanceof MessageEvent) {
			MessageEvent t = (MessageEvent) e;
			this.seen(j, t.getUser().getNick(), true);
			String b = t.getMessage();
			if (b.startsWith("!seen")) {
				if (b.equalsIgnoreCase("!seenstats")) {
					t.respond(this.seenStats());
				} else if (b.length() > 6) {
					String[] c = b.split(" ");
					if (c.length >= 2) {
						t.respond(this.checkSeen(j, c[1]));
					}
				}
			}
		} else if (e instanceof JoinEvent) {
			this.seen(j, ((JoinEvent) e).getUser().getNick(), true);
		} else if (e instanceof PartEvent) {
			this.seenOff(j, ((PartEvent) e).getUser().getNick());
		} else if (e instanceof QuitEvent) {
			this.seenOff(j, ((QuitEvent) e).getUser().getNick());
		} else if (e instanceof KickEvent) {
			this.seenOff(j, ((KickEvent) e).getRecipient().getNick());
		} else if (e instanceof NickChangeEvent) {
			this.seenOff(j, ((NickChangeEvent) e).getOldNick());
			this.seen(j, ((NickChangeEvent) e).getNewNick(), true);
		}
	}

	protected boolean wantEventM(Event e) {
		if (e instanceof MessageEvent || e instanceof JoinEvent
				|| e instanceof PartEvent || e instanceof QuitEvent
				|| e instanceof KickEvent || e instanceof NickChangeEvent) {
			return true;
		}
		return false;
	}

	private void seen(String prefix, String user, boolean s) {
		user = prefix + user.toLowerCase();
		int a = (int) (System.currentTimeMillis() / 1000);
		this.ensureLoad();
		int[] b;
		if (this.d.containsKey(user)) {
			b = this.d.get(user);
		} else {
			b = new int[3];
			b[0] = a;
		}
		b[1] = a;
		b[2] = 1;
		this.d.put(user, b);
		if (s) {
			this.save();
		}
	}

	private void seenOff(String prefix, String user) {
		user = user.toLowerCase();
		this.seen(prefix, user, false);
		int[] b = this.d.get(user);
		b[2] = 0;
		this.d.put(prefix + user.toLowerCase(), b);
		this.save();
	}

	private String checkSeen(String p, String w) {
		String a = p+ w.toLowerCase();
		String seen = "";
		int h = (int) (System.currentTimeMillis() / 1000);
		if (this.d.containsKey(a)) {
			int[] b = this.d.get(a);
			if (b[2] == 0) {
				seen += "I last saw " + w + " "
						+ Utilities.intToTimeString(h - b[1]) + " ago.";
			} else {
				seen += w + " is currently online.";
			}
			seen += " I first saw " + w + " "
					+ Utilities.intToTimeString(h - b[0]) + " ago.";
		} else {
			seen += "I have not seen " + w + ".";
		}
		return seen;
	}

	private String seenStats() {
		int users = 0, online = 0, time = (int) (System.currentTimeMillis() / 1000);
		for (Entry<String, int[]> entry : d.entrySet()) {
			users++;
			int[] v = entry.getValue();
			if (v[2] != 0) {
				online++;
			}
		}
		return "I am currently tracking " + users + " nicks, " + online
				+ " of whom are currently online. (AwesomeBot "
				+ be.xrg.evilbotx.EvilBotX.VERSION + ", " + time + ")";
		// return null;
	}

	private void ensureLoad() {
		if (!l) {
			this.load();
		}
	}

	@SuppressWarnings("unchecked")
	private void load() {
		byte[] a = null;
		try {
			if (this.s.hasData(this.getComponentID(), this.getComponentName())) {
				a = this.s.getData(this.getComponentID(),
						this.getComponentName());
				if (a != null) {
					ObjectInputStream is = new ObjectInputStream(
							new ByteArrayInputStream(a));
					this.d = (HashMap<String, int[]>) is.readObject();
					this.l = true;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (!this.l) {
			this.init();
		}
	}

	private void init() {
		this.d = new HashMap<String, int[]>();
		this.l = true;
	}

	private void save() {
		byte[] a = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(this.d);
			a = out.toByteArray();
			objOut.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.s.putData(this.getComponentID(), this.getComponentName(), a);
	}
}
