package it.smartcommunitylab.tataapp.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.smartcommunitylab.tataapp.model.Settings;

public interface SettingsRepo extends MongoRepository<Settings, String> {

	public Settings findByAgencyId(String agencyId);
}
