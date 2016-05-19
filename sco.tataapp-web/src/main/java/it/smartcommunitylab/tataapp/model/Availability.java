package it.smartcommunitylab.tataapp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Availability {
	private long date;
	private long fromTime;
	private long toTime;

	public Availability() {

	}

	public Availability(Availability a) {
		this.date = a.getDate();
		this.fromTime = a.getFromTime();
		this.toTime = a.getToTime();
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getFromTime() {
		return fromTime;
	}

	public void setFromTime(long fromTime) {
		this.fromTime = fromTime;
	}

	public long getToTime() {
		return toTime;
	}

	public void setToTime(long toTime) {
		this.toTime = toTime;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		DateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		return String.format("%s [%s - %s]", dayFormat.format(new Date(date)), timeFormat.format(new Date(fromTime)),
				timeFormat.format(new Date(toTime)));
	}
}
