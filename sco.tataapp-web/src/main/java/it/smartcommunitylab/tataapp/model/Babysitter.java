package it.smartcommunitylab.tataapp.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Babysitter {

	@Id
	private String id;

	private String name;
	private String surname;

	private String qualification;

	private long birthdate;
	private String email;
	private String address;
	private String city;

	private String description;
	private String updates;

	private List<String> languages;
	private boolean carOwner;

	private String calendarURL;

	/*
	 * actually unused
	 */
	private boolean available;

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@JsonIgnore
	private List<Availability> timeAvailability;

	public String getCalendarURL() {
		return calendarURL;
	}

	public void setCalendarURL(String calendarURL) {
		this.calendarURL = calendarURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUpdates() {
		return updates;
	}

	public void setUpdates(String updates) {
		this.updates = updates;
	}

	public List<Availability> getTimeAvailability() {
		return timeAvailability;
	}

	public void setTimeAvailability(List<Availability> timeAvailability) {
		this.timeAvailability = timeAvailability;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public boolean isCarOwner() {
		return carOwner;
	}

	public void setCarOwner(boolean carOwner) {
		this.carOwner = carOwner;
	}

	private String agencyId;

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public long getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(long birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Transient
	public void verify() {
		if (StringUtils.isBlank(email)) {
			throw new IllegalArgumentException("email cannot be empty");
		}

		if (StringUtils.isBlank(agencyId)) {
			throw new IllegalArgumentException("agency cannot be empty");
		}
	}

}
