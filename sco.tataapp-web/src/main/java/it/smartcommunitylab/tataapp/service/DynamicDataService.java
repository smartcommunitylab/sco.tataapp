package it.smartcommunitylab.tataapp.service;

import org.springframework.stereotype.Service;

import it.smartcommunitylab.tataapp.model.PriceList;

@Service
public interface DynamicDataService {

	public PriceList getPriceList(String agencyId);
}
