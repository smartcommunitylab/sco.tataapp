package it.smartcommunitylab.tataapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.service.BabysitterService;

@RestController(value = "/api/tata")
public class BabysitterController {

	@Autowired
	private BabysitterService service;

	@RequestMapping(method = RequestMethod.GET, value = "/agency/{agencyId}")
	public Page<Babysitter> readAll(@PathVariable String agencyId, Pageable pageable) {
		return service.loadAll(agencyId, pageable);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/agency/{agencyId}/tata/{babysitterId}")
	public Babysitter read(@PathVariable String agencyId, @PathVariable String babysitterId) {
		return service.load(agencyId, babysitterId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/agency/{agencyId}")
	public Babysitter read(@RequestBody Babysitter b, @PathVariable String agencyId) {
		return service.save(b);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/agency/{agencyId}/tata/{babysitterId}")
	public void delete(@PathVariable String agencyId, @PathVariable String babysitterId) {
	}
}
