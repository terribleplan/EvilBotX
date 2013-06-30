package be.xrg.evilbotx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Storage {
	static String dfName = "dsssseRs";
	private Map<String, byte[]> data;
	private boolean loaded = false;
	private final String location;
	private final String fileName;

	public Storage(String filename) {
		this(filename, System.getProperty("user.dir") + "/");
	}

	public Storage(String filename, String location) {
		this.location = location;
		this.fileName = filename;
		this.read();
		if (!this.loaded) {
			this.init();
		}
	}

	@SuppressWarnings("unchecked")
	private void read() {
		File f = new File(this.location);
		File ff = new File(f, this.fileName);
		if (ff.exists()) {
			try {
				FileInputStream fi = new FileInputStream(ff);
				byte[] b = new byte[(int) f.length()];
				fi.read(b);
				fi.close();
				ObjectInputStream is = new ObjectInputStream(
						new ByteArrayInputStream(b));
				this.data = (HashMap<String, byte[]>) is.readObject();
				this.loaded = true;
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void write() {
		try {
			FileOutputStream f = new FileOutputStream(this.location
					+ this.fileName);
			f.write(Storage.toByteArr((Serializable) this.data));
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		boolean fail = false;
		File f = new File(this.location);
		File ff = new File(f, this.fileName);
		if (!f.exists()) {
			if (!f.mkdirs()) {
				fail = true;
			}
		}
		if (ff.exists()) {
			try {
				if (!(ff.delete() && ff.createNewFile())) {
					fail = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
				fail = true;
			}
		} else {
			try {
				if (!ff.createNewFile()) {
					fail = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
				fail = true;
			}
		}
		this.data = new HashMap<String, byte[]>();
		if (!fail) {
			loaded = true;
		} else {
			System.exit(-2);
		}
		this.putData(385639, "FakeDataToPopulate", Storage.toByteArr("lolol"));
	}

	public boolean hasData(int ID, String appName) {
		return this.data.containsKey(this.getUID(ID, appName));
	}

	public void putData(int ID, String appName, byte[] data) {
		String uid = this.getUID(ID, appName);
		this.data.put(uid, data);
		this.write();
	}

	public byte[] getData(int ID, String appName) {
		return this.data.get(this.getUID(ID, appName));
	}

	private String getUID(int ID, String appName) {
		return Utilities.crc32(appName + ID);
	}

	public static byte[] toByteArr(Serializable o) {
		byte[] b = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(o);
			b = out.toByteArray();
			objOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}
}
