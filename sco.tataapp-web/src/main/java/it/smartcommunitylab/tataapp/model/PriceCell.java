package it.smartcommunitylab.tataapp.model;

public class PriceCell {
	private String hourRange;
	private Double rate;

	public PriceCell(String hourRange, Double rate) {
		super();
		this.hourRange = hourRange;
		this.rate = rate;
	}

	public String getHourRange() {
		return hourRange;
	}

	public void setHourRange(String hourRange) {
		this.hourRange = hourRange;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
}
