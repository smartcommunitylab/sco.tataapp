package it.smartcommunitylab.tataapp.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import it.smartcommunitylab.tataapp.beans.YamlSettings;
import it.smartcommunitylab.tataapp.model.Settings;
import it.smartcommunitylab.tataapp.repo.SettingsRepo;

@Component
@Profile({ "default", "prod" })
public class SettingsServiceImpl implements SettingsService {

	private static final Logger logger = LoggerFactory.getLogger(SettingsServiceImpl.class);
	@Autowired
	private SettingsRepo settingsRepo;

	@Value("classpath:/settings.yml")
	private Resource resource;

	@PostConstruct
	private void init() {
		Yaml yaml = new Yaml(new Constructor(YamlSettings.class));
		try {
			YamlSettings data = (YamlSettings) yaml.load(resource.getInputStream());
			for (Settings s : data.getSettings()) {
				if (settingsRepo.findByAgencyId(s.getAgencyId()) == null) {
					logger.info("init settings for agency {} using settings.yml", s.getAgencyId());
					settingsRepo.save(s);
					logger.info("init settings for agency {} complete", s.getAgencyId());
				}
			}
		} catch (IOException e) {
		}

	}

	@Override
	public Settings loadSettings(String agencyId) {
		return settingsRepo.findByAgencyId(agencyId);
	}

	@Override
	public Settings save(Settings s) {
		return settingsRepo.save(s);
	}

}
