package it.smartcommunitylab.tataapp.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import com.google.api.services.calendar.model.Event;

public class TataPoint {

	@Transient
	private static final Logger logger = LoggerFactory.getLogger(TataPoint.class);

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

	@Field("description")
	private String contactDescription;

	private String agencyId;

	public TataPoint() {

	}

	public TataPoint(String agencyId, Event event) {
		if (event != null) {
			address = event.getLocation();
			// FIXME hard to extract the city from address

			name = event.getSummary();
			id = event.getId();

			if (event.getStart().getDateTime() != null) {
				startDate = event.getStart().getDateTime().getValue();
				startTime = event.getStart().getDateTime().getValue();
			} else {
				startDate = event.getStart().getDate().getValue();
				startTime = event.getStart().getDate().getValue();
			}

			if (event.getEnd().getDateTime() != null) {
				endDate = event.getEnd().getDateTime().getValue();
				endTime = event.getEnd().getDateTime().getValue();
			} else {
				endDate = event.getEnd().getDate().getValue();
				endTime = event.getEnd().getDate().getValue();
			}
			recurrence = extractRecurrence(event.getRecurrence());
			contactDescription = event.getDescription();
		}
		this.agencyId = agencyId;
	}

	/**
	 * Create a Recurrence object analyzing iCal spec. Actually supported RRULE
	 * property, FREQ and BYDAY fields
	 * 
	 * @param recurrence
	 * @return
	 */
	private Recurrence extractRecurrence(List<String> recurrence) {
		Recurrence r = new Recurrence();
		if (recurrence != null) {
			for (String recDef : recurrence) {
				if (recDef.startsWith("RRULE:")) {
					recDef = recDef.substring("RRULE:".length());
					String[] parts = recDef.split(";");
					for (String part : parts) {
						if (part.startsWith("FREQ=")) {
							r.setFrequency(part.substring("FREQ=".length()));
						} else if (part.startsWith("BYDAY=")) {
							r.setDays(part.substring("BYDAY=".length()).split(","));
						}
					}
				}
			}
		}
		return r;
	}

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

	public Recurrence() {
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