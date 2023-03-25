package com.hsx124.pojo;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private CreditCard creditCard;
	private Iphone ihpone;
	private GiftCard giftCard;
	private String payMethod;

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Iphone getIhpone() {
		return this.ihpone;
	}

	public void setIhpone(Iphone ihpone) {
		this.ihpone = ihpone;
	}

	public GiftCard getGiftCard() {
		return this.giftCard;
	}

	public void setGiftCard(GiftCard giftCard) {
		this.giftCard = giftCard;
	}

	public String getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String toString() {
		return "User [firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email
				+ ", mobile=" + this.mobile +
				", creditCard=" + this.creditCard + ", ihpone=" + this.ihpone + ", giftCard=" + this.giftCard
				+ ", payMethod=" +
				this.payMethod + "]";
	}
}