package it.smartcommunitylab.tataapp.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.smartcommunitylab.tataapp.model.Meeting;

public interface MeetingRepo extends MongoRepository<Meeting, String> {

}
