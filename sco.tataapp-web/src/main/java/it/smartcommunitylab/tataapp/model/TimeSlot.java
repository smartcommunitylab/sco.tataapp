package it.smartcommunitylab.tataapp.model;

public enum TimeSlot {
	MORNING(8, 12), AFTERNOON(12, 18), EVENING(18, 22), NIGHT(22, 8);

	private final int fromHour;
	private final int toHour;

	TimeSlot(int fromHour, int toHour) {
		this.fromHour = fromHour;
		this.toHour = toHour;
	}

	public int getFromHour() {
		return fromHour;
	}

	public int getToHour() {
		return toHour;
	}
}
