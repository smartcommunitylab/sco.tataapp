package it.smartcommunitylab.tataapp.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.model.PriceList;

@Component
@Profile({ "default" })
public class DynamicDataServiceImpl implements DynamicDataService {

	@Override
	public PriceList getPriceList(String agencyId) {
		return null;
	}

}
