package it.smartcommunitylab.tataapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.model.TataPoint;

@Service
public class GoogleCalendarService {

	private static final Logger logger = LoggerFactory.getLogger(GoogleCalendarService.class);

	@Value("classpath:/client_secret.json")
	private Resource apiKeys;

	@Value("${google.calendar.appname}")
	private String applicationName;

	@Autowired
	private SettingsService settingsSrv;

	private static final java.io.File dataStoreDir = new java.io.File(System.getProperty("user.home"),
			".tataapp-credentials/calendar-importer.json");

	@Value("${google.calendar.tatapoint-cal-id}")
	private String IdTatapointCal;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/calendar-java-quickstart.json
	 */
	private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_READONLY);

	private static FileDataStoreFactory dataStoreFactory;

	private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	private static HttpTransport httpTransport;

	static {
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory(dataStoreDir);
		} catch (Throwable t) {
			logger.error("Exception defining google api classes");
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = apiKeys.getInputStream();
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory,
				clientSecrets, SCOPES).setDataStoreFactory(dataStoreFactory).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		return credential;
	}

	/**
	 * Build and return an authorized Calendar client service.
	 * 
	 * @return an authorized Calendar client service
	 * @throws IOException
	 */
	public com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
		Credential credential = authorize();
		return new com.google.api.services.calendar.Calendar.Builder(httpTransport, jsonFactory, credential)
				.setApplicationName(applicationName).build();
	}

	public List<TataPoint> importTataPoint(String agencyId) throws IOException {

		Settings settings = settingsSrv.loadSettings(agencyId);
		List<TataPoint> result = new ArrayList<>();

		if (settings != null) {
			// Build a new authorized API client service.
			// Note: Do not confuse this class with the
			// com.google.api.services.calendar.model.Calendar class.
			com.google.api.services.calendar.Calendar service = getCalendarService();

			// List the next 10 events from the primary calendar.
			// DateTime now = new DateTime(System.currentTimeMillis());
			// Events events =
			// service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime")
			// .setSingleEvents(true).execute();

			Date[] timeWindow = getActualYearWindow();
			DateTime startWeek = new DateTime(timeWindow[0]);
			DateTime endWeek = new DateTime(timeWindow[1]);

			// timeMax -> tutti eventi che iniziano prima della data
			// timeMin -> tutti eventi che finiscono dopo la data
			Events e = service.events().list(IdTatapointCal).setTimeMax(endWeek).setTimeMin(startWeek)
					.setSingleEvents(false).execute();

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
