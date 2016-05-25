package it.smartcommunitylab.tataapp.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;

import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.service.SettingsService;

/**
 * Helper to simplify google oauth2 flow in a web application scenario
 * 
 */

@Controller
public class GoogleAuthHelper {

	private static final Logger logger = LoggerFactory.getLogger(GoogleAuthHelper.class);

	private static final Iterable<String> scopes = Arrays.asList(CalendarScopes.CALENDAR_READONLY);
	private static final JsonFactory jsonFactory = new JacksonFactory();
	private static final HttpTransport httpTransport = new NetHttpTransport();
	private static final String GOOGLE_OAUTH_USER_ID_SESSION_ATTR = "google-oauth-userId";
	private static final String GOOGLE_OAUTH_STATE_SESSION_ATTR = "google-oauth-state";

	private final String callbackURI;

	private GoogleAuthorizationCodeFlow flow;

	@Autowired
	private SettingsService settingsSrv;

	public GoogleAuthHelper(String clientId, String clientSecret, String callbackURI) {

		this.callbackURI = callbackURI;

		try {
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientId, clientSecret,
					(Collection<String>) scopes).setDataStoreFactory(createDataStoreFactory()).setAccessType("offline")
							.build();
		} catch (IOException e) {
			logger.error("Exception building google authorization flow");
		}

	}

	public GoogleAuthHelper(InputStream apiKeys, String callbackURI) {

		GoogleClientSecrets gClientSecrets;
		try {
			gClientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(apiKeys));

			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, gClientSecrets,
					(Collection<String>) scopes).setDataStoreFactory(createDataStoreFactory()).setAccessType("offline")
							.build();
		} catch (IOException e) {
			logger.error("Exception building google authorization flow");
		}

		this.callbackURI = callbackURI;

	}

	private DataStoreFactory createDataStoreFactory() {
		final java.io.File dataStoreDir = new java.io.File(System.getProperty("user.home"),
				".tataapp-credentials/calendar-importer.json");

		try {
			return new FileDataStoreFactory(dataStoreDir);
		} catch (IOException e) {
			logger.error("Exception building credential datastore");
			return null;
		}
	}

	/**
	 * Builds a login URL based on client ID, secret, callback URI, and scope.
	 */
	public String buildAuthorizationURL(HttpServletRequest req, String userId) {

		final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();

		if (req != null) {
			req.getSession().setAttribute(GOOGLE_OAUTH_USER_ID_SESSION_ATTR, userId);
		}

		String state = generateStateToken();
		if (req != null) {
			req.getSession().setAttribute(GOOGLE_OAUTH_STATE_SESSION_ATTR, state);
		}
		return url.setRedirectUri(callbackURI).setState(state).build();
	}

	/**
	 * Generates a secure state token.
	 */
	private String generateStateToken() {

		SecureRandom sr1 = new SecureRandom();
		return "google;" + sr1.nextInt();

	}

	/**
	 * Expects an Authentication Code, and makes an authenticated request for
	 * the user's profile information.
	 * 
	 * @param authCode
	 *            : String, authentication code provided by google
	 * @return {@link GoogleUser} formatted user profile information
	 * @throws IOException
	 */
	private Credential storeCredential(final String authCode, final String userId) throws IOException {

		final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(callbackURI).execute();
		return flow.createAndStoreCredential(response, userId);
	}

	public Credential getCredential(final String userId) {
		try {
			return flow.loadCredential(userId);
		} catch (IOException e) {
			logger.error("Exception getting google credential");
			return null;
		}
	}

	/**
	 * Instance of {@link GoogleAuthHelper} for oauth google
	 */

	/**
	 * This rest web service is the one that google called after login (callback
	 * url). First it retrieve code and token that google sends back. It checks
	 * if code and token are not null, then if token is the same that was saved
	 * in session. If it is not response status is UNAUTHORIZED, otherwise it
	 * retrieves user data. If user is not already saved in db, then user is
	 * added in db, iff email is not already used, otherwise it sends an
	 * UNAUTHORIZED status and redirect user to home page without authenticating
	 * him/her. If it is all ok, then it authenticates user in spring security
	 * and create cookie user. Then redirects authenticated user to home page
	 * where user can access protected resources.
	 * 
	 * @param request
	 *            : instance of {@link HttpServletRequest}
	 * @param response
	 *            : instance of {@link HttpServletResponse}
	 * @return redirect to home page
	 */
	@RequestMapping(value = "/google-auth", method = RequestMethod.GET)
	public String confirmStateToken(HttpServletRequest request, HttpServletResponse response) {

		String errorCode = request.getParameter("error");

		// if access denied redirect to home
		if (!"access_denied".equals(errorCode)) {

			String code = request.getParameter("code");
			String userId = (String) request.getSession().getAttribute(GOOGLE_OAUTH_USER_ID_SESSION_ATTR);
			String state = request.getParameter("state");

			String sessionState = (String) request.getSession().getAttribute(GOOGLE_OAUTH_STATE_SESSION_ATTR);
			if (code == null || state == null && !state.equals(sessionState)) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
			try {
				Credential cred = storeCredential(code, userId);

				// application dependent CODE
				if (cred != null) {
					Settings s = settingsSrv.loadSettings(userId);
					if (s != null) {
						s.setCalendarAuthorization(true);
						settingsSrv.save(s);
					}
				}

			} catch (IOException e) {
				logger.error("Exception storing  google credential");
			}
			return "redirect:/";
		}
		return "redirect:/?jump_google=true";
	}
}
