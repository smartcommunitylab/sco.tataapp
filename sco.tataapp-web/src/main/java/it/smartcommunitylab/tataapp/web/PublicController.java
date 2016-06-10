package it.smartcommunitylab.tataapp.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import it.smartcommunitylab.tataapp.model.PriceList;
import it.smartcommunitylab.tataapp.model.ServiceOffice;
import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.model.TataPoint;
import it.smartcommunitylab.tataapp.service.BabysitterService;
import it.smartcommunitylab.tataapp.service.DynamicDataService;
import it.smartcommunitylab.tataapp.service.EstimatationService;
import it.smartcommunitylab.tataapp.service.ImageService;
import it.smartcommunitylab.tataapp.service.MeetingService;
import it.smartcommunitylab.tataapp.service.SettingsService;
import it.smartcommunitylab.tataapp.service.TataPointService;

@RestController
@CrossOrigin
@RequestMapping(value = "/public")
public class PublicController {

	private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

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

	@Autowired
	private DynamicDataService dynamicSrv;

	@Autowired
	private ImageService imageSrv;

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
		criteria.setAgencyId(agencyId);
		return babysitterSrv.search(criteria, pageable);
	}

	@RequestMapping(value = "/api/agency/{agencyId}/tata/{babysitterId}/avatar", method = RequestMethod.GET)
	public void downloadImage(@PathVariable String babysitterId, HttpServletResponse resp) throws Exception {
		try {
			InputStream in = imageSrv.retrieveInputStream(babysitterId);
			OutputStream o = resp.getOutputStream();
			resp.setContentType("image/png");
			byte[] buffer = new byte[1024];
			int c = 0;
			while ((c = in.read(buffer)) != -1) {
				o.write(buffer, 0, c);
			}
		} catch (IOException e) {
			try {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Picture not found");
			} catch (IOException e1) {
				logger.error("IOException sending HTTP error getting avatar for download");
			}
		}
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

	/*
	 * DYNAMIC DATA APIs
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/api/agency/{agencyId}/pricelist")
	public PriceList getPriceList(@PathVariable String agencyId) {
		return dynamicSrv.getPriceList(agencyId);
	}

}
