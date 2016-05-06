package it.smartcommunitylab.tataapp.model;

public class Settings {
	private String agencyId;
	private String email;
	private String tatapointCalName;
	private String tatapointCalId;

	public String getTatapointCalName() {
		return tatapointCalName;
	}

	public void setTatapointCalName(String tatapointCalName) {
		this.tatapointCalName = tatapointCalName;
	}

	public String getTatapointCalId() {
		return tatapointCalId;
	}

	public void setTatapointCalId(String tatapointCalId) {
		this.tatapointCalId = tatapointCalId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
