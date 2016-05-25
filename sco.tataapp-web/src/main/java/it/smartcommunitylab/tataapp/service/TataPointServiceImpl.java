package it.smartcommunitylab.tataapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.model.TataPoint;
import it.smartcommunitylab.tataapp.repo.TataPointRepo;

@Component
@Profile({ "default", "prod" })
public class TataPointServiceImpl implements TataPointService {

	private static final Logger logger = LoggerFactory.getLogger(TataPointServiceImpl.class);

	@Autowired
	private TataPointRepo repo;

	@Autowired
	private SettingsService settingsSrv;

	@Autowired
	private GoogleCalendarService calendarSrv;

	public TataPoint save(TataPoint tp) {
		return repo.save(tp);
	}

	public Set<TataPoint> loadAll(String agencyId) {
		return repo.findByAgencyId(agencyId);
	}

	public Page<TataPoint> loadAll(String agencyId, Pageable pageable) {
		return repo.findByAgencyId(agencyId, pageable);
	}

	public TataPoint load(String agencyId, String id) {
		return repo.findByAgencyIdAndId(agencyId, id);
	}

	public void delete(String agencyId, String id) {
		repo.deleteByAgencyIdAndId(agencyId, id);
	}

	@Scheduled(cron = "${task.calendar.tatapoint}")
	public void importTask() {
		for (Settings s : settingsSrv.loadSettings()) {
			try {
				List<TataPoint> tatapoints = calendarSrv.importTataPoint(s.getAgencyId());
				repo.deleteByAgencyId(s.getAgencyId());
				repo.save(tatapoints);
				logger.info("Imported tatapoint for agency {}", s.getAgencyId());
			} catch (IOException e) {
				logger.error("Exception importing tatapoint for agency {}", s.getAgencyId());
			}
		}
	}
}
