package it.smartcommunitylab.tataapp.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylab.tataapp.model.ServiceOffice;
import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.service.SettingsService;

@RestController
@CrossOrigin
public class OfficeController {

	@Autowired
	private SettingsService settingsSrv;

	@RequestMapping(method = RequestMethod.GET, value = "/api/agency/{agencyId}/office")
	public Page<ServiceOffice> readAll(@PathVariable String agencyId, Pageable pageable) {
		Settings s = settingsSrv.loadSettings(agencyId);
		if (s != null) {
			return new PageImpl<>(s.getOffices(), pageable, s.getOffices().size());
		}
		return new PageImpl<>(new ArrayList<ServiceOffice>(), pageable, 0);
	}
}
