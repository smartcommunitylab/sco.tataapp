package it.smartcommunitylab.tataapp.model;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public enum TimeSlot {
	MORNING(6, 12), AFTERNOON(12, 19), EVENING(19, 22), NIGHT(22, 6);

	private final long fromHour;
	private final long toHour;

	TimeSlot(int fromHour, int toHour) {
		LocalDate epoch = new LocalDate(1970, 1, 1);
		LocalTime fromTime = new LocalTime(fromHour, 0);
		LocalTime toTime = new LocalTime(toHour, 0);
		this.fromHour = epoch.toDateTime(fromTime).getMillis();
		this.toHour = epoch.toDateTime(toTime).getMillis();
	}

	public long getFromHour() {
		return fromHour;
	}

	public long getToHour() {
		return toHour;
	}
}
