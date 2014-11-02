package fr.upmc.dar.sncf.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.upmc.dar.sncf.security.SessionIdentifierGenerator;

@Entity
public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue
	private Long id;

	@NotNull
	@ManyToOne
	private Person person;

	@NotNull
	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	@NotNull
	@JsonIgnore
	private String ip;

	protected Session() {

	}

	public Session(Person person, String ip) {
		super();
		this.person = person;
		this.token = SessionIdentifierGenerator.nextSessionId();
		this.date = new Date();
		this.ip = ip;
	}
	
	@NotNull
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public Long getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}	

}
