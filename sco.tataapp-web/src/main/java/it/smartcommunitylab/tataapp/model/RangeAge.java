package it.smartcommunitylab.tataapp.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public enum RangeAge {
	RANGE1(RangeAge.R1_VALUE), RANGE2(RangeAge.R2_VALUE), RANGE3(RangeAge.R3_VALUE);

	private static final String R1_VALUE = "20-30";
	private static final String R2_VALUE = "30-40";
	private static final String R3_VALUE = "40+";

	private String range;
	private Date lowerBound;
	private Date upperBound;

	public static RangeAge parse(String range) {
		switch (range) {
		case R1_VALUE:
			return RANGE1;
		case R2_VALUE:
			return RANGE2;
		case R3_VALUE:
			return RANGE3;
		default:
			return null;
		}
	}

	private RangeAge(String range) {
		this.range = range;
		GregorianCalendar today = new GregorianCalendar();
		today.set(Calendar.DAY_OF_MONTH, 1);
		today.set(Calendar.MONTH, 0);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		Date todayDate = today.getTime();
		switch (range) {
		case R1_VALUE:
			String[] r = range.split("-");

			today.add(Calendar.YEAR, -Integer.valueOf(r[1]));
			lowerBound = today.getTime();
			today.setTime(todayDate);
			today.add(Calendar.YEAR, -Integer.valueOf(r[0]));
			upperBound = today.getTime();
			break;
		case R2_VALUE:
			r = range.split("-");
			today.setTime(todayDate);
			today.add(Calendar.YEAR, -Integer.valueOf(r[1]));
			lowerBound = today.getTime();
			today.setTime(todayDate);
			today.add(Calendar.YEAR, -Integer.valueOf(r[0]));
			upperBound = today.getTime();
			break;
		case R3_VALUE:
			r = range.split("\\+");
			today.setTime(todayDate);
			today.add(Calendar.YEAR, -Integer.valueOf(r[0]));
			upperBound = today.getTime();
			break;
		default:
			break;
		}
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public Date getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(Date lowerBound) {
		this.lowerBound = lowerBound;
	}

	public Date getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Date upperBound) {
		this.upperBound = upperBound;
	}

}
