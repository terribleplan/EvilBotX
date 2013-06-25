package be.xrg.evilbotx.special;

import org.pircbotx.hooks.events.MessageEvent;

public class CountdownThread extends Thread {
	public static final boolean load = true;
	public static String[] countdown = { "5!", "4!", "3!", "2!", "1!",
			"Smoke!!!!!! 8D--~~~~~~~~~~~~" };
	@SuppressWarnings("rawtypes")
	private MessageEvent e;

	@SuppressWarnings("rawtypes")
	public CountdownThread(MessageEvent e) {
		System.out.println("Started new special.Countdown");
		this.e = e;
	}

	public void run() {
		System.out.println("Running special.Countdown");
		for (int i = 0; i < CountdownThread.countdown.length; i++) {
			e.getBot().sendMessage(e.getChannel(),
					"3,14" + CountdownThread.countdown[i]);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
