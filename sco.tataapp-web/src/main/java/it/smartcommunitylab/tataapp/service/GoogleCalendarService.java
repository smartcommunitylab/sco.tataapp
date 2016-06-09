package it.smartcommunitylab.tataapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import it.smartcommunitylab.tataapp.model.Availability;
import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.model.TataPoint;
import it.smartcommunitylab.tataapp.web.GoogleAuthHelper;

@Service
public class GoogleCalendarService {

	private static final Logger logger = LoggerFactory.getLogger(GoogleCalendarService.class);

	private static final int START_WORKDAY_HOUR = 8;
	private static final int START_WORKDAY_MINUTE = 0;

	private static final int END_WORKDAY_HOUR = 18;
	private static final int END_WORKDAY_MINUTE = 0;

	@Autowired
	private SettingsService settingsSrv;

	@Autowired
	private BabysitterService babysitterSrv;

	@Autowired
	private GoogleAuthHelper googleAuthHelper;

	private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
	private static HttpTransport httpTransport;

	static {
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		} catch (Throwable t) {
			logger.error("Exception defining google api classes");
		}
	}

	/**
	 * Build and return an authorized Calendar client service.
	 * 
	 * @return an authorized Calendar client service
	 * @throws IOException
	 */
	private com.google.api.services.calendar.Calendar getCalendarService(String agencyId) {
		Credential credential = googleAuthHelper.getCredential(agencyId);
		if (credential != null) {
			// set empty string for application name to avoid log WARNING
			return new com.google.api.services.calendar.Calendar.Builder(httpTransport, jsonFactory, credential)
					.setApplicationName("").build();
		} else {
			logger.error("No google credential for agency {}", agencyId);
			return null;
		}
	}

	public void importTataAvailability(String agencyId) throws IOException {
		com.google.api.services.calendar.Calendar service = getCalendarService(agencyId);

		Date[] timeWindow = getTataAvailabilityWindow();
		DateTime startWeek = new DateTime(timeWindow[0]);
		DateTime endWeek = new DateTime(timeWindow[1]);

		Set<Babysitter> babysitters = babysitterSrv.loadAll(agencyId);
		for (Babysitter babysitter : babysitters) {
			String calendarName = babysitter.getName() + " " + babysitter.getSurname();
			CalendarListEntry entry = searchCalendar(agencyId, calendarName);
			List<Availability> newAvailability = new ArrayList<>();
			if (entry != null) {
				Events e = service.events().list(entry.getId()).setTimeMax(endWeek).setTimeMin(startWeek)
						.setSingleEvents(true).execute();
				List<Event> items = e.getItems();
				logger.info("Read {} events availability of calendar {}", items.size(), calendarName);
				for (Event event : items) {
					if (logger.isDebugEnabled()) {
						logger.debug("calendarName {}", calendarName);
						logger.debug("{} ({} - {})", event.getSummary(),
								event.getStart().getDateTime() != null ? event.getStart().getDateTime()
										: event.getStart().getDate(),
								event.getEnd().getDateTime() != null ? event.getEnd().getDateTime()
										: event.getEnd().getDate());
					}
					List<Availability> availabilityList = extractFromEvent(event);
					if (logger.isDebugEnabled()) {
						for (Availability a : availabilityList) {
							logger.debug(a.toString());
						}
					}
					newAvailability.addAll(availabilityList);
				}

				babysitter.setTimeAvailability(newAvailability);
				logger.info("Extracted total {} availability objects of calendar {}",
						babysitter.getTimeAvailability().size(), calendarName);
				babysitterSrv.save(babysitter);
			}
		}
	}

	private List<Availability> extractFromEvent(Event event) {
		List<Availability> result = new ArrayList<>();
		if (event != null) {
			Availability availability = new Availability();

			LocalDate epoch = new LocalDate(1970, 1, 1);
			DateTime start = event.getStart().getDateTime();
			LocalDate dayStart = start != null ? new LocalDate(start.getValue())
					: new LocalDate(event.getStart().getDate().getValue());
			availability.setDate(dayStart.toDate().getTime());

			int h = START_WORKDAY_HOUR;
			int m = START_WORKDAY_MINUTE;
			if (start != null) {
				org.joda.time.DateTime timeExtracter = new org.joda.time.DateTime(start.getValue());
				h = timeExtracter.getHourOfDay();
				m = timeExtracter.getMinuteOfHour();
			}
			LocalTime time = new LocalTime(h, m);
			availability.setFromTime(epoch.toDateTime(time).getMillis());

			DateTime end = event.getEnd().getDateTime();
			LocalDate dayEnd = end != null ? new LocalDate(end.getValue())
					: new LocalDate(event.getStart().getDate().getValue());

			h = END_WORKDAY_HOUR;
			m = END_WORKDAY_MINUTE;
			if (end != null) {
				org.joda.time.DateTime timeExtracter = new org.joda.time.DateTime(end.getValue());
				h = timeExtracter.getHourOfDay();
				m = timeExtracter.getMinuteOfHour();
			}
			time = new LocalTime(h, m);
			availability.setToTime(epoch.toDateTime(time).getMillis());

			result.add(availability);

			// check if startDay != endDate create an Availability obj for every
			// day in range [startDay,endDay]
			if (!dayStart.isEqual(dayEnd)) {

				for (int i = 1; i <= Days.daysBetween(dayStart, dayEnd).getDays(); i++) {
					Availability newDay = new Availability(availability);
					newDay.setDate(dayStart.plusDays(i).toDate().getTime());
					result.add(newDay);
				}
			}
		}

		return result;
	}

	public List<TataPoint> importTataPoint(String agencyId) throws IOException {

		Settings settings = settingsSrv.loadSettings(agencyId);
		List<TataPoint> result = new ArrayList<>();

		if (settings != null) {
			com.google.api.services.calendar.Calendar service = getCalendarService(agencyId);
			if (service != null) {

				// search tatapoint cal id if it not present in settings of
				// agency
				if (settings.getTatapointCalId() == null) {
					logger.info("Tatapoint cal id not present..start to search for name {}",
							settings.getTatapointCalName());
					CalendarListEntry tatapointCal = searchCalendar(agencyId, settings.getTatapointCalName());
					if (tatapointCal != null) {
						settings.setTatapointCalId(tatapointCal.getId());
						settingsSrv.save(settings);
						logger.info("Found cal id for name {}..saved in settings", settings.getTatapointCalName());
						logger.debug("tatapoint id calendar: {}", tatapointCal.getId());
					}
				}

				if (settings.getTatapointCalId() == null) {
					logger.error("tatapoint cal id not found in settings of agency {}", agencyId);
				} else {

					Date[] timeWindow = getActualYearWindow();
					DateTime startWeek = new DateTime(timeWindow[0]);
					DateTime endWeek = new DateTime(timeWindow[1]);

					// timeMax -> all events that start before datetime
					// timeMin -> all events that end after datetime
					Events e = service.events().list(settings.getTatapointCalId()).setTimeMax(endWeek)
							.setTimeMin(startWeek).setSingleEvents(false).execute();

					List<Event> items = e.getItems();
					logger.info("Read {} events tatapoint", items.size());

					for (Event event : items) {
						if (logger.isDebugEnabled()) {
							logger.debug("{} ({} - {})", event.getSummary(), event.getStart().getDateTime(),
									event.getEnd().getDateTime());
							logger.debug("id " + event.getId());
							logger.debug("recurrence " + event.getRecurrence());
							logger.debug("location " + event.getLocation());
							logger.debug("desc " + event.getDescription());
						}
						result.add(new TataPoint(agencyId, event));
					}
				}
			} else {
				logger.error("Problem creating google calendar service api");
			}
		} else {
			logger.warn("ATTENTION no settings for agency {}", agencyId);
		}
		return result;
	}

	private CalendarListEntry searchCalendar(String agencyId, String pattern) {
		com.google.api.services.calendar.Calendar service = getCalendarService(agencyId);

		try {
			for (CalendarListEntry entry : service.calendarList().list().execute().getItems()) {
				if (entry.getSummary().toLowerCase().contains(pattern.toLowerCase())) {
					logger.info("Found cal for name pattern", pattern);
					return entry;
				}
			}
		} catch (IOException e) {
			logger.error("Exception getting calendar list for agency {}", agencyId, e);
		}

		return null;
	}

	/**
	 * Returns actual year window.
	 * 
	 * @return arrays of size 2 result[0] is lower bound (1 jan actual year
	 *         0:00) result[1] is upper bound (31 dec actual year 23:59)
	 */
	private Date[] getActualYearWindow() {
		Calendar cal = new GregorianCalendar();
		int actualYear = cal.get(Calendar.YEAR);
		Date[] range = new Date[2];
		cal.set(actualYear, 0, 1, 0, 0);
		range[0] = cal.getTime();
		cal.set(actualYear, 11, 31, 23, 59);
		range[1] = cal.getTime();
		return range;
	}

	/*
	 * Return date window used to get tata availability Actually it returns 3
	 * months since today
	 * 
	 * @return arrays of size 2 result[0] is lower bound (today 0:00) result[1]
	 * is upper bound (today +3months 0:00)
	 */
	private Date[] getTataAvailabilityWindow() {
		Calendar cal = new GregorianCalendar();
		Date[] range = new Date[2];
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		range[0] = cal.getTime();
		cal.add(Calendar.MONTH, 3);
		range[1] = cal.getTime();
		return range;

	}
}
