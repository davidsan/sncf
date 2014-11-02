package fr.upmc.dar.sncf.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Favorite", uniqueConstraints = { @UniqueConstraint(name = "unique_favorite", columnNames = {
		"person", "resourceId", "resourceType" }) })
public class Favorite implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Favorite() {
	}

	public Favorite(Person person, String resourceId, Long resourceType,
			String resourceName) {
		super();
		this.person = person;
		this.resourceId = resourceId;
		this.resourceType = resourceType;
		this.resourceName = resourceName;
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
	private String resourceId;

	@NotNull
	@Column(nullable = false)
	private Long resourceType;

	@NotNull
	@Column(nullable = false)
	private String resourceName;

	public Long getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

}
