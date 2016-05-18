package it.smartcommunitylab.tataapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylab.tataapp.model.Meeting;
import it.smartcommunitylab.tataapp.sec.IdentityLookupService;
import it.smartcommunitylab.tataapp.service.MeetingService;

@RestController
@CrossOrigin()
public class MeetingController {

	@Autowired
	private MeetingService service;

	@Autowired
	private IdentityLookupService identityLookup;

	@RequestMapping(method = RequestMethod.POST, value = "/api/agency/{agencyId}/meeting")
	public Meeting save(@RequestBody Meeting payload, @PathVariable String agencyId) {
		agencyId = identityLookup.getName();
		payload.setAgencyId(agencyId);
		return service.save(payload);
	}
}
