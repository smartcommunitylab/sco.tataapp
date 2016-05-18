package it.smartcommunitylab.tataapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.model.PriceList;
import it.smartcommunitylab.tataapp.repo.DynamicDataRepo;

@Component
@Profile({ "default" })
public class DynamicDataServiceImpl implements DynamicDataService {

	@Autowired
	private DynamicDataRepo repo;

	@Override
	public PriceList getPriceList(String agencyId) {
		return repo.findByAgencyId(agencyId);
	}

	@Override
	public PriceList savePriceList(String agencyId, PriceList priceList) {
		priceList.setAgencyId(agencyId);
		return repo.save(priceList);
	}

}
