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

@Entity
public class Message implements Serializable {

	public static final long RESOURCE_ID_TRAIN = 0;
	public static final long RESOURCE_ID_GARE = 1;
	private static final long serialVersionUID = 1L;

	protected Message() {
	}

	public Message(Person person, String content, String resourceId,
			Long resourceType) {
		super();
		this.person = person;
		this.content = content;
		this.date = new Date();
		this.resourceId = resourceId;
		this.resourceType = resourceType;
	}

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue
	private Long id;

	@NotNull
	@ManyToOne
	private Person person;

	@NotNull
	@Column(nullable = false)
	private String content;

	@NotNull
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@NotNull
	@Column(nullable = false)
	private String resourceId;

	@NotNull
	@Column(nullable = false)
	private Long resourceType;

	public Long getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Long getResourceType() {
		return resourceType;
	}

	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

}
