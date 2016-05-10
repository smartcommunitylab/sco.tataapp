package it.smartcommunitylab.tataapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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

import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.model.TataPoint;
import it.smartcommunitylab.tataapp.web.GoogleAuthHelper;

@Service
public class GoogleCalendarService {

	private static final Logger logger = LoggerFactory.getLogger(GoogleCalendarService.class);

	@Autowired
	private SettingsService settingsSrv;

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
			return new com.google.api.services.calendar.Calendar.Builder(httpTransport, jsonFactory, credential)
					.build();
		} else {
			logger.error("No google credential for agency {}", agencyId);
			return null;
		}
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
					for (CalendarListEntry entry : service.calendarList().list().execute().getItems()) {
						if (entry.getSummary().equals(settings.getTatapointCalName())) {
							settings.setTatapointCalId(entry.getId());
							settingsSrv.save(settings);
							logger.info("Found cal id for name {}..saved in settings", settings.getTatapointCalName());
							logger.debug("tatapoint id calendar: {}", entry.getId());
						}

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
						result.add(new TataPoint(event));
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
}
