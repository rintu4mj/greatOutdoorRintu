package com.capgemini.go.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.dto.Retailer;
import com.capgemini.go.dto.RetailerInventory;
import com.capgemini.go.dto.ReturnReportRequest;
import com.capgemini.go.dto.SalesRep;
import com.capgemini.go.dto.User;
import com.capgemini.go.dto.ViewDetailedSalesReportByProduct;
import com.capgemini.go.dto.ViewSalesReportByUser;
import com.capgemini.go.dto.WrongProductNotification;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.exception.SalesRepresentativeException;

public interface GoAdminDao {

	public List<WrongProductNotification> getNotification(Connection connection) throws GoAdminException;

	public List<Double> retrieveReturnReport(ReturnReportRequest request, Connection connection)throws GoAdminException;

	boolean addProductMaster(User productmaster, Connection connection) throws GoAdminException;
	
	public List<User> viewProductMaster(Connection connection)throws GoAdminException;

	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void suggestFreqOrderProducts(String retailerId, Connection connection ) throws GoAdminException;
	
	// Shelf Time Report and Delivery Time Report
	/*******************************************************************************************************
	 * - Function Name 		: getMonthlyTimeReport
	 * - Input Parameters 	: RetailerInventory queryArguments
	 * - Return Type 		: List<RetailerInventoryBean> 
	 * - Throws 			: N/A
	 * - Author 			: Kunal 
	 * - Creation Date 		: 21/9/2019
	 * - Description 		: to get List of all products and their Monthly Shelf time periods
	 ********************************************************************************************************/
	public List<RetailerInventoryBean> getMonthlyShelfTime (RetailerInventory queryArguments);
	
	/*******************************************************************************************************
	 * - Function Name 		: getQuarterlyTimeReport
	 * - Input Parameters 	: RetailerInventory queryArguments
	 * - Return Type 		: List<RetailerInventoryBean> 
	 * - Throws 			: N/A
	 * - Author 			: Kunal 
	 * - Creation Date 		: 21/9/2019
	 * - Description 		: to get List of all products and their Quarterly Shelf time periods
	 ********************************************************************************************************/
	public List<RetailerInventoryBean> getQuarterlyShelfTime (RetailerInventory queryArguments);
	
	/*******************************************************************************************************
	 * - Function Name 		: getYearlyTimeReport
	 * - Input Parameters 	: RetailerInventory queryArguments
	 * - Return Type 		: List<RetailerInventoryBean> 
	 * - Throws 			: N/A
	 * - Author 			: Kunal 
	 * - Creation Date 		: 21/9/2019
	 * - Description 		: to get List of all products and their Yearly Shelf time periods
	 ********************************************************************************************************/
	public List<RetailerInventoryBean> getYearlyShelfTime (RetailerInventory queryArguments);
	
	/*******************************************************************************************************
	 * - Function Name 		: getOutlierProductCategoryDeliveryTime
	 * - Input Parameters 	: RetailerInventory queryArguments
	 * - Return Type 		: List<RetailerInventoryBean> 
	 * - Throws 			: N/A
	 * - Author 			: Kunal 
	 * - Creation Date 		: 21/9/2019
	 * - Description 		: to get List of all product categories and their Delivery time periods
	 ********************************************************************************************************/
	public List<RetailerInventoryBean> getOutlierProductCategoryDeliveryTime (RetailerInventory queryArguments);
	
	/*******************************************************************************************************
	 * - Function Name 		: getOutlierItemDeliveryTime
	 * - Input Parameters 	: RetailerInventory queryArguments
	 * - Return Type 		: List<RetailerInventoryBean> 
	 * - Throws 			: N/A
	 * - Author 			: Kunal 
	 * - Creation Date 		: 21/9/2019
	 * - Description 		: to get List of all products and their Delivery time periods
	 ********************************************************************************************************/
	public List<RetailerInventoryBean> getOutlierItemDeliveryTime (RetailerInventory queryArguments);
	
	/*******************************************************************************************************
	 * - Function Name 		: getOutlierItemInOutlierProductCategoryDeliveryTime
	 * - Input Parameters 	: RetailerInventory queryArguments
	 * - Return Type 		: List<RetailerInventoryBean> 
	 * - Throws 			: N/A
	 * - Author 			: Kunal 
	 * - Creation Date 		: 21/9/2019
	 * - Description 		: to get List of all products in outlier categories and their Delivery time periods
	 ********************************************************************************************************/
	public List<RetailerInventoryBean> getOutlierItemInOutlierProductCategoryDeliveryTime (RetailerInventory queryArguments);
	// end of Shelf Time Report and Delivery Time Report

	
	public void setBonus(SalesRep sr, double bonus);
	public double getBonus(SalesRep sr) throws SalesRepresentativeException;
	public void setTarget(SalesRep sr, double target);
	public double getTarget(SalesRep sr) throws SalesRepresentativeException;
	public void setDiscount(Retailer ret, double discount);
	public double getDiscount(Retailer ret)  throws RetailerException;
	
}
