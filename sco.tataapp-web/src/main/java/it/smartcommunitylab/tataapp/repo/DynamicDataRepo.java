package it.smartcommunitylab.tataapp.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.smartcommunitylab.tataapp.model.PriceList;

public interface DynamicDataRepo extends MongoRepository<PriceList, String> {

	public PriceList findByAgencyId(String agencyId);
}
