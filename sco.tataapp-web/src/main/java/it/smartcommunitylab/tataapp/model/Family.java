package it.smartcommunitylab.tataapp.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

public class Family {

	@Id
	private String id;

	private Representive representive;
	private Set<Child> children = new HashSet<>();
	private String agencyId;

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public Representive getRepresentive() {
		return representive;
	}

	public void setRepresentive(Representive representive) {
		this.representive = representive;
	}

	public Set<Child> getChildren() {
		return children;
	}

	public void setChildren(Set<Child> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
