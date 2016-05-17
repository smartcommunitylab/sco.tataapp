package it.smartcommunitylab.tataapp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.beans.SearchCriteria;
import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.repo.BabysitterRepo;

@Component
@Profile({ "default" })
public class BabysitterServiceImpl implements BabysitterService {

	@Autowired
	private BabysitterRepo babysitterRepo;

	public Babysitter save(Babysitter babysitter) {
		babysitter.verify();
		return babysitterRepo.save(babysitter);
	}

	public Babysitter load(String agencyId, String id) {
		return babysitterRepo.findByAgencyIdAndId(agencyId, id);
	}

	public Babysitter load(String id) {
		return babysitterRepo.findOne(id);
	}

	public Set<Babysitter> loadAll(String agencyId) {
		return new HashSet<>(babysitterRepo.findAll());
	}

	public Page<Babysitter> loadAll(String agencyId, Pageable p) {
		return babysitterRepo.findByAgencyId(agencyId, p);
	}

	public Page<Babysitter> loadAll(Pageable p) {
		return babysitterRepo.findAll(p);
	}

	public void delete(String agencyId, String id) {
		babysitterRepo.delete(id);
	}

	@Override
	public Page<Babysitter> search(SearchCriteria criteria, Pageable p) {
		return new PageImpl<Babysitter>(babysitterRepo.searchByMatching(criteria));
	}

}
