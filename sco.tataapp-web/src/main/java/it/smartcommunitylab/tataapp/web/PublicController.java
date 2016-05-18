package it.smartcommunitylab.tataapp.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylab.tataapp.beans.EstimatationData;
import it.smartcommunitylab.tataapp.beans.EstimatationResult;
import it.smartcommunitylab.tataapp.beans.SearchCriteria;
import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.model.Meeting;
import it.smartcommunitylab.tataapp.model.ServiceOffice;
import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.model.TataPoint;
import it.smartcommunitylab.tataapp.service.BabysitterService;
import it.smartcommunitylab.tataapp.service.EstimatationService;
import it.smartcommunitylab.tataapp.service.MeetingService;
import it.smartcommunitylab.tataapp.service.SettingsService;
import it.smartcommunitylab.tataapp.service.TataPointService;

@RestController
@CrossOrigin
@RequestMapping(value = "/public")
public class PublicController {

	@Autowired
	private BabysitterService babysitterSrv;

	@Autowired
	private TataPointService tatapointSrv;

	@Autowired
	private EstimatationService estimationSrv;

	@Autowired
	private MeetingService meetingSrv;

	@Autowired
	private SettingsService settingsSrv;

	/*
	 * BABYSITTER APIs
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/agency/{agencyId}/tata")
	public Page<Babysitter> readAllBabysitter(@PathVariable String agencyId, Pageable pageable) {
		return babysitterSrv.loadAll(agencyId, pageable);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/api/agency/{agencyId}/tata/{babysitterId}")
	public Babysitter readBabysitter(@PathVariable String agencyId, @PathVariable String babysitterId) {
		return babysitterSrv.load(agencyId, babysitterId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/agency/{agencyId}/tata/search")
	public Page<Babysitter> searchBabysitter(@RequestBody SearchCriteria criteria, @PathVariable String agencyId,
			Pageable pageable) {
		return babysitterSrv.loadAll(agencyId, pageable);
	}

	/*
	 * TATAPOINT APIs
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/api/agency/{agencyId}/tatapoint")
	public Page<TataPoint> readAllTatapoint(@PathVariable String agencyId, Pageable pageable) {
		return tatapointSrv.loadAll(agencyId, pageable);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/api/agency/{agencyId}/tatapoint/{tatapointId}")
	public TataPoint readTatapoint(@PathVariable String agencyId, @PathVariable String tatapointId) {
		return tatapointSrv.load(agencyId, tatapointId);
	}

	/*
	 * ESTIMATION APIs
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/api/agency/{agencyId}/estimation")
	public EstimatationResult estimate(@RequestBody EstimatationData payload, @PathVariable String agencyId) {
		return estimationSrv.estimate(payload);
	}

	/*
	 * MEETING APIs
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/api/agency/{agencyId}/meeting")
	public Meeting saveMeeting(@RequestBody Meeting payload, @PathVariable String agencyId) {
		payload.setAgencyId(agencyId);
		return meetingSrv.save(payload);
	}

	/*
	 * OFFICE APIs
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/api/agency/{agencyId}/office")
	public Page<ServiceOffice> readAllOffices(@PathVariable String agencyId, Pageable pageable) {
		Settings s = settingsSrv.loadSettings(agencyId);
		if (s != null) {
			return new PageImpl<>(s.getOffices(), pageable, s.getOffices().size());
		}
		return new PageImpl<>(new ArrayList<ServiceOffice>(), pageable, 0);
	}

}
