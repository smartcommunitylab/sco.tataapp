package it.smartcommunitylab.tataapp.model;

import org.springframework.data.annotation.Id;

public class TataPoint {

	@Id
	private String id;
	private String name;
	private String address;
	private String city;
	private long startEvent;
	private long endEvent;
	private TataPointContact contact;

	private String agencyId;

	public TataPointContact getContact() {
		return contact;
	}

	public void setContact(TataPointContact contact) {
		this.contact = contact;
	}

	public long getStartEvent() {
		return startEvent;
	}

	public void setStartEvent(long startEvent) {
		this.startEvent = startEvent;
	}

	public long getEndEvent() {
		return endEvent;
	}

	public void setEndEvent(long endEvent) {
		this.endEvent = endEvent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

}
