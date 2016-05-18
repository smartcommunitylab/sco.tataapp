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
import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.repo.MeetingRepo;

@Component
@Profile({ "default", "prod" })
public class MeetingServiceImpl implements MeetingService {

	private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);

	@Autowired
	private MeetingRepo repo;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BabysitterService babySitterSrv;

	@Autowired
	private SettingsService settingsSrv;

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
			Settings s = settingsSrv.loadSettings(meeting.getAgencyId());
			if (s == null) {
				logger.error("Settings not found for agencyId {}, e-mail for meeting cannot be sended",
						meeting.getAgencyId());
			} else {
				emailService.sendSimpleMail(meeting.getFamilyRepresentive().getEmail(), s.getEmail(),
						"richiesta nuovo colloquio", parameters, TEMPLATE_MEETING);
				logger.info("sended new meeting e-mail from {} to {}", meeting.getFamilyRepresentive().getEmail(),
						s.getEmail());
			}
		} catch (MessagingException e) {
			logger.error("Exception sending email for meeting {}", meeting.getId());
		}
	}

}
