package it.smartcommunitylab.tataapp.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.smartcommunitylab.tataapp.beans.SearchCriteria;
import it.smartcommunitylab.tataapp.model.Babysitter;

@Service
public interface BabysitterService {

	public Babysitter save(Babysitter babysitter);

	public Babysitter load(String agencyId, String id);

	public Babysitter load(String id);

	public Set<Babysitter> loadAll(String agencyId);

	public Page<Babysitter> loadAll(String agencyId, Pageable p);

	public Page<Babysitter> loadAll(Pageable p);

	public void delete(String agencyId, String id);

	public Page<Babysitter> search(SearchCriteria criteria, Pageable p);
}