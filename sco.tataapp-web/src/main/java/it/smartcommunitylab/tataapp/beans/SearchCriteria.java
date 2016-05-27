package it.smartcommunitylab.tataapp.beans;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {

	private List<String> langs = new ArrayList<>();
	private String rangeAge;
	private boolean carOwner;

	private long fromDate;
	private long toDate;
	private List<String> timeSlots = new ArrayList<>();
	private String[] days = new String[0];
	private String agencyId;

	public String getAgencyId() {
		return agencyId;
	}

	public List<String> getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(List<String> timeSlots) {
		this.timeSlots = timeSlots;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public List<String> getLangs() {
		return langs;
	}

	public void setLangs(List<String> langs) {
		this.langs = langs;
	}

	public String getRangeAge() {
		return rangeAge;
	}

	public void setRangeAge(String rangeAge) {
		this.rangeAge = rangeAge;
	}

	public boolean isCarOwner() {
		return carOwner;
	}

	public void setCarOwner(boolean carOwner) {
		this.carOwner = carOwner;
	}

	public long getFromDate() {
		return fromDate;
	}

	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}

	public long getToDate() {
		return toDate;
	}

	public void setToDate(long toDate) {
		this.toDate = toDate;
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String[] days) {
		this.days = days;
	}

}
