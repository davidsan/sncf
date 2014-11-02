package fr.upmc.dar.sncf.domain;

public class HashedPasswordTuple {

	private String hashedPassword;
	private Integer salt;

	public HashedPasswordTuple(String hashedPassword, Integer salt) {
		super();
		this.hashedPassword = hashedPassword;
		this.salt = salt;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public Integer getSalt() {
		return salt;
	}

}
