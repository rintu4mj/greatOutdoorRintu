
package com.capgemini.go.dto;

public class FrequentOrdered {
	private String retailerID;
	private String productID;
	private String addressID; 
	

	public String getAddressID() {
		return addressID;
	}

	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}

	public FrequentOrdered(String retailerID, String productID,String addressID) {
		super();
		this.retailerID = retailerID;
		this.productID = productID;
		this.addressID = addressID;
		
	}

	public String getRetailerID() {
		return retailerID;
	}

	public void setRetailerID(String retailerID) {
		this.retailerID = retailerID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}


}
