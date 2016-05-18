package it.smartcommunitylab.tataapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.sec.IdentityLookupService;
import it.smartcommunitylab.tataapp.service.BabysitterService;

@RestController
@CrossOrigin
public class BabysitterController {

	@Autowired
	private BabysitterService service;

	@Autowired
	private IdentityLookupService identityLookup;

	@RequestMapping(method = RequestMethod.GET, value = "/console/api/agency/{agencyId}/tata")
	public Page<Babysitter> readAll(@PathVariable String agencyId, Pageable pageable) {
		agencyId = identityLookup.getName();
		return service.loadAll(agencyId, pageable);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/console/api/agency/{agencyId}/tata/{babysitterId}")
	public Babysitter read(@PathVariable String agencyId, @PathVariable String babysitterId) {
		agencyId = identityLookup.getName();
		return service.load(agencyId, babysitterId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/console/api/agency/{agencyId}/tata")
	public Babysitter save(@RequestBody Babysitter b, @PathVariable String agencyId) {
		agencyId = identityLookup.getName();
		b.setAgencyId(agencyId);
		return service.save(b);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/console/api/agency/{agencyId}/tata/{babysitterId}")
	public void delete(@PathVariable String agencyId, @PathVariable String babysitterId) {
		agencyId = identityLookup.getName();
		service.delete(agencyId, babysitterId);
	}

	// @RequestMapping(method = RequestMethod.POST, value =
	// "/public/api/agency/{agencyId}/tata/search")
	// public Page<Babysitter> search(@RequestBody SearchCriteria criteria,
	// @PathVariable String agencyId,
	// Pageable pageable) {
	// agencyId = identityLookup.getName();
	// return service.loadAll(agencyId, pageable);
	// }
}
