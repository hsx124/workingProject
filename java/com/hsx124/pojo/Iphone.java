package com.hsx124.pojo;

public class Iphone {
	private String model;
	private String screenSize;
	private String color;
	private String capacity;
	private boolean isTradeIn;
	private boolean isAppCare;
	private int orderCount = 1;

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getScreenSize() {
		return this.screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCapacity() {
		return this.capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public boolean isTradeIn() {
		return this.isTradeIn;
	}

	public void setTradeIn(boolean isTradeIn) {
		this.isTradeIn = isTradeIn;
	}

	public boolean isAppCare() {
		return this.isAppCare;
	}

	public void setAppCare(boolean isAppCare) {
		this.isAppCare = isAppCare;
	}

	public int getOrderCount() {
		return this.orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public String toString() {
		return "Iphone [model=" + this.model + ", screenSize=" + this.screenSize + ", color=" + this.color
				+ ", capacity=" + this.capacity +
				", isTradeIn=" + this.isTradeIn + ", isAppCare=" + this.isAppCare + ", orderCount=" + this.orderCount
				+ "]";
	}
}