package be.xrg.evilbotx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.zip.CRC32;

import org.apache.commons.lang3.StringEscapeUtils;

public class Utilities {
	public static final String strEnc = "UTF-8";
	public static boolean compat = false;

	public static String[] getHTMLPage(String url) {
		URL loc;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String[] ret = { "", "" };
		try {
			loc = new URL(url);
			conn = (HttpURLConnection) loc.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			ret[0] += conn.getResponseCode();
			while ((line = rd.readLine()) != null) {
				ret[1] += line;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
			ret[0] += "-1";
		}
		return ret;
	}
	public static String decodeHTMLEntities(String encoded) {
		return StringEscapeUtils.unescapeHtml4(encoded);
	}
	public static String getPageTitle(String html) {
		int[] f = new int[2];
		f[0] = html.indexOf("<title>") + 7;
		f[1] = html.indexOf("</title>");
		return "" + html.substring(f[0], f[1]);
	}

	public static String intToTimeString(int seconds) {
		boolean started = false;
		String ret = "";
		int amount = 86400;
		if (seconds > amount) {
			ret += (seconds / amount) + " day";
			if (seconds/amount > 1) {
				ret += "s";
			}
			ret += ", ";
			seconds -= (seconds / amount) * amount;
			started = true;
		}
		amount = 3600;
		if (seconds > amount || started) {
			ret += (seconds / amount) + " hour";
			if (seconds/amount > 1) {
				ret += "s";
			}
			ret += ", ";
			seconds -= (seconds / amount) * amount;
			started = true;
		}
		amount = 60;
		if (seconds > amount || started) {
			ret += (seconds / amount) + " minute";
			if (seconds/amount > 1) {
				ret += "s";
			}
			ret += ", ";
			seconds -= (seconds / amount) * amount;
			started = true;
		}
		ret += seconds + " second";
		if (seconds > 1) {
			ret += "s";
		}
		return ret;
	}

	public static void saveData(byte[] data, String modName, int modID) {

	}

	public static boolean hasData(String modName, int modID) {
		return false;
	}

	public static byte[] loadData(String modName, int modID) {
		return null;
	}

	public static String[] formatString(String[] a, int lineLength) {
		String nextln = null;
		boolean done = false;
		ArrayList<String> r = new ArrayList<String>();
		nextln = "╔";
		for (int i = 0; i < lineLength; i++) {
			nextln += "═";
		}
		nextln += "╗";
		r.add(nextln);
		while (!done) {
			for (String s : a) {
				nextln = "║";
				int curl = 0;
				String[] words = s.split(" ");
				for (String w : words) {
					if (curl + w.length() > lineLength) {
						nextln += w;
					} else {
						nextln = Utilities.padString(nextln, lineLength + 1);
						nextln += "║";
						r.add(nextln);
						nextln = "║";
					}
				}
				nextln = Utilities.padString(nextln, lineLength + 1);
				nextln += "║";
				r.add(nextln);
			}
		}
		nextln = "╚";
		for (int i = 0; i < lineLength; i++) {
			nextln += "═";
		}
		nextln += "╝";
		r.add(nextln);
		return (String[]) r.toArray();
	}

	public static String md5(String input) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		if (input != null)
			input = bToStr(md5.digest(input.getBytes(Utilities.strEnc)));
		return input;
	}

	public static String crc32(String input) {
		CRC32 crc32 = new CRC32();
		if (input != null) {
			crc32.update(input.getBytes());
			input = Utilities.padOut(Long.toHexString(crc32.getValue()), 8);
		}
		return input;
	}

	public static String bToStr(byte[] in) {
		char[] hexArray = "0123456789abcdef".toCharArray();
		char[] hexChars = new char[in.length * 2];
		int v;
		for (int j = 0; j < in.length; j++) {
			v = in[j] & 0xFF;
			hexChars[j * 2] = hexArray[v / 16];
			hexChars[j * 2 + 1] = hexArray[v % 16];
		}
		return new String(hexChars);
	}

	private static String padString(String ln, int lineLength) {
		for (int i = ln.length(); i < lineLength; i++) {
			ln += " ";
		}
		return ln;
	}

	private static String padOut(String input, int nChar) {
		return String.format("%" + nChar + "s", input).replace(' ', '0');
	}
}
