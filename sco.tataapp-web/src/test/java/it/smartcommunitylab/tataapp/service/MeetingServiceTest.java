package it.smartcommunitylab.tataapp.service;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.smartcommunitylab.tataapp.WebApplication;
import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.model.Child;
import it.smartcommunitylab.tataapp.model.Meeting;
import it.smartcommunitylab.tataapp.model.Representive;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WebApplication.class)
public class MeetingServiceTest {

	@Autowired
	private MeetingService meetingSrv;

	@Autowired
	private BabysitterService babysitterSrv;

	@Test
	public void saveMeeting() {
		Meeting m = new Meeting();

		m.setAgencyId("tataApp");
		m.setChildren(Arrays.asList(new Child(7, false)));
		Representive repr = new Representive();
		repr.setCity("Trento");
		repr.setEmail("family@family.ff");
		repr.setPhone("495929884");
		repr.setName("Miles");
		repr.setSurname("Morales");
		m.setFamilyRepresentive(repr);

		m = meetingSrv.save(m);
		Assert.assertNotNull(m.getId());

	}

	@Test
	public void saveMeetingWithBabysitter() {
		Babysitter b = new Babysitter();
		b.setName("Jessica");
		b.setSurname("Drew");
		b.setEmail("j.drew@localhost");
		b.setAgencyId("tataApp");
		b.setAddress("Via sommarive 18, povo, Trento");
		b.setCarOwner(true);
		b.setCity("Trento");

		b = babysitterSrv.save(b);

		Meeting m = new Meeting();

		m.setAgencyId("tataApp");
		m.setChildren(Arrays.asList(new Child(7, false)));
		Representive repr = new Representive();
		repr.setCity("Trento");
		repr.setEmail("family@family.ff");
		repr.setPhone("495929884");
		repr.setName("Miles");
		repr.setSurname("Morales");
		m.setFamilyRepresentive(repr);

		m.setBabysitterId(b.getId());
		m = meetingSrv.save(m);
		Assert.assertNotNull(m.getId());

	}
}
