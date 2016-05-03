package it.smartcommunitylab.tataapp.model;

public class Child {
	private int age;
	private boolean disability;

	public Child() {
	}

	public Child(int age, boolean disability) {
		super();
		this.age = age;
		this.disability = disability;
	}

	public long getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isDisability() {
		return disability;
	}

	public void setDisability(boolean disability) {
		this.disability = disability;
	}

}
