package it.smartcommunitylab.tataapp.repo;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.smartcommunitylab.tataapp.model.TataPoint;

public interface TataPointRepo extends MongoRepository<TataPoint, String> {

	public Page<TataPoint> findByAgencyId(String agencyId, Pageable p);

	public Set<TataPoint> findByAgencyId(String agencyId);

	public TataPoint findByAgencyIdAndId(String agencyId, String id);

}
