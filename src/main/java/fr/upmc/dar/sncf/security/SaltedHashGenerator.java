package fr.upmc.dar.sncf.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import fr.upmc.dar.sncf.domain.HashedPasswordTuple;

public class SaltedHashGenerator {

	public static void main(String[] args) {
		// some test
		HashedPasswordTuple tuple = hashPasswordUser("password");
		System.out.println(tuple.getHashedPassword());
		System.out.println(getSaltedHash("password", tuple.getSalt()));
	}

	private static SecureRandom srand = new SecureRandom();

	private static int generateNewSalt() {
		return srand.nextInt((int) Math.pow(2, 32));
	}

	public static HashedPasswordTuple hashPasswordUser(String password) {
		// Generate new 32 bits salt
		int salt = generateNewSalt();
		// Compute the salted hash
		String hashedPassword = getSaltedHash(password, salt);
		return new HashedPasswordTuple(hashedPassword, salt);
	}

	public static String getSaltedHash(String password, int salt) {
		return computeSHA256(password + "|" + salt);
	}

	private static String computeSHA256(String password) {
		MessageDigest digest;
		StringBuilder sb = new StringBuilder();
		try {
			digest = MessageDigest.getInstance("SHA-256");
			// Get the hash's bytes
			byte[] hash = digest.digest(password.getBytes());
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			for (int i = 0; i < hash.length; i++) {
				sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
