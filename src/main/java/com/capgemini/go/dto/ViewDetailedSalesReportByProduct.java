package com.capgemini.go.dto;

import java.time.Month;

public class ViewDetailedSalesReportByProduct {
	
	
	//MONTH\t\t\tREVENUE\t\t\t(M-M)AMOUNT CHANGE\t\t\t(M-M)PERCENTAGE GROWTH\t\t\tCOLOR CODE
	private int month;
	private Double revenue;
	private Double amountChange;
	private Double percentageGrowth;
	private String code;
	
	public ViewDetailedSalesReportByProduct(int month, Double revenue, Double amountChange, Double percentageGrowth,
			String code) {
		super();
		this.month = month;
		this.revenue = revenue;
		this.amountChange = amountChange;
		this.percentageGrowth = percentageGrowth;
		this.code = code;
	}
	public ViewDetailedSalesReportByProduct()
	{
		//default constructor
		
	}
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

	public Double getAmountChange() {
		return amountChange;
	}

	public void setAmountChange(Double amountChange) {
		this.amountChange = amountChange;
	}

	public Double percentageGrowth() {
		return percentageGrowth;
	}

	public void setpercentageGrowth(Double percentageGrowth) {
		this.percentageGrowth = percentageGrowth;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public void printData() {
		
		System.out.printf("%-25s %-25.2f %-25.2f %-25.2f %-25s %n",Month.of(month+1).name(),revenue, amountChange, percentageGrowth,
			code);
		
	}

	

}
