package it.smartcommunitylab.tataapp.repo;

import java.util.List;

import it.smartcommunitylab.tataapp.beans.SearchCriteria;
import it.smartcommunitylab.tataapp.model.Babysitter;

public interface MatchingRepo {

	public List<Babysitter> searchByMatching(SearchCriteria crit);
}
