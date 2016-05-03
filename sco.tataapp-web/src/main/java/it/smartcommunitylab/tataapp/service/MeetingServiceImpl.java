package it.smartcommunitylab.tataapp.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.model.Meeting;
import it.smartcommunitylab.tataapp.repo.MeetingRepo;

@Component
@Profile({ "default" })
public class MeetingServiceImpl implements MeetingService {

	private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);
	@Autowired
	private MeetingRepo repo;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BabysitterService babySitterSrv;

	private static final String TEMPLATE_MEETING = "meeting";

	@Override
	public Meeting save(Meeting meeting) {
		// add creation request time
		meeting.setCreationTs(System.currentTimeMillis());
		meeting = repo.save(meeting);

		// send e-mail notification
		sendEmail(meeting);
		return meeting;
	}

	private void sendEmail(Meeting meeting) {

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("representive", meeting.getFamilyRepresentive());
		if (meeting.getBabysitterId() != null) {
			Babysitter b = babySitterSrv.load(meeting.getAgencyId(), meeting.getBabysitterId());
			if (b != null) {
				parameters.put("tata", b);
			}
		}
		parameters.put("children", meeting.getChildren());

		try {
			emailService.sendSimpleMail("to@localhost.cc", "richiesta nuovo colloquio", parameters, TEMPLATE_MEETING);
		} catch (MessagingException e) {
			logger.error("Exception sending email for meeting {}", meeting.getId());
		}
	}

}
