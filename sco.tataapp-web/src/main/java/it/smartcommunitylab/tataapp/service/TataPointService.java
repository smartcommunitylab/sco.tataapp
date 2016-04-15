package it.smartcommunitylab.tataapp.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.model.TataPoint;
import it.smartcommunitylab.tataapp.repo.TataPointRepo;

@Component
public class TataPointService {

	@Autowired
	private TataPointRepo repo;

	public TataPoint save(TataPoint tp) {
		return repo.save(tp);
	}

	public Set<TataPoint> loadAll(String agencyId) {
		return repo.findByAgencyId(agencyId);
	}

	public Page<TataPoint> loadAll(String agencyId, Pageable pageable) {
		return repo.findByAgencyId(agencyId, pageable);
	}

	public TataPoint load(String agencyId, String id) {
		return repo.findByAgencyIdAndId(agencyId, id);
	}

	public void delete(String agencyId, String id) {
		repo.delete(id);
	}
}
