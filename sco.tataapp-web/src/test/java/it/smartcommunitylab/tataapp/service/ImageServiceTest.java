package it.smartcommunitylab.tataapp.service;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.smartcommunitylab.tataapp.WebApplication;
import it.smartcommunitylab.tataapp.model.Babysitter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WebApplication.class)
public class ImageServiceTest {

	@Value("classpath:/mock-avatar/FA01.png")
	private Resource image;

	private static final String AGENCY_ID = "tataApp";

	@Autowired
	private BabysitterService babysitterSrv;

	@Autowired
	private ImageService imageSrv;

	@Autowired
	private MongoTemplate mongo;

	@Before
	public void clean() {
		mongo.dropCollection(Babysitter.class);
	}

	@Test
	public void testCreation() throws IOException {
		Babysitter b = new Babysitter();
		b.setAgencyId(AGENCY_ID);
		b.setEmail("babysitter@tt.aa");
		b.setName("Jessica");
		b.setSurname("Drew");
		b = babysitterSrv.save(b);

		imageSrv.store(image.getInputStream(), b.getId());
	}

	@Test
	public void testRetrieve() throws IOException {
		Babysitter b = new Babysitter();
		b.setAgencyId(AGENCY_ID);
		b.setEmail("babysitter@tt.aa");
		b.setName("Jessica");
		b.setSurname("Drew");
		b = babysitterSrv.save(b);

		imageSrv.store(image.getInputStream(), b.getId());

		Assert.assertNotNull(imageSrv.retrieve(b.getId()));
		Assert.assertNull(imageSrv.retrieve("fakeID"));

	}

}
