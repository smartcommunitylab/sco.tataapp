package it.smartcommunitylab.tataapp.repo;

import java.util.List;

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
		mongoCrit = mongoCrit.and("languages").all(crit.getLangs());

		// check rangeAge
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

		// check carOwner
		mongoCrit = mongoCrit.and("carOwner").is(crit.isCarOwner());

		// check time availability
		Query q = new Query(mongoCrit);
		logger.debug("Search matching query {}", q.toString());
		return mongo.find(q, Babysitter.class);
	}

}
