package it.smartcommunitylab.tataapp.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.beans.EstimatationData;
import it.smartcommunitylab.tataapp.beans.EstimatationResult;

@Component
@Profile({ "default" })
public class EstimatationServiceImpl implements EstimatationService {

	@Override
	public EstimatationResult estimate(EstimatationData data) {
		// TODO Auto-generated method stub
		return null;
	}

}
