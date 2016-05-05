package it.smartcommunitylab.tataapp.service;

import org.springframework.stereotype.Service;

import it.smartcommunitylab.tataapp.model.Settings;

@Service
public interface SettingsService {

	public Settings loadSettings(String agencyId);
}
