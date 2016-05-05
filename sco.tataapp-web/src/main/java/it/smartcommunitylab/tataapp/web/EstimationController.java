package it.smartcommunitylab.tataapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylab.tataapp.beans.EstimatationData;
import it.smartcommunitylab.tataapp.beans.EstimatationResult;
import it.smartcommunitylab.tataapp.service.EstimatationService;

@RestController
@CrossOrigin()
public class EstimationController {

	@Autowired
	private EstimatationService service;

	@RequestMapping(method = RequestMethod.POST, value = "/api/agency/{agencyId}/estimation")
	public EstimatationResult estimate(@RequestBody EstimatationData payload, @PathVariable String agencyId) {
		return service.estimate(payload);
	}
}
