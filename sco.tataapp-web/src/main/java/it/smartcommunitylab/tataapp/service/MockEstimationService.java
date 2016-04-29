package it.smartcommunitylab.tataapp.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.beans.EstimatationData;
import it.smartcommunitylab.tataapp.beans.EstimatationResult;

@Component
@Profile({ "mock" })
public class MockEstimationService implements EstimatationService {

	@Override
	public EstimatationResult estimate(EstimatationData data) {
		return new EstimatationResult(data, 400d);
	}

}
