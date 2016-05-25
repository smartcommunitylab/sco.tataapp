package it.smartcommunitylab.tataapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.smartcommunitylab.tataapp.model.Settings;

@Service
public interface SettingsService {

	public Settings loadSettings(String agencyId);

	public List<Settings> loadSettings();

	public Settings save(Settings s);
}
