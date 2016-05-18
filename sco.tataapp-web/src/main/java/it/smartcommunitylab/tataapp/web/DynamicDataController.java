package it.smartcommunitylab.tataapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylab.tataapp.model.PriceList;
import it.smartcommunitylab.tataapp.sec.IdentityLookupService;
import it.smartcommunitylab.tataapp.service.DynamicDataService;

@RestController
@CrossOrigin
public class DynamicDataController {

	@Autowired
	private DynamicDataService service;

	@Autowired
	private IdentityLookupService identityLookup;

	@RequestMapping(method = RequestMethod.GET, value = "/api/agency/{agencyId}/pricelist")
	public PriceList getPriceList(@PathVariable String agencyId) {
		agencyId = identityLookup.getName();
		return service.getPriceList(agencyId);
	}
}
