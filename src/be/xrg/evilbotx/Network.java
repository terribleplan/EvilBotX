package be.xrg.evilbotx;

import java.util.ArrayList;

public class Network {
	private XListener privateListener;

	private String name;
	private String nick;
	private String address;
	private String pass;
	
	private ArrayList<String> channels;

	private int port;

	private boolean autoNickChange = true;
	private boolean customListener = false;
	private boolean verbose = false;
	private boolean ssl = true;

	public Network(String login, String nick, String address, String pass, int port) {
		this.address = address;
		this.channels = new ArrayList<String>();
		this.name = login;
		this.nick = nick;
		this.pass = pass;
		this.port = port;
	}
	
	public void addChannel(String channel) {
		this.channels.add(channel);
	}

	public void setSSL(boolean ssl) {
		this.ssl = ssl;
	}

	public void setCustomListener(XListener listen) {
		this.privateListener = listen;
		this.customListener = true;
	}

	public String getAddress() {
		return this.address;
	}
	
	public XListener getListener() {
		return this.privateListener;
	}

	public String getLogin() {
		return this.name;
	}

	public String getNick() {
		return this.nick;
	}
	public String getPass() {
		return this.pass;
	}
	public int getPort() {
		return this.port;
	}

	public boolean useAutoNick() {
		return this.autoNickChange;
	}

	public boolean useCustomListener() {
		return this.customListener;
	}

	public boolean useSSL() {
		return this.ssl;
	}

	public boolean useVerbose() {
		return this.verbose;
	}

	public ArrayList<String> getChannels() {
		return this.channels;
	}

}
