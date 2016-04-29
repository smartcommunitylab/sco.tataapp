package it.smartcommunitylab.tataapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.beans.SearchCriteria;
import it.smartcommunitylab.tataapp.model.Babysitter;

@Component
@Profile({ "mock" })
public class MockBabysitterService implements BabysitterService {

	private List<Babysitter> babysitters = mock();
	private static Integer idGen = new Integer(1);

	@Override
	public Babysitter save(Babysitter babysitter) {
		babysitter.setId(Integer.toString(idGen++));
		return babysitter;
	}

	@Override
	public Babysitter load(String agencyId, String id) {

		return babysitters.get(0);
	}

	@Override
	public Babysitter load(String id) {
		return babysitters.get(0);
	}

	@Override
	public Set<Babysitter> loadAll(String agencyId) {
		return new HashSet<>(babysitters);
	}

	@Override
	public Page<Babysitter> loadAll(String agencyId, Pageable p) {
		return new PageImpl<>(babysitters);
	}

	@Override
	public Page<Babysitter> loadAll(Pageable p) {
		return new PageImpl<>(babysitters);
	}

	@Override
	public void delete(String agencyId, String id) {
		// TODO Auto-generated method stub

	}

	private List<Babysitter> mock() {
		List<Babysitter> results = new ArrayList<>();
		Babysitter b = new Babysitter();
		b.setAgencyId("tataapp");
		b.setBirthdate(new GregorianCalendar(1990, 4, 22).getTimeInMillis());
		b.setName("Chiara");
		b.setSurname("Rossi");
		b.setAddress("Via sommarive 18, Povo");
		b.setCity("Trento");
		b.setCarOwner(true);
		b.setLanguages(Arrays.asList("it", "en", "de"));
		b.setEmail("chiara@aa.aa");
		b.setId("fdlj3l3");
		results.add(b);

		b = new Babysitter();
		b.setAgencyId("tataapp");
		b.setBirthdate(new GregorianCalendar(1984, 2, 12).getTimeInMillis());
		b.setName("Serena");
		b.setSurname("Bianchi");
		b.setAddress("Via sommarive 18, Povo");
		b.setCity("Trento");
		b.setLanguages(Arrays.asList("it", "en"));
		b.setEmail("serena@aa.aa");
		b.setId("fdlj3ld");
		results.add(b);

		b = new Babysitter();
		b.setAgencyId("tataapp");
		b.setBirthdate(new GregorianCalendar(1983, 1, 2).getTimeInMillis());
		b.setName("Manuala");
		b.setSurname("Verdi");
		b.setAddress("Via sommarive 18, Povo");
		b.setCity("Trento");
		b.setCarOwner(true);
		b.setLanguages(Arrays.asList("it", "fr"));
		b.setEmail("manuela@aa.aa");
		b.setId("fdll3");
		results.add(b);
		return results;

	}

	@Override
	public Page<Babysitter> search(SearchCriteria criteria, Pageable p) {
		return new PageImpl<>(babysitters.subList(0, 2));
	}

}
