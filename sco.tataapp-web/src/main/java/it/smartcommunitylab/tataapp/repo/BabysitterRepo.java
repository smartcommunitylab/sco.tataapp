package it.smartcommunitylab.tataapp.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.smartcommunitylab.tataapp.model.Babysitter;

public interface BabysitterRepo extends MongoRepository<Babysitter, String> {

	public Page<Babysitter> findByAgencyId(String agencyId, Pageable pageable);

	public Babysitter findByAgencyIdAndId(String agencyId, String id);
}
