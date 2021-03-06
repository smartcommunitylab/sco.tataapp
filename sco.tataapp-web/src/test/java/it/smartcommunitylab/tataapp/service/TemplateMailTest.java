package it.smartcommunitylab.tataapp.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.smartcommunitylab.tataapp.WebApplication;
import it.smartcommunitylab.tataapp.beans.SearchCriteria;
import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.model.Child;
import it.smartcommunitylab.tataapp.model.Representive;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WebApplication.class)
public class TemplateMailTest {

	@Autowired
	private TemplateMailService generator;

	@Autowired
	MeetingService meeting;

	@Test
	public void testCompleteMeeting() {
		Map<String, Object> parameters = new HashMap<>();
		Representive r = new Representive();
		r.setName("Cletus");
		r.setSurname("Cassidy");
		r.setCity("Trento");
		r.setEmail("c.cassidy@aa.aa");
		r.setPhone("000-0000000");
		parameters.put("representive", r);

		List<Child> children = Arrays.asList(new Child(3, false), new Child(8, true));
		parameters.put("children", children);
		parameters.put("children", children);

		Babysitter tata = new Babysitter();
		tata.setId("1234567");
		tata.setName("Julia");
		tata.setSurname("Carpenter");
		parameters.put("tata", tata);

		System.out.println(generator.generate(parameters, "meeting"));
	}

	@Test
	public void testNoTataMeeting() {
		Map<String, Object> parameters = new HashMap<>();
		Representive r = new Representive();
		r.setName("Cletus");
		r.setSurname("Cassidy");
		r.setCity("Trento");
		r.setEmail("c.cassidy@aa.aa");
		r.setPhone("000-0000000");
		parameters.put("representive", r);

		List<Child> children = Arrays.asList(new Child(3, false), new Child(8, true));
		parameters.put("children", children);

		Babysitter tata = new Babysitter();
		tata.setId("1234567");
		tata.setName("Julia");
		tata.setSurname("Carpenter");

		System.out.println(generator.generate(parameters, "meeting"));
	}

	@Test
	public void testWithSearchRequest() {
		Map<String, Object> parameters = new HashMap<>();
		Representive r = new Representive();
		r.setName("Cletus");
		r.setSurname("Cassidy");
		r.setCity("Trento");
		r.setEmail("c.cassidy@aa.aa");
		r.setPhone("000-0000000");
		parameters.put("representive", r);

		List<Child> children = Arrays.asList(new Child(3, false), new Child(8, true));
		parameters.put("children", children);

		Babysitter tata = new Babysitter();
		tata.setId("1234567");
		tata.setName("Julia");
		tata.setSurname("Carpenter");

		SearchCriteria sc = new SearchCriteria();
		sc.setCarOwner(true);
		sc.setDays(new String[] { "MON", "FRI" });
		sc.setFromDate(new LocalDate(2016, 6, 17).toDate().getTime());
		sc.setToDate(new LocalDate(2016, 8, 20).toDate().getTime());
		sc.setRangeAge("20-30");
		sc.setLangs(Arrays.asList("it", "de", "EN", "es", "fr"));
		sc.setTimeSlots(Arrays.asList("morning", "evening"));
		parameters.put("searchRequest", new TemplateMailSearchCriteria(sc));

		System.out.println(generator.generate(parameters, "meeting"));
	}

	@Test
	public void testWithSearchRequestMissingField() {
		Map<String, Object> parameters = new HashMap<>();
		Representive r = new Representive();
		r.setName("Cletus");
		r.setSurname("Cassidy");
		r.setCity("Trento");
		r.setEmail("c.cassidy@aa.aa");
		r.setPhone("000-0000000");
		parameters.put("representive", r);

		List<Child> children = Arrays.asList(new Child(3, false), new Child(8, true));
		parameters.put("children", children);

		Babysitter tata = new Babysitter();
		tata.setId("1234567");
		tata.setName("Julia");
		tata.setSurname("Carpenter");

		SearchCriteria sc = new SearchCriteria();
		sc.setCarOwner(true);
		// sc.setDays(new String[] { "MON", "FRI" });
		sc.setFromDate(new LocalDate(2016, 6, 17).toDate().getTime());
		sc.setToDate(new LocalDate(2016, 8, 20).toDate().getTime());
		sc.setRangeAge("20-30");
		sc.setLangs(Arrays.asList("it", "de", "EN", "es", "fr"));
		// sc.setTimeSlots(Arrays.asList("morning", "evening"));
		parameters.put("searchRequest", new TemplateMailSearchCriteria(sc));

		System.out.println(generator.generate(parameters, "meeting"));
	}

	@Test
	public void testReminder() {
		Map<String, Object> parameters = new HashMap<>();

		Babysitter tata = new Babysitter();
		tata.setId("1234567");
		tata.setName("Julia");
		tata.setSurname("Carpenter");
		parameters.put("tata", tata);
		System.out.println(generator.generate(parameters, "remind"));
	}
}
