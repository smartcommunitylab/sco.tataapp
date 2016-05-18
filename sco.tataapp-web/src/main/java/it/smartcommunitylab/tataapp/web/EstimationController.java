package it.smartcommunitylab.tataapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.smartcommunitylab.tataapp.beans.EstimatationData;
import it.smartcommunitylab.tataapp.beans.EstimatationResult;
import it.smartcommunitylab.tataapp.sec.IdentityLookupService;
import it.smartcommunitylab.tataapp.service.EstimatationService;

//@RestController
//@CrossOrigin
public class EstimationController {

	@Autowired
	private EstimatationService service;

	@Autowired
	private IdentityLookupService identityLookup;

	@RequestMapping(method = RequestMethod.POST, value = "/public/api/agency/{agencyId}/estimation")
	public EstimatationResult estimate(@RequestBody EstimatationData payload, @PathVariable String agencyId) {
		agencyId = identityLookup.getName();

		return service.estimate(payload);
	}

}
