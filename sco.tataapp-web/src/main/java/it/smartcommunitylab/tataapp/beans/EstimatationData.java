package it.smartcommunitylab.tataapp.beans;

public class EstimatationData {
	private long startDate;
	private long endDate;
	private boolean bonusAssignee;
	private String bonusType;
	private double weeklyHour;

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public boolean isBonusAssignee() {
		return bonusAssignee;
	}

	public void setBonusAssignee(boolean bonusAssignee) {
		this.bonusAssignee = bonusAssignee;
	}

	public String getBonusType() {
		return bonusType;
	}

	public void setBonusType(String bonusType) {
		this.bonusType = bonusType;
	}

	public double getWeeklyHour() {
		return weeklyHour;
	}

	public void setWeeklyHour(double weeklyHour) {
		this.weeklyHour = weeklyHour;
	}
}
