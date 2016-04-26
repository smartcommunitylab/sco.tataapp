package it.smartcommunitylab.tataapp.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.smartcommunitylab.tataapp.WebApplication;
import it.smartcommunitylab.tataapp.model.TataPoint;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WebApplication.class)
public class TataPointServiceTest {

	private static final String AGENCY_ID = "tataapp";

	@Autowired
	private TataPointServiceImpl service;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void clean() {
		mongoTemplate.dropCollection(TataPoint.class);
	}

	@Test
	public void crud() {
		TataPoint tp = new TataPoint();
		tp.setName("point1");
		tp.setAddress("Via sommarive 18");
		tp.setCity("Trento");
		tp.setAgencyId(AGENCY_ID);
		Assert.assertNull(tp.getId());
		tp = service.save(tp);
		Assert.assertNotNull(tp.getId());
		Assert.assertEquals(1, service.loadAll(AGENCY_ID).size());
		Assert.assertNotNull(service.load(AGENCY_ID, tp.getId()));

		service.delete(AGENCY_ID, tp.getId());

		Assert.assertEquals(0, service.loadAll(AGENCY_ID, new PageRequest(1, 20)).getTotalElements());
	}
}
