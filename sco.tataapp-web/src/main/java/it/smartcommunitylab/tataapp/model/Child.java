package it.smartcommunitylab.tataapp.model;

public class Child {
	private long birthDate;
	private boolean disability;

	public Child() {
	}

	public Child(long birthDate, boolean disability) {
		super();
		this.birthDate = birthDate;
		this.disability = disability;
	}

	public long getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(long birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isDisability() {
		return disability;
	}

	public void setDisability(boolean disability) {
		this.disability = disability;
	}

}
