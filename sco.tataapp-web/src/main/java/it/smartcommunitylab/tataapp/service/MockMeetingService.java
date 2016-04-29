package it.smartcommunitylab.tataapp.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.model.Meeting;

@Component
@Profile({ "mock" })
public class MockMeetingService implements MeetingService {

	@Override
	public Meeting save(Meeting meeting) {
		return meeting;
	}

}
