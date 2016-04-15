package it.smartcommunitylab.tataapp.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

public class Babysitter {

	@Id
	private String id;

	private String name;
	private String surname;

	private Date birthdate;
	private String email;
	private String address;
	private String city;
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

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
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
