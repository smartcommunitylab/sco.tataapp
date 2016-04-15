package it.smartcommunitylab.tataapp.service;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.smartcommunitylab.tataapp.WebApplication;
import it.smartcommunitylab.tataapp.model.Babysitter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WebApplication.class)
public class BabysitterServiceTest {

	private static final String AGENCY_ID = "tataapp";
	@Autowired
	MongoTemplate mongoTemplate;

	@Before
	public void clean() {
		mongoTemplate.dropCollection(Babysitter.class);
	}

	@Autowired
	private BabysitterService service;

	@Test
	public void persist() {
		Set<Babysitter> set = service.loadAll(AGENCY_ID);
		Assert.assertEquals(0, set.size());
		Babysitter b = new Babysitter();
		b.setEmail("email@tataapp.eu");
		b.setAddress("via sommarive 18");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b = service.save(b);
		Assert.assertNotNull(b.getId());
		Assert.assertEquals(1, service.loadAll(AGENCY_ID).size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void persistInvalid() {
		Set<Babysitter> set = service.loadAll(AGENCY_ID);
		Assert.assertEquals(0, set.size());
		Babysitter b = new Babysitter();
		b.setAddress("via sommarive 18");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b = service.save(b);
		Assert.assertNotNull(b.getId());
		Assert.assertEquals(1, service.loadAll(AGENCY_ID).size());
	}

	@Test
	public void edit() {
		Babysitter b = new Babysitter();
		b.setAddress("via sommarive 18");
		b.setEmail("email@tataapp.eu");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b = service.save(b);
		String id = b.getId();
		b.setCity("Rovereto");

		Babysitter edited = service.save(b);

		Assert.assertEquals(id, edited.getId());
		Assert.assertEquals("Rovereto", edited.getCity());
	}

	@Test
	public void delete() {
		Babysitter b = new Babysitter();
		b.setEmail("email@tataapp.eu");
		b.setAddress("via sommarive 18");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b = service.save(b);
		Assert.assertEquals(1, service.loadAll(AGENCY_ID).size());
		service.delete(AGENCY_ID, b.getId());
		Assert.assertEquals(0, service.loadAll(AGENCY_ID).size());
	}
}
