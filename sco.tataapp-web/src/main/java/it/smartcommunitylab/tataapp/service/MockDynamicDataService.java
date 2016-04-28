package it.smartcommunitylab.tataapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.model.PriceCell;
import it.smartcommunitylab.tataapp.model.PriceList;

@Component
@Profile({ "mock" })
public class MockDynamicDataService implements DynamicDataService {

	@Override
	public PriceList getPriceList(String agencyId) {
		PriceList pl = new PriceList();
		List<PriceCell> list = new ArrayList<>();
		list.add(new PriceCell("<15", 14d));
		list.add(new PriceCell("16-24", 13d));
		list.add(new PriceCell("25-40", 12.45d));
		pl.setDaily(list);

		list = new ArrayList<>();
		list.add(new PriceCell("<15", 24d));
		list.add(new PriceCell("16-24", 23d));
		list.add(new PriceCell("25-40", 22.45d));
		pl.setDailyDisability(list);

		list = new ArrayList<>();
		list.add(new PriceCell("<15", 14d));
		list.add(new PriceCell("16-24", 13d));
		list.add(new PriceCell("25-40", 12.45d));
		pl.setFestive(list);

		list = new ArrayList<>();
		list.add(new PriceCell("<15", 154d));
		list.add(new PriceCell("16-24", 153d));
		list.add(new PriceCell("25-40", 14.45d));
		pl.setFestiveDisability(list);

		list = new ArrayList<>();
		list.add(new PriceCell("<15", 414d));
		list.add(new PriceCell("16-24", 413d));
		list.add(new PriceCell("25-40", 412.45d));
		pl.setNighttime(list);

		list = new ArrayList<>();
		list.add(new PriceCell("<15", 145d));
		list.add(new PriceCell("16-24", 163d));
		list.add(new PriceCell("25-40", 127.45d));
		pl.setNighttimeDisability(list);
		return pl;
	}

}
