package it.smartcommunitylab.tataapp.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.beans.EstimatationData;
import it.smartcommunitylab.tataapp.beans.EstimatationResult;

@Component
@Profile({ "default", "prod" })
public class EstimatationServiceImpl implements EstimatationService {

	/*
	 * Method calculates estimatation for WEEKLY use of service. Input doesn't
	 * contain data about range of daily hour of usage..so ipotize DAILY HOUR
	 * for estimation (non-Javadoc)
	 * 
	 * @see
	 * it.smartcommunitylab.tataapp.service.EstimatationService#estimate(it.
	 * smartcommunitylab.tataapp.beans.EstimatationData)
	 */

	@Override
	public EstimatationResult estimate(EstimatationData data) {
		Double estimationRate = 0d, totalRate = 0d, bonusRate = 0d;
		if (data != null) {
			Double baseRate = 0d;
			if (data.isDisability()) {
				baseRate = 18d;
			} else {
				if (data.getWeeklyHour() < 15) {
					baseRate = 14.64d;
				} else if (data.getWeeklyHour() < 24) {
					baseRate = 13.95d;
				} else if (data.getWeeklyHour() < 40) {
					baseRate = 13.42d;
				}
			}

			estimationRate = baseRate * data.getWeeklyHour();

			Double bonusRateHour = 0d;
			if (data.isBonusAssignee()) {
				switch (data.getBonusType()) {
				case "type1":
					bonusRateHour = 5.50d;
					break;
				case "type2":
					bonusRateHour = 4.50d;
					break;
				case "type3":
					bonusRateHour = 3d;
					break;
				default:
					break;
				}
			}

			totalRate = estimationRate;
			bonusRate = bonusRateHour * data.getWeeklyHour();
			estimationRate -= bonusRate;
		}

		return new EstimatationResult(data, estimationRate, totalRate, bonusRate);
	}

}
