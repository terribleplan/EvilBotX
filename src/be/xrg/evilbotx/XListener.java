package be.xrg.evilbotx;

import java.util.Iterator;
import java.util.Map.Entry;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;

import be.xrg.evilbotx.parents.EBXComponent;

//This is meant to deal with raw types
@SuppressWarnings({ "unchecked", "rawtypes" })
public class XListener extends ListenerAdapter implements Listener {
	private java.util.Map<String, EBXComponent> com = new java.util.HashMap<String, EBXComponent>();

	public void onEvent(Event e) throws Exception {
		super.onEvent(e);
		Iterator<Entry<String, EBXComponent>> i = this.com.entrySet()
				.iterator();
		while (i.hasNext()) {
			Entry<String, EBXComponent> z = i.next();
			EBXComponent t = z.getValue();
			String k = z.getKey();
			if (t.wantEvent(e)) {
				System.out.println(k);
				t.handleEvent(e);
			}
		}
		
	}

	public void addComponent(EBXComponent c) {
		String u = this.makeUniqueName(c);
		System.out.println("AddComponent-" + u + " " + c.getComponentName());
		if (!this.com.containsKey(u)) {
			this.com.put(u, c);
		}
	}

	public void removeComponent(EBXComponent c) {
		String u = this.makeUniqueName(c);
		if (!this.com.containsKey(u)) {
			this.com.remove(u);
		}
	}

	private String makeUniqueName(EBXComponent c) {
		return Utilities.crc32(c.getClass().getName() + c.getComponentName()
				+ c.getComponentID() + "some needless salt");
	}
}
