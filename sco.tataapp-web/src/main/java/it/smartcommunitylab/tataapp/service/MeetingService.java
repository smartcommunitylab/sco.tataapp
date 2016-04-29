package it.smartcommunitylab.tataapp.service;

import org.springframework.stereotype.Service;

import it.smartcommunitylab.tataapp.model.Meeting;

@Service
public interface MeetingService {

	public Meeting save(Meeting meeting);
}
