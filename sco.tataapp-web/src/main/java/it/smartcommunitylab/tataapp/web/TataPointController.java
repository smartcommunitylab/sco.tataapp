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

import it.smartcommunitylab.tataapp.model.TataPoint;
import it.smartcommunitylab.tataapp.service.TataPointService;

@RestController
@CrossOrigin()
public class TataPointController {

	@Autowired
	private TataPointService service;

	@RequestMapping(method = RequestMethod.GET, value = "/api/agency/{agencyId}/tatapoint")
	public Page<TataPoint> readAll(@PathVariable String agencyId, Pageable pageable) {
		return service.loadAll(agencyId, pageable);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/api/agency/{agencyId}/tatapoint/{tatapointId}")
	public TataPoint read(@PathVariable String agencyId, @PathVariable String tatapointId) {
		return service.load(agencyId, tatapointId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/agency/{agencyId}/tatapoint")
	public TataPoint save(@RequestBody TataPoint tatapoint, @PathVariable String agencyId) {
		tatapoint.setAgencyId(agencyId);
		return service.save(tatapoint);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/api/agency/{agencyId}/tatapoint/{tatapointId}")
	public void delete(@PathVariable String agencyId, @PathVariable String tatapointId) {
		service.delete(agencyId, tatapointId);
	}
}
