package com.hsx124.pojo;

public class GiftCard {
	private String giftNo;

	private String lastName;

	private String firstName;

	private String postCode;

	private String state;

	private String city;

	private String street;

	private String street2;

	public String getGiftNo() {
		return this.giftNo;
	}

	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet2() {
		return this.street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String toString() {
		return "GiftCard [giftNo=" + this.giftNo + ", lastName=" + this.lastName + ", firstName=" + this.firstName
				+ ", postCode=" +
				this.postCode + ", state=" + this.state + ", city=" + this.city + ", street=" + this.street
				+ ", street2=" + this.street2 +
				"]";
	}
}
