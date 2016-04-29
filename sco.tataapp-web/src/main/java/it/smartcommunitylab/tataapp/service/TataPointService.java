package it.smartcommunitylab.tataapp.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.smartcommunitylab.tataapp.model.TataPoint;

@Service
public interface TataPointService {

	public TataPoint save(TataPoint tp);

	public Set<TataPoint> loadAll(String agencyId);

	public Page<TataPoint> loadAll(String agencyId, Pageable pageable);

	public TataPoint load(String agencyId, String id);

	public void delete(String agencyId, String id);
}
