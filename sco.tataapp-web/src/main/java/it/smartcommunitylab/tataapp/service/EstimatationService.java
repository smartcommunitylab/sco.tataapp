package it.smartcommunitylab.tataapp.service;

import org.springframework.stereotype.Service;

import it.smartcommunitylab.tataapp.beans.EstimatationData;
import it.smartcommunitylab.tataapp.beans.EstimatationResult;

@Service
public interface EstimatationService {

	public EstimatationResult estimate(EstimatationData data);
}
