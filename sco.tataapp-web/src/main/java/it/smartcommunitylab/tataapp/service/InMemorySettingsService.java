package it.smartcommunitylab.tataapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import it.smartcommunitylab.tataapp.beans.YamlSettings;
import it.smartcommunitylab.tataapp.model.Settings;

@Service
@Profile({ "mock" })
public class InMemorySettingsService implements SettingsService {

	private static Logger logger = LoggerFactory.getLogger(InMemorySettingsService.class);

	@Value("classpath:/settings.yml")
	private Resource resource;

	private Map<String, Settings> mapping;

	@PostConstruct
	private void init() {
		Yaml yaml = new Yaml(new Constructor(YamlSettings.class));
		try {
			YamlSettings data = (YamlSettings) yaml.load(resource.getInputStream());
			mapping = process(data.getSettings());
		} catch (IOException e) {
			logger.error("exception loading auth settings resource");
			mapping = new HashMap<String, Settings>();
		}

	}

	@Override
	public Settings loadSettings(String agencyId) {
		return mapping.get(agencyId);
	}

	private Map<String, Settings> process(List<Settings> settings) {
		Map<String, Settings> res = new HashMap<>();
		for (Settings s : settings) {
			res.put(s.getAgencyId(), s);
		}
		return res;
	}

	@Override
	public Settings save(Settings s) {
		if (s.getAgencyId() != null) {
			mapping.put(s.getAgencyId(), s);
		}
		return s;
	}

	@Override
	public List<Settings> loadSettings() {
		return new ArrayList<>(mapping.values());
	}

}
