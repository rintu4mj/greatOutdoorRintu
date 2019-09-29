package com.capgemini.go.dto;

public class Retailer {
	private String userId;
	private double discount;
	private String cartID;

	public Retailer(String userId, double discount, String cartID) {
		super();
		this.userId = userId;
		this.discount = discount;
		this.cartID = cartID;
	}
	public Retailer()
	{
		
	}

	public Retailer(String userId) {
		super();
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getCartID() {
		return cartID;
	}

	public void setCartID(String cartID) {
		this.cartID = cartID;
	}

	public void printData() {

		System.out.printf("%-20s %-20.2f %n", userId, discount);
	}

}