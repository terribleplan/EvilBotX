package be.xrg.evilbotx.components;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.HalfOpEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.KickEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.OpEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.RemoveChannelBanEvent;
import org.pircbotx.hooks.events.SetChannelBanEvent;
import org.pircbotx.hooks.events.SuperOpEvent;
import org.pircbotx.hooks.events.TopicEvent;
import org.pircbotx.hooks.events.VoiceEvent;

import be.xrg.evilbotx.Storage;
import be.xrg.evilbotx.Utilities;
import be.xrg.evilbotx.parents.EBXComponent;

@SuppressWarnings("rawtypes")
public class ChannelStatsWatcher extends EBXComponent {
	static Class[] q = { ActionEvent.class, HalfOpEvent.class, JoinEvent.class,
			KickEvent.class, MessageEvent.class, OpEvent.class,
			PartEvent.class, RemoveChannelBanEvent.class,
			SetChannelBanEvent.class, SuperOpEvent.class, TopicEvent.class,
			VoiceEvent.class };

	public ChannelStatsWatcher(Storage s) {
		super(s, q);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleEvent(Event e) {
		String j = Utilities.crc32(e.getBot().getLogin());
		if (e instanceof MessageEvent) {
			MessageEvent t = (MessageEvent) e;
			this.inc(j, 0);
			String b = t.getMessage();
			if (b.equals("!stats")) {

			}
		} else if (e instanceof ActionEvent) {
			// ActionEvent
		} else if (e instanceof HalfOpEvent) {
			// HalfOpEvent
		} else if (e instanceof JoinEvent) {
			// JoinEvent
		} else if (e instanceof KickEvent) {
			// KickEvent
		} else if (e instanceof OpEvent) {
			// OpEvent
		} else if (e instanceof PartEvent) {
			// PartEvent
		} else if (e instanceof RemoveChannelBanEvent) {
			// RemoveChannelBanEvent
		} else if (e instanceof SetChannelBanEvent) {
			// SetChannelBanEvent
		} else if (e instanceof SuperOpEvent) {
			// SuperOpEvent
		} else if (e instanceof TopicEvent) {
			// TopicEvent
		} else if (e instanceof VoiceEvent) {
			// VoiceEvent
		}
	}

	private void inc(String p, int place) {

	}

	@Override
	public String getComponentName() {
		return "ChannelStats";
	}

	@Override
	public int getComponentID() {
		return 9001938;
	}

}
