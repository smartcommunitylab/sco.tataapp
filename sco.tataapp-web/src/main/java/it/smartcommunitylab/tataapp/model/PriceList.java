package it.smartcommunitylab.tataapp.model;

import java.util.List;

public class PriceList {

	private List<PriceCell> daily;
	private List<PriceCell> dailyDisability;
	private List<PriceCell> festive;
	private List<PriceCell> festiveDisability;
	private List<PriceCell> nighttime;
	private List<PriceCell> nighttimeDisability;

	public List<PriceCell> getDaily() {
		return daily;
	}

	public void setDaily(List<PriceCell> daily) {
		this.daily = daily;
	}

	public List<PriceCell> getDailyDisability() {
		return dailyDisability;
	}

	public void setDailyDisability(List<PriceCell> dailyDisability) {
		this.dailyDisability = dailyDisability;
	}

	public List<PriceCell> getFestive() {
		return festive;
	}

	public void setFestive(List<PriceCell> festive) {
		this.festive = festive;
	}

	public List<PriceCell> getFestiveDisability() {
		return festiveDisability;
	}

	public void setFestiveDisability(List<PriceCell> festiveDisability) {
		this.festiveDisability = festiveDisability;
	}

	public List<PriceCell> getNighttime() {
		return nighttime;
	}

	public void setNighttime(List<PriceCell> nighttime) {
		this.nighttime = nighttime;
	}

	public List<PriceCell> getNighttimeDisability() {
		return nighttimeDisability;
	}

	public void setNighttimeDisability(List<PriceCell> nighttimeDisability) {
		this.nighttimeDisability = nighttimeDisability;
	}

}
