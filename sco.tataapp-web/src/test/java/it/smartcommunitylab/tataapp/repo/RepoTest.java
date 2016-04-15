package it.smartcommunitylab.tataapp.repo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.smartcommunitylab.tataapp.WebApplication;
import it.smartcommunitylab.tataapp.model.Family;
import it.smartcommunitylab.tataapp.model.Representive;
import it.smartcommunitylab.tataapp.repo.FamilyRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WebApplication.class)
public class RepoTest {

	@Autowired
	FamilyRepo familyRepo;

	@Test
	public void persist() {
		Family f = new Family();
		Representive repr = new Representive();
		repr.setEmail("capofamiglia@smartcommunitylab.it");
		f.setRepresentive(repr);
		familyRepo.save(f);

		Assert.assertTrue(familyRepo.findAll().iterator().hasNext());

	}
}
