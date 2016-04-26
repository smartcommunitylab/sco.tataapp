package it.smartcommunitylab.tataapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class TataPoint {

	@Id
	private String id;
	private String name;
	private String address;
	private String city;
	private long startDate;
	private long endDate;
	private long startTime;
	private long endTime;
	private Recurrence recurrence;

	public Recurrence getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String frequency, String[] days) {
		this.recurrence = new Recurrence(frequency, days);
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long beginPeriod) {
		this.startDate = beginPeriod;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endPeriod) {
		this.endDate = endPeriod;
	}

	public String getContactDescription() {
		return contactDescription;
	}

	public void setContactDescription(String contactDescription) {
		this.contactDescription = contactDescription;
	}

	@Field("description")
	private String contactDescription;

	private String agencyId;

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startEvent) {
		this.startTime = startEvent;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endEvent) {
		this.endTime = endEvent;
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

class Recurrence {
	private String frequency;
	private String[] days;

	public Recurrence(String frequency, String[] days) {
		this.frequency = frequency;
		this.days = days;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String[] days) {
		this.days = days;
	}

}