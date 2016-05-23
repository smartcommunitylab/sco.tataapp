package it.smartcommunitylab.tataapp.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.beans.SearchCriteria;
import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.repo.BabysitterRepo;

@Component
@Profile({ "default", "prod" })
public class BabysitterServiceImpl implements BabysitterService {

	private static final Logger logger = LoggerFactory.getLogger(BabysitterServiceImpl.class);

	@Autowired
	private BabysitterRepo babysitterRepo;

	@Autowired
	private EmailService emailSrv;

	@Autowired
	private SettingsService settingsSrv;

	public Babysitter save(Babysitter babysitter) {
		babysitter.verify();
		return babysitterRepo.save(babysitter);
	}

	public Babysitter load(String agencyId, String id) {
		return babysitterRepo.findByAgencyIdAndId(agencyId, id);
	}

	public Babysitter load(String id) {
		return babysitterRepo.findOne(id);
	}

	public Set<Babysitter> loadAll(String agencyId) {
		return new HashSet<>(babysitterRepo.findAll());
	}

	public Page<Babysitter> loadAll(String agencyId, Pageable p) {
		return babysitterRepo.findByAgencyId(agencyId, p);
	}

	public Page<Babysitter> loadAll(Pageable p) {
		return babysitterRepo.findAll(p);
	}

	public void delete(String agencyId, String id) {
		babysitterRepo.delete(id);
	}

	@Override
	public Page<Babysitter> search(SearchCriteria criteria, Pageable p) {
		return new PageImpl<Babysitter>(babysitterRepo.searchByMatching(criteria));
	}

	@Scheduled(cron = "${task.reminder}")
	public void reminderTask() {
		List<Babysitter> babysitters = babysitterRepo.findAll();
		for (Babysitter b : babysitters) {
			Settings settings = settingsSrv.loadSettings(b.getAgencyId());
			if (settings != null) {
				Map<String, Object> params = new HashMap<>();
				params.put("tata", b);
				try {
					emailSrv.sendSimpleMail(settings.getEmail(), b.getEmail(), "[tataApp] promemoria disponibilità",
							params, "remind");
					logger.debug("Sended remind email to email {} of agency {}", b.getEmail(), b.getAgencyId());
				} catch (MessagingException e) {
					logger.error("Exception sending remind email for email {} of agency {}", b.getEmail(),
							b.getAgencyId());
				}
			} else {
				logger.error("Settings not found for agency {}", b.getAgencyId());
			}
		}

		logger.info("Task remind email concluded");
	}

}
