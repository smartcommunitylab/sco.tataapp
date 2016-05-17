package it.smartcommunitylab.tataapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.smartcommunitylab.tataapp.WebApplication;
import it.smartcommunitylab.tataapp.beans.SearchCriteria;
import it.smartcommunitylab.tataapp.model.Availability;
import it.smartcommunitylab.tataapp.model.Babysitter;
import it.smartcommunitylab.tataapp.model.RangeAge;

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

	@Test
	public void searchMatching() {
		Babysitter b = new Babysitter();
		b.setName("Jessica");
		b.setSurname("Drew");
		b.setEmail("email@tataapp.eu");
		b.setAddress("via sommarive 18");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b.setBirthdate(new GregorianCalendar(1981, 9, 22).getTimeInMillis());
		b.setLanguages(Arrays.asList("IT", "EN"));
		service.save(b);

		SearchCriteria sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setLangs(Arrays.asList("IT"));
		sc.setRangeAge(RangeAge.RANGE2.getRange());
		Assert.assertEquals(1, service.search(sc, null).getContent().size());

		sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setLangs(Arrays.asList("IT", "EN"));
		sc.setRangeAge(RangeAge.RANGE2.getRange());
		Assert.assertEquals(1, service.search(sc, null).getContent().size());

		sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setLangs(Arrays.asList("IT", "EN"));
		sc.setRangeAge(RangeAge.RANGE2.getRange());
		sc.setCarOwner(true);
		Assert.assertEquals(0, service.search(sc, null).getContent().size());

		sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setLangs(Arrays.asList("IT", "FR"));
		sc.setRangeAge(RangeAge.RANGE2.getRange());
		Assert.assertEquals(0, service.search(sc, null).getContent().size());

		sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setLangs(Arrays.asList("IT", "DE", "EN"));
		sc.setRangeAge(RangeAge.RANGE2.getRange());
		Assert.assertEquals(0, service.search(sc, null).getContent().size());
	}

	@Test
	public void searchMatchingRangeAge() {
		Babysitter b = new Babysitter();
		b.setName("Jessica");
		b.setSurname("Drew");
		b.setEmail("email@tataapp.eu");
		b.setAddress("via sommarive 18");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b.setBirthdate(new GregorianCalendar(1981, 9, 22).getTimeInMillis());
		b.setLanguages(Arrays.asList("IT"));
		service.save(b);

		b = new Babysitter();
		b.setName("Cindy");
		b.setSurname("Moon");
		b.setEmail("email@tataapp.eu");
		b.setAddress("via sommarive 18");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b.setBirthdate(new GregorianCalendar(1985, 1, 2).getTimeInMillis());
		b.setLanguages(Arrays.asList("IT"));
		service.save(b);

		b = new Babysitter();
		b.setName("Julia");
		b.setSurname("Carpenter");
		b.setEmail("email@tataapp.eu");
		b.setAddress("via sommarive 18");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b.setBirthdate(new GregorianCalendar(1971, 0, 12).getTimeInMillis());
		b.setLanguages(Arrays.asList("IT"));
		service.save(b);

		SearchCriteria sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setLangs(Arrays.asList("IT"));
		sc.setRangeAge(RangeAge.RANGE1.getRange());
		Assert.assertEquals(0, service.search(sc, null).getContent().size());

		sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setLangs(Arrays.asList("IT"));
		sc.setRangeAge(RangeAge.RANGE2.getRange());
		Assert.assertEquals(2, service.search(sc, null).getContent().size());

		sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setLangs(Arrays.asList("IT"));
		sc.setRangeAge(RangeAge.RANGE3.getRange());
		Assert.assertEquals(1, service.search(sc, null).getContent().size());
	}

	@Test
	public void searchMatchingAvailability() {
		LocalDate epoch = new LocalDate(1970, 1, 1);
		Babysitter b = new Babysitter();
		b.setName("Jessica");
		b.setSurname("Drew");
		b.setEmail("email@tataapp.eu");
		b.setAddress("via sommarive 18");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b.setBirthdate(new LocalDate(1981, 10, 22).toDate().getTime());
		b.setLanguages(Arrays.asList("IT"));

		List<Availability> timeAvailable = new ArrayList<>();
		Availability a = new Availability();
		a.setDate(new LocalDate(2016, 5, 13).toDate().getTime());
		a.setFromTime(epoch.toDateTime(new LocalTime(8, 0)).getMillis());
		a.setToTime(epoch.toDateTime(new LocalTime(18, 0)).getMillis());
		timeAvailable.add(a);

		a = new Availability();
		a.setDate(new LocalDate(2016, 5, 16).toDate().getTime());
		a.setFromTime(epoch.toDateTime(new LocalTime(14, 0)).getMillis());
		a.setToTime(epoch.toDateTime(new LocalTime(18, 30)).getMillis());
		timeAvailable.add(a);

		b.setTimeAvailability(timeAvailable);
		service.save(b);

		timeAvailable = new ArrayList<>();
		a = new Availability();
		a.setDate(new LocalDate(2016, 5, 13).toDate().getTime());
		a.setFromTime(epoch.toDateTime(new LocalTime(14, 0)).getMillis());
		a.setToTime(epoch.toDateTime(new LocalTime(18, 0)).getMillis());
		timeAvailable.add(a);

		a = new Availability();
		a.setDate(new LocalDate(2016, 6, 1).toDate().getTime());
		a.setFromTime(epoch.toDateTime(new LocalTime(8, 0)).getMillis());
		a.setToTime(epoch.toDateTime(new LocalTime(18, 0)).getMillis());
		timeAvailable.add(a);
		a = new Availability();
		a.setDate(new LocalDate(2016, 6, 2).toDate().getTime());
		a.setFromTime(epoch.toDateTime(new LocalTime(8, 0)).getMillis());
		a.setToTime(epoch.toDateTime(new LocalTime(18, 0)).getMillis());
		timeAvailable.add(a);
		b = new Babysitter();
		b.setName("Cindy");
		b.setSurname("Moon");
		b.setEmail("email@tataapp.eu");
		b.setAddress("via sommarive 18");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b.setBirthdate(new GregorianCalendar(1985, 1, 2).getTimeInMillis());
		b.setLanguages(Arrays.asList("IT"));
		b.setTimeAvailability(timeAvailable);
		service.save(b);

		b = new Babysitter();
		b.setName("Julia");
		b.setSurname("Carpenter");
		b.setEmail("email@tataapp.eu");
		b.setAddress("via sommarive 18");
		b.setCity("Trento");
		b.setAgencyId(AGENCY_ID);
		b.setBirthdate(new GregorianCalendar(1971, 0, 12).getTimeInMillis());
		b.setLanguages(Arrays.asList("IT"));
		service.save(b);

		SearchCriteria sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setFromDate(new LocalDate(2016, 5, 15).toDate().getTime());
		sc.setToDate(new LocalDate(2016, 5, 20).toDate().getTime());
		sc.setDays(new String[] { "MON", "TUE", "WED" });
		Assert.assertEquals(1, service.search(sc, null).getContent().size());

		sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setFromDate(new LocalDate(2016, 4, 1).toDate().getTime());
		sc.setToDate(new LocalDate(2016, 5, 30).toDate().getTime());
		sc.setDays(new String[] { "MON", "TUE", "WED", "THU" });
		Assert.assertEquals(1, service.search(sc, null).getContent().size());

		sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setFromDate(new LocalDate(2016, 4, 1).toDate().getTime());
		sc.setToDate(new LocalDate(2016, 5, 30).toDate().getTime());
		sc.setDays(new String[] { "FRI" });
		Assert.assertEquals(2, service.search(sc, null).getContent().size());

		sc = new SearchCriteria();
		sc.setAgencyId(AGENCY_ID);
		sc.setFromDate(new LocalDate(2016, 4, 1).toDate().getTime());
		sc.setToDate(new LocalDate(2016, 4, 30).toDate().getTime());
		sc.setDays(new String[] { "MON", "TUE", "WED", "THU", "FRI" });
		Assert.assertEquals(0, service.search(sc, null).getContent().size());

	}
}
