package it.smartcommunitylab.tataapp.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Settings {

	@Id
	private String id;

	private String agencyId;
	private String email;
	private String googleAccount;

	private String tatapointCalName;
	private String tatapointCalId;
	private boolean calendarAuthorization;
	private List<ServiceOffice> offices;

	public String getGoogleAccount() {
		return googleAccount;
	}

	public void setGoogleAccount(String googleAccount) {
		this.googleAccount = googleAccount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ServiceOffice> getOffices() {
		return offices;
	}

	public void setOffices(List<ServiceOffice> offices) {
		this.offices = offices;
	}

	public boolean isCalendarAuthorization() {
		return calendarAuthorization;
	}

	public void setCalendarAuthorization(boolean calendarAuthorization) {
		this.calendarAuthorization = calendarAuthorization;
	}

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
