package it.smartcommunitylab.tataapp.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.sec.IdentityLookupService;
import it.smartcommunitylab.tataapp.service.SettingsService;

@RestController
public class SettingsController {

	@Autowired
	private SettingsService settingsSrv;

	@Autowired
	private GoogleAuthHelper googleAuthHelper;

	@Autowired
	private IdentityLookupService identityLookup;

	@RequestMapping(method = RequestMethod.GET, value = "/console/api/agency/{agencyId}/settings/permissions")
	public PermissionBean calendarAuthorizationCheck(HttpServletRequest req, @PathVariable String agencyId) {
		agencyId = identityLookup.getName();
		Settings s = settingsSrv.loadSettings(agencyId);
		PermissionBean permBean = new PermissionBean();
		if (s != null) {
			permBean.setCalendarPermissionOk(s.isCalendarAuthorization());
			if (!permBean.isCalendarPermissionOk()) {
				permBean.setAuthorizationURL(googleAuthHelper.buildAuthorizationURL(req, agencyId));
			}
		}
		return permBean;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/console/api/agency/{agencyId}/settings")
	public Settings readSettings(HttpServletRequest req, @PathVariable String agencyId) {
		agencyId = identityLookup.getName();
		Settings s = settingsSrv.loadSettings(agencyId);
		return s;
	}
}

@JsonInclude(Include.NON_NULL)
class PermissionBean {
	private boolean calendarPermissionOk;
	private String authorizationURL;

	public PermissionBean() {
	}

	public PermissionBean(boolean calendarPermissionOk, String authorizationURL) {
		super();
		this.calendarPermissionOk = calendarPermissionOk;
		this.authorizationURL = authorizationURL;
	}

	public boolean isCalendarPermissionOk() {
		return calendarPermissionOk;
	}

	public void setCalendarPermissionOk(boolean calendarPermissionOk) {
		this.calendarPermissionOk = calendarPermissionOk;
	}

	public String getAuthorizationURL() {
		return authorizationURL;
	}

	public void setAuthorizationURL(String authorizationURL) {
		this.authorizationURL = authorizationURL;
	}

}
