package it.smartcommunitylab.tataapp.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.smartcommunitylab.tataapp.model.Babysitter;

@Service
public interface BabysitterService {

	Babysitter save(Babysitter babysitter);

	Babysitter load(String agencyId, String id);

	Babysitter load(String id);

	Set<Babysitter> loadAll(String agencyId);

	Page<Babysitter> loadAll(String agencyId, Pageable p);

	Page<Babysitter> loadAll(Pageable p);

	void delete(String agencyId, String id);

}