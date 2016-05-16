package it.smartcommunitylab.tataapp.model;

import java.util.List;

public class Settings {
	private String agencyId;
	private String email;
	private String tatapointCalName;
	private String tatapointCalId;
	private boolean calendarAuthorization;
	private List<ServiceOffice> offices;

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
