package it.smartcommunitylab.tataapp.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.sec.IdentityLookupService;
import it.smartcommunitylab.tataapp.service.BabysitterService;
import it.smartcommunitylab.tataapp.service.ImageService;

@RestController
public class BabysitterController {

	private static final Logger logger = LoggerFactory.getLogger(BabysitterController.class);

	@Autowired
	private BabysitterService service;

	@Autowired
	private ImageService imageSrv;

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
	// "/console/api/agency/{agencyId}/tata/{babysitterId}/avatar")
	// public void updloadProfilePicture(@PathVariable String agencyId,
	// @PathVariable String babysitterId,
	// HttpServletRequest request, HttpServletResponse response) {
	// agencyId = identityLookup.getName();
	// try {
	// imageSrv.store(request.getInputStream(), babysitterId);
	// } catch (IOException e) {
	// try {
	// response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problem
	// storing avatar");
	// } catch (IOException e1) {
	// logger.error("IOException sending HTTP error uploading avatar");
	// }
	// }
	// }

	@RequestMapping(method = RequestMethod.POST, value = "/console/api/agency/{agencyId}/tata/{babysitterId}/avatar")
	public void updloadProfilePicture(@PathVariable String agencyId, @PathVariable String babysitterId,
			@RequestParam("tImage") MultipartFile picture, HttpServletRequest request, HttpServletResponse response) {
		agencyId = identityLookup.getName();
		try {
			imageSrv.store(picture.getInputStream(), babysitterId);
		} catch (IOException e) {
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problem storing avatar");
			} catch (IOException e1) {
				logger.error("IOException sending HTTP error uploading avatar");
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/console/api/agency/{agencyId}/tata/{babysitterId}/avatar")
	public void downloadProfilePicture(@PathVariable String agencyId, @PathVariable String babysitterId,
			HttpServletResponse resp) {
		agencyId = identityLookup.getName();
		InputStream in;
		try {
			in = imageSrv.retrieveInputStream(babysitterId);
			OutputStream o = resp.getOutputStream();
			resp.setContentType("image/png");
			byte[] buffer = new byte[1024];
			int c = 0;
			while ((c = in.read(buffer)) != -1) {
				o.write(buffer, 0, c);
			}
		} catch (IOException e) {
			try {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problem getting avatar for download");
			} catch (IOException e1) {
				logger.error("IOException sending HTTP error getting avatar for download");
			}
		}

	}

}
