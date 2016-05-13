package it.smartcommunitylab.tataapp.repo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import it.smartcommunitylab.tataapp.beans.SearchCriteria;
import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.model.RangeAge;

public class BabysitterRepoImpl implements MatchingRepo {

	private static final Logger logger = LoggerFactory.getLogger(BabysitterRepoImpl.class);

	@Autowired
	private MongoTemplate mongo;

	@Override
	public List<Babysitter> searchByMatching(SearchCriteria crit) {
		// check agencyId
		Criteria mongoCrit = Criteria.where("agencyId").is(crit.getAgencyId());

		// check langs
		if (!crit.getLangs().isEmpty()) {
			mongoCrit = mongoCrit.and("languages").all(crit.getLangs());
		} else {
			logger.warn("search matching criteria field {} empty or null", "langs");
		}
		// check rangeAge
		if (!StringUtils.isBlank(crit.getRangeAge())) {
			RangeAge r = RangeAge.parse(crit.getRangeAge());
			if (r != null && r.getLowerBound() != null) {
				mongoCrit = mongoCrit.and("birthdate").gt(r.getLowerBound().getTime());
			}

			if (r != null && r.getUpperBound() != null) {
				if (!mongoCrit.getKey().equals("birthdate")) {
					mongoCrit = mongoCrit.and("birthdate");
				}
				mongoCrit = mongoCrit.lte(r.getUpperBound().getTime());
			}
		} else {
			logger.warn("search matching criteria field {} empty or null", "rangeAge");
		}
		// check carOwner
		mongoCrit = mongoCrit.and("carOwner").is(crit.isCarOwner());

		// check time availability
		if (crit.getFromDate() > 0 && crit.getDays().length > 0) {
			mongoCrit = mongoCrit.and("timeAvailability.fromDate").in(calculateDatesFromRequest(crit));
		} else {
			logger.warn("search matching criteria without dates criteria");
		}
		Query q = new Query(mongoCrit);
		logger.debug("Search matching query {}", q.toString());
		return mongo.find(q, Babysitter.class);
	}

	private List<Long> calculateDatesFromRequest(SearchCriteria crit) {
		List<Long> dates = new ArrayList<>();
		if (crit != null) {
			DateTime start = new DateTime(crit.getFromDate());
			DateTime end = new DateTime(crit.getToDate());
			for (String day : crit.getDays()) {
				DateTime dayDate = start.withDayOfWeek(stringToDay(day));
				DateTime dateCursor = new DateTime(start);
				if (start.isAfter(dayDate)) {
					dateCursor = dayDate.plusWeeks(1);
				} else {
					dateCursor = dayDate;
				}

				while (dateCursor.isBefore(end)) {
					dates.add(dateCursor.getMillis());
					dateCursor = dateCursor.plusWeeks(1);
				}
			}
		}
		return dates;
	}

	private int stringToDay(String day) {
		switch (day) {
		case "MON":
			return DateTimeConstants.MONDAY;
		case "TUE":
			return DateTimeConstants.TUESDAY;
		case "WED":
			return DateTimeConstants.WEDNESDAY;
		case "THU":
			return DateTimeConstants.THURSDAY;
		case "FRI":
			return DateTimeConstants.FRIDAY;
		case "SAT":
			return DateTimeConstants.SATURDAY;
		case "SUN":
			return DateTimeConstants.SUNDAY;
		default:
			throw new IllegalArgumentException(String.format("%s is not a valid day", day));
		}
	}
}
