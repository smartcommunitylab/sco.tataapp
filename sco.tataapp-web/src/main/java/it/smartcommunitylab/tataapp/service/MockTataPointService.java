package it.smartcommunitylab.tataapp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.model.TataPoint;

@Component
@Profile({ "mock" })
public class MockTataPointService implements TataPointService {

	private List<TataPoint> mocks = mockResult();

	private static Integer idGen = new Integer(1);

	@Override
	public TataPoint save(TataPoint tp) {
		tp.setId("" + idGen++);
		return tp;
	}

	@Override
	public Set<TataPoint> loadAll(String agencyId) {
		return new HashSet<>(mocks);
	}

	@Override
	public Page<TataPoint> loadAll(String agencyId, Pageable pageable) {
		return new PageImpl<>(mocks);
	}

	@Override
	public TataPoint load(String agencyId, String id) {
		return mocks.get(0);
	}

	@Override
	public void delete(String agencyId, String id) {
	}

	private List<TataPoint> mockResult() {
		List<TataPoint> r = new ArrayList<>();
		TataPoint t = new TataPoint();

		t.setName("tatapoint al sociale");
		t.setAddress("Via Sommarive, 18, 38123 Povo TN, Italy");
		t.setAgencyId("tataApp");
		t.setContactDescription("Riferimento Associazione TataApp tel. 333-21234344 e-mail: ass@uu.ii");
		t.setRecurrence("WEEKLY", new String[] { "MON", "SUN" });
		t.setStartDate(new GregorianCalendar(2016, 0, 7).getTimeInMillis());
		t.setEndDate(new GregorianCalendar(2016, 2, 7).getTimeInMillis());
		GregorianCalendar gc = new GregorianCalendar();
		gc.clear();
		gc.set(Calendar.HOUR_OF_DAY, 18);
		gc.set(Calendar.MINUTE, 0);
		t.setStartTime(gc.getTimeInMillis());
		gc.clear();
		gc.set(Calendar.HOUR_OF_DAY, 22);
		gc.set(Calendar.MINUTE, 30);
		t.setEndTime(gc.getTimeInMillis());

		r.add(t);

		t = new TataPoint();
		t.setName("Ericcson");
		t.setAddress("Via Aeroporto, Trento, Province of Trento, Italy");
		t.setAgencyId("tataapp");
		t.setContactDescription("Gino Strada tel 0000/111111 gstrada@gg.rr");
		t.setRecurrence("MONTHLY", null);
		t.setStartDate(new GregorianCalendar(2016, 3, 1, 0, 0).getTimeInMillis());
		t.setEndDate(new GregorianCalendar(2016, 6, 31, 0, 0).getTimeInMillis());
		gc = new GregorianCalendar();
		gc.clear();
		gc.set(Calendar.HOUR_OF_DAY, 8);
		gc.set(Calendar.MINUTE, 0);
		t.setStartTime(gc.getTimeInMillis());
		gc = new GregorianCalendar();
		gc.clear();
		gc.set(Calendar.HOUR_OF_DAY, 18);
		gc.set(Calendar.MINUTE, 0);
		t.setEndTime(gc.getTimeInMillis());

		r.add(t);

		return r;

	}
}
