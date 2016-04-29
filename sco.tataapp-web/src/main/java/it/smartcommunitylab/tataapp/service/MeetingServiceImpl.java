package it.smartcommunitylab.tataapp.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.model.Meeting;

@Component
@Profile({ "default" })
public class MeetingServiceImpl implements MeetingService {

	@Override
	public Meeting save(Meeting meeting) {
		// TODO Auto-generated method stub
		return null;
	}

}
