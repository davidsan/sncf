package fr.upmc.dar.sncf.external.api;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Gravatar {

	public static final String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

	public static String getUrl(String mail) {
		byte[] buf = mail.trim().toLowerCase().getBytes();
		String hash;
		try {
			hash = Gravatar.toHexString(MessageDigest.getInstance("MD5")
					.digest(buf));
			hash = hash.toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		StringBuilder str = new StringBuilder();
		str.append(GRAVATAR_URL);
		str.append(hash);
		return str.toString();
	}

	public static String getUrl(String mail, int size) {
		StringBuilder str = new StringBuilder();
		str.append(getUrl(mail));
		str.append("?s=");
		str.append(size);
		return str.toString();
	}

	private static String toHexString(byte[] buf) {
		BigInteger bi = new BigInteger(1, buf);
		return String.format("%0" + (buf.length << 1) + "X", bi);
	}

}
