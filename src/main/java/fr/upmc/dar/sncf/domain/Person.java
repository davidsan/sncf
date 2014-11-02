package fr.upmc.dar.sncf.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.upmc.dar.sncf.external.api.Gravatar;

@Entity
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Person() {
	}

	public Person(String username, String password, String mail) {
		super();
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.gravatar = Gravatar.getUrl(mail);
	}

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	@NotNull
	@Size(max = 128)
	private String username;

	@Column(nullable = false)
	@NotNull
	@Size(max = 128)
	@JsonIgnore
	private String password;

	@Column(nullable = false)
	@NotNull
	@JsonIgnore
	private int salt;

	@Column(nullable = false)
	@NotNull
	@Size(max = 128)
	@JsonIgnore
	private String mail;

	@Column(nullable = false)
	@NotNull
	@Size(max = 128)
	private String gravatar;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSalt() {
		return salt;
	}

	public void setSalt(int salt) {
		this.salt = salt;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
		this.gravatar = Gravatar.getUrl(mail);
	}

	public String getGravatar() {
		return gravatar;
	}
}
