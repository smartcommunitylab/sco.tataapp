package it.smartcommunitylab.tataapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.format.DateTimeFormat;

import it.smartcommunitylab.tataapp.beans.SearchCriteria;

public class TemplateMailSearchCriteria extends SearchCriteria {
	private String fromDateFormatted;
	private String toDateFormatted;
	private List<String> daysFormatted = new ArrayList<>();
	private List<String> timeSlotsFormatted = new ArrayList<>();
	private List<String> langsFormatted = new ArrayList<>();

	public TemplateMailSearchCriteria(SearchCriteria c) {
		super(c);
		fromDateFormatted = DateTimeFormat.forPattern("dd/MM/yyyy").print(c.getFromDate());
		toDateFormatted = DateTimeFormat.forPattern("dd/MM/yyyy").print(c.getToDate());
		for (String d : c.getDays()) {
			daysFormatted.add(translateDay(d));
		}

		for (String l : c.getLangs()) {
			langsFormatted.add(translateLang(l));
		}

		for (String ts : c.getTimeSlots()) {
			timeSlotsFormatted.add(translateTimeSlots(ts));
		}
	}

	public String getFromDateFormatted() {
		return fromDateFormatted;
	}

	public void setFromDateFormatted(String fromDateFormatted) {
		this.fromDateFormatted = fromDateFormatted;
	}

	public String getToDateFormatted() {
		return toDateFormatted;
	}

	public void setToDateFormatted(String toDateFormatted) {
		this.toDateFormatted = toDateFormatted;
	}

	private String translateDay(String d) {
		switch (d.toUpperCase()) {
		case "MON":
			return "lunedì";
		case "TUE":
			return "martedì";
		case "WED":
			return "mercoledì";
		case "THU":
			return "giovedì";
		case "FRI":
			return "venerdì";
		case "SAT":
			return "sabato";
		case "SUN":
			return "domenica";
		default:
			return "";
		}
	}

	private String translateTimeSlots(String ts) {
		switch (ts.toUpperCase()) {
		case "MORNING":
			return "mattina";
		case "AFTERNOON":
			return "pomeriggio";
		case "EVENING":
			return "sera";
		case "NIGHT":
			return "notte";
		default:
			return "";
		}
	}

	private String translateLang(String lang) {
		return new Locale(lang).getDisplayLanguage(Locale.ITALIAN);
	}

	public List<String> getDaysFormatted() {
		return daysFormatted;
	}

	public void setDaysFormatted(List<String> daysFormatted) {
		this.daysFormatted = daysFormatted;
	}

	public List<String> getTimeSlotsFormatted() {
		return timeSlotsFormatted;
	}

	public void setTimeSlotsFormatted(List<String> timeSlotsFormatted) {
		this.timeSlotsFormatted = timeSlotsFormatted;
	}

	public List<String> getLangsFormatted() {
		return langsFormatted;
	}

	public void setLangsFormatted(List<String> langsFormatted) {
		this.langsFormatted = langsFormatted;
	}
}