package it.smartcommunitylab.tataapp.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Meeting {

	@Id
	private String id;

	private Representive familyRepresentive;

	private List<Child> children;

	private String babysitterId;

	private long creationTs;

	private boolean vouchers;

	public boolean isVouchers() {
		return vouchers;
	}

	public void setVouchers(boolean vouchers) {
		this.vouchers = vouchers;
	}

	private String agencyId;

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(List<Child> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Representive getFamilyRepresentive() {
		return familyRepresentive;
	}

	public void setFamilyRepresentive(Representive familyRepresentive) {
		this.familyRepresentive = familyRepresentive;
	}

	public String getBabysitterId() {
		return babysitterId;
	}

	public void setBabysitterId(String babysitterId) {
		this.babysitterId = babysitterId;
	}

	public long getCreationTs() {
		return creationTs;
	}

	public void setCreationTs(long creationTs) {
		this.creationTs = creationTs;
	}

}
