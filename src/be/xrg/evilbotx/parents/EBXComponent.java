package be.xrg.evilbotx.parents;

import java.util.ArrayList;
import java.util.Arrays;

import org.pircbotx.hooks.Event;

import be.xrg.evilbotx.Storage;

@SuppressWarnings("rawtypes")
public abstract class EBXComponent {
	protected Storage s;
	private ArrayList<Class> c;

	private EBXComponent(Storage s) {
		this.c = new ArrayList<Class>();
		this.s = s;
	}

	public EBXComponent(Storage s, Class a) {
		this(s);
		this.c.add(a);
	}

	public EBXComponent(Storage s, Class[] a) {
		this(s);
		this.c.addAll(Arrays.asList(a));
	}

	public boolean wantEvent(Event e) {
		for (Class a : this.c) {
			if (a.isInstance(e)) {
				return (true && this.wantEventM(e));
			}
		}
		return false;
	}
	
	protected boolean wantEventM(Event e) {
		return false;
	}

	public abstract void handleEvent(Event e);

	public abstract String getComponentName();

	public abstract int getComponentID();
}
