package com.capgemini.go.service;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.dto.Retailer;
import com.capgemini.go.dto.ReturnReportRequest;
import com.capgemini.go.dto.SalesRep;
import com.capgemini.go.dto.User;
import com.capgemini.go.dto.ViewDetailedSalesReportByProduct;
import com.capgemini.go.dto.ViewSalesReportByUser;
import com.capgemini.go.dto.WrongProductNotification;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.exception.SalesRepresentativeException;

public interface GoAdminService {
	// Others functions

	// Shelf Time Report and Delivery Time Report
	/*******************************************************************************************************
	 * - Author 			: Kunal 
	 * - Creation Date 		: 21/9/2019
	 * - Description 		: Static Enumeration for Different Report Types
	 ********************************************************************************************************/
	public static enum ReportType {
		MONTHLY_SHELF_TIME, QUARTERLY_SHELF_TIME, YEARLY_SHELF_TIME, 
		OUTLIER_PRODUCT_CATEGORY_DELIVERY_TIME, OUTLIER_ITEM_DELIVERY_TIME, OUTLIER_ITEM_IN_OUTLIER_PRODUCT_CATEGORY_DELIVERY_TIME
	}
	
	/*******************************************************************************************************
	 * - Function Name 		: getShelfTimeReport
	 * - Input Parameters 	: ReportType reportType, String retailerId, Calendar dateSelection
	 * - Return Type 		: List<RetailerInventoryBean> 
	 * - Throws 			: N/A
	 * - Author 			: Kunal 
	 * - Creation Date 		: 21/9/2019
	 * - Description 		: to get List of all products and their shelf time periods
	 ********************************************************************************************************/
	public List<RetailerInventoryBean> getShelfTimeReport(ReportType reportType, String retailerId, Calendar dateSelection);

	/*******************************************************************************************************
	 * - Function Name 		: getDeliveryTimeReport
	 * - Input Parameters 	: ReportType reportType, String retailerId, int productCategory
	 * - Return Type 		: List<RetailerInventoryBean> 
	 * - Throws 			: N/A
	 * - Author 			: Kunal 
	 * - Creation Date 		: 21/9/2019
	 * - Description 		: to get List of all products and their Delivery time periods
	 ********************************************************************************************************/
	public List<RetailerInventoryBean> getDeliveryTimeReport(ReportType reportType, String retailerId, int productCategory);
	// end of Shelf Time Report and Delivery Time Report

	List<WrongProductNotification> getNotification(Connection connection) throws GoAdminException;
	List<Double> retrieveReturnReport(ReturnReportRequest request, Connection connection )throws GoAdminException;		
	
	List<User> viewProductMaster(Connection connection) throws GoAdminException;

	
	
	
	SalesRep viewSalesRepData(String salesRepId,Connection connection) throws GoAdminException;

	List<SalesRep> viewAllSalesRepData(Connection connection)throws GoAdminException;

	Retailer viewRetailerData(String RetailerId,Connection connection)throws GoAdminException;

	List<Retailer> viewAllRetailerData(Connection connection)throws GoAdminException;

	List<ViewSalesReportByUser> viewSalesReportByCategory(Date entry, Date exit, int cat,Connection connection)throws GoAdminException;
	
	List<ViewSalesReportByUser> viewSalesReportByUser(Date entry, Date exit, String TargetuserId,Connection connection)throws GoAdminException;
	
	List<ViewSalesReportByUser> viewSalesReportByUserAndCategory(Date entry, Date exit, String TargetuserId,
			int category,Connection connection)throws GoAdminException;
	List<ViewSalesReportByUser> viewSalesReportALLUserAndCategory(Date entry, Date exit,Connection connection)throws GoAdminException;
	
	List<ViewDetailedSalesReportByProduct> viewDetailedSalesReportByProduct(Date entry, Date exit, int cat,Connection connection)throws GoAdminException;
	

	
	
	
	
	
	
	
	
	
	

	void suggestFreqOrderProducts(String userID,Connection connection)throws GoAdminException;

	public boolean addProductMaster(User productMaster, Connection connection) throws GoAdminException;
	
	void setBonus (SalesRep sr, double bonus);
	double getBonus (SalesRep sr) throws SalesRepresentativeException;
	void setTarget(SalesRep sr, double target);
	double getTarget(SalesRep sr) throws SalesRepresentativeException;
	void setDiscount(Retailer ret, double discount);
	double getDiscount(Retailer ret) throws RetailerException;
}
