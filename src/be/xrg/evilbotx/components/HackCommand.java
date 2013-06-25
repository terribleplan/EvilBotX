package be.xrg.evilbotx.components;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;

import be.xrg.evilbotx.EvilBotX;
import be.xrg.evilbotx.Utilities;

@SuppressWarnings("rawtypes")
public class HackCommand extends be.xrg.evilbotx.parents.EBXComponent {
	private Map<String, int[]> d;
	private boolean l = false;

	private String q;
	private boolean r = false;

	public HackCommand(be.xrg.evilbotx.Storage s) {
		super(s, MessageEvent.class);
	}

	@Override
	public void handleEvent(Event e) {
		MessageEvent a = (MessageEvent) e;
		String b = a.getMessage();
		if (b.equals("!hack")) {
			hack(a);
		} else if (b.equals("!hackstats")) {
			hackStats(a);
		} else if (b.equals("!hackcount")) {
			hackCount(a);
		}
	}

	protected boolean wantEventM(Event e) {
		if (e instanceof MessageEvent) {
			return ((MessageEvent) e).getMessage().startsWith("!hack");
		}
		return false;
	}

	private void hack(MessageEvent e) {
		this.ensureLoad();
		int INTERVAL = 3600;
		int ct = (int) (System.currentTimeMillis() / 1000);
		String who = e.getUser().getNick().toLowerCase();
		int i = (int) (Math.random() * 50 - 10);
		if (!this.d.containsKey(who)) {
			int[] t = new int[2];
			t[0] = 0;
			t[1] = ct - INTERVAL;
			this.d.put(who, t);
		}
		int[] ud = this.d.get(who);
		int tt = ud[0] + i;
		String ms;
		if (ud[1] + INTERVAL <= ct) {
			if (tt < 0) {
				tt = 0;
			}
			if (i <= 0 && ud[0] == 0) {
				ms = "You were unable to hack anything. You still have 0 computers in your botnet.";
				return;
			} else if (i < -50) {
				ms = "Looks like the IT department woke up. " + (i * -1)
						+ " hosts lost. You now have " + tt
						+ " computers in your botnet.";
			} else if (i < -8) {
				ms = "" + (-1 * i)
						+ " of your compromised hosts were lost. You now have "
						+ tt + " computers in your botnet.";
			} else if (i < 0) {
				ms = "" + (-1 * i)
						+ " of your bots were cleaned. You now have " + tt
						+ " computers in your botnet.";
			} else if (i == 0) {
				ms = who
						+ ": You weren't able to hack any machines. You still have "
						+ tt + " computers in your botnet.";
			} else if (i > 30) {
				ms = "You are a hacking god and owned " + i
						+ " boxes. You now have " + tt
						+ " computers in your botnet.";
			} else if (i > 1) {
				ms = "You hacked " + i + " d00dz. You now have " + tt
						+ " computers in your botnet.";
			} else {
				ms = "You hacked " + i + " box. MUST HACK MORE.";
			}
			ud[0] = tt;
			ud[1] = ct;
			this.d.put(who, ud);
			e.respond(ms);
			this.r = false;
			this.save();
		} else {
			int t = (ud[1] + INTERVAL) - ct;
			e.respond("You're too tired to hack anything. Chill for "
					+ Utilities.intToTimeString(t) + ".");
		}
	}

	private void hackCount(MessageEvent e) {
		this.ensureLoad();
		String who = e.getUser().getNick();
		String sta = "You have ";
		if (this.d.containsKey(who)) {
			int[] ud = this.d.get(who);
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			sta += ud[0]
					+ " computers in  your botnet, and your last hack occured at "
					+ df.format(ud[1] * (long) 1000) + ".";
		} else {
			sta += "not done any hacking.";
		}
		e.respond(sta);
	}

	private void hackStats(MessageEvent e) {
		this.ensureLoad();
		if (!this.r) {
			int users = 0, boxes = 0, latest = 0, time = (int) (System
					.currentTimeMillis() / 1000), leader = 0;
			String recentName = "", leaderName = "";
			Date dtime = new Date(System.currentTimeMillis());
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			for (Entry<String, int[]> entry : d.entrySet()) {
				int[] ud = entry.getValue();
				String key = entry.getKey();
				users++;
				boxes += ud[0];
				if (leader < ud[0]) {
					leader = ud[0];
					leaderName = key;
				}
				if (latest < ud[1]) {
					latest = ud[1];
					recentName = key;
				}
			}
			dtime = new Date(latest * (long) 1000);
			this.q = "Currently tracking " + users + " users with " + boxes
					+ " zombie PCs. Current leader is " + leaderName + " with "
					+ leader + " machines. The most recent hack happened on "
					+ df.format(dtime) + " and was done by " + recentName
					+ ". (" + EvilBotX.VERSION + ", " + time + ")";
			this.r = true;
		}
		e.respond(this.q);
	}

	private void ensureLoad() {
		if (!this.l) {
			this.load();
		}
	}

	@Override
	public String getComponentName() {
		return "HackCommand";
	}

	@Override
	public int getComponentID() {
		return 2400062;
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
