
package com.capgemini.go.service;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.dao.GoAdminDao;
import com.capgemini.go.dao.GoAdminDaoImpl;
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
import com.capgemini.go.utility.GoLog;

public class GoAdminServiceImpl implements GoAdminService {

	GoAdminDao goAdmin = new GoAdminDaoImpl();

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : viewProductMaster - Input Parameters : connection - Return
	 * Type : void - Throws : - Author : Agnibha - Creation Date : 21/9/2019 -
	 * Description : to get List of all product master
	 * 
	 * @throws GoAdminException
	 ********************************************************************************************************/
	public List<User> viewProductMaster(Connection connection) throws GoAdminException {

		return goAdmin.viewProductMaster(connection);

	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : sendNotification - Input Parameters :orderReturnDatabase,
	 * userId - Return Type : OrderReturnList Product List - Throws : - Author :
	 * CAPGEMINI - Creation Date : 21/9/2019 - Description : to send Notification if
	 * wrong product is shipped
	 * 
	 * @throws GoAdminException
	 ********************************************************************************************************/
	public List<WrongProductNotification> getNotification(Connection connection) throws GoAdminException {

		List<WrongProductNotification> notifications = new ArrayList<WrongProductNotification>();
		notifications = goAdmin.getNotification(connection);
		return notifications;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : retrieve return report - Input Parameters : Return Report
	 * request , connection mode of report - Return Type : boolean - Throws : -
	 * Author : CAPGEMINI - Creation Date : 21/9/2019 - Description : to retrieve
	 * return report
	 * 
	 * @throws GoAdminException
	 ********************************************************************************************************/
	public List<Double> retrieveReturnReport(ReturnReportRequest request, Connection connection)
			throws GoAdminException {

		return null;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : addProductMaster - Input Parameters :User productMaster -
	 * Return Type : boolean - Throws : - Author : CAPGEMINI - Creation Date :
	 * 21/9/2019 - Description : to add product Master
	 * 
	 * @throws GoAdminException
	 ********************************************************************************************************/
	public boolean addProductMaster(User productmaster, Connection connection) throws GoAdminException {
		boolean productMasterRegistrationStatus = false;
		productMasterRegistrationStatus = goAdmin.addProductMaster(productmaster, connection);

		return productMasterRegistrationStatus;
	}

	GoAdminDao go = new GoAdminDaoImpl();

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewSalesRepData Input Parameters : salesRepId,connection
	 * Return Type : SalesRep Throws : - Author : Rintu Creation Date : 21/9/2019
	 * Description : To view sales report of specific sales representative
	 ********************************************************************************************************/

	public SalesRep viewSalesRepData(String salesRepId, Connection connection) throws GoAdminException {
		SalesRep result = null;
		try {
			result = go.viewSalesRepData(salesRepId, connection);
		} catch (GoAdminException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewAllSalesRepData - Input Parameters : connrction - Return
	 * Type : List - Throws : - Author : Rintu - Creation Date : 21/9/2019 -
	 * Description : To view sales report of all sales representative
	 ********************************************************************************************************/

	public List<SalesRep> viewAllSalesRepData(Connection connection) throws GoAdminException {
		List<SalesRep> result = new ArrayList<SalesRep>();

		try {
			result = go.viewAllSalesRepData(connection);

		} catch (GoAdminException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewAllRetailerData viewSalesRepData Input Parameters :
	 * connection RetailerId Return Type : List Throws : Author : Rintu Creation
	 * Date : 21/9/2019 Description : To view sales report of all retailer
	 ********************************************************************************************************/

	public List<Retailer> viewAllRetailerData(Connection connection) throws GoAdminException {

		List<Retailer> result = new ArrayList<Retailer>();
		try {
			result = go.viewAllRetailerData(connection);
		} catch (GoAdminException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;

	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewRetailerData Data Input Parameters :
	 * RetailerId,connection Return Type : Retailer Throws : Author : Rintu Creation
	 * Date : 21/9/2019 Description : To view sales report of specific retailer
	 ********************************************************************************************************/

	public Retailer viewRetailerData(String RetailerId, Connection connection) throws GoAdminException {

		Retailer result = null;
		try {
			result = go.viewRetailerData(RetailerId, connection);

		} catch (GoAdminException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewSalesReportByCategory Input Parameters : Category , entry
	 * , exit ,connection Return Type : List Throws : Author : Rintu Creation Date :
	 * 21/9/2019 Description : To view sales report of specific product within given
	 * dates ID within given date
	 ********************************************************************************************************/

	public List<ViewSalesReportByUser> viewSalesReportByCategory(Date entry, Date exit, int cat, Connection connection)
			throws GoAdminException {
		List<ViewSalesReportByUser> result = new ArrayList<ViewSalesReportByUser>();
		try {
			result = go.viewSalesReportByCategory(entry, exit, cat, connection);
		} catch (GoAdminException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;

	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewSalesReportByUser Input Parameters : TargetuserId , entry
	 * , exit, connection Return Type : List Throws : Author : Rintu Creation Date :
	 * 21/9/2019 Description : To view sales report of specific user within given
	 * date
	 ********************************************************************************************************/

	public List<ViewSalesReportByUser> viewSalesReportByUser(Date entry, Date exit, String TargetuserId,
			Connection connection) throws GoAdminException {
		List<ViewSalesReportByUser> result = new ArrayList<ViewSalesReportByUser>();
		try {
			result = go.viewSalesReportByUser(entry, exit, TargetuserId, connection);
		} catch (GoAdminException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;

	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewSalesReportByUserAndCategory Input Parameters :
	 * TargetuserId , entry , exit , category ,connection Return Type : List Throws
	 * : Author : Rintu Creation Date : 21/9/2019 Description : To view sales report
	 * of specific product and User within a given date
	 ********************************************************************************************************/

	public List<ViewSalesReportByUser> viewSalesReportByUserAndCategory(Date entry, Date exit, String TargetuserId,
			int category, Connection connection) throws GoAdminException {
		List<ViewSalesReportByUser> result = new ArrayList<ViewSalesReportByUser>();
		try {
			result = go.viewSalesReportByUserAndCategory(entry, exit, TargetuserId, category, connection);
		} catch (GoAdminException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;

	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewSalesReportALLUserAndCategory Input Parameters :
	 * connection , entry , exit , product Return Type : List Throws : Author :
	 * Rintu Creation Date : 21/9/2019 Description : To view sales reports of all
	 * user and all products within a given dates.
	 ********************************************************************************************************/

	public List<ViewSalesReportByUser> viewSalesReportALLUserAndCategory(Date entry, Date exit, Connection connection)
			throws GoAdminException {

		List<ViewSalesReportByUser> result = new ArrayList<ViewSalesReportByUser>();
		try {
			result = go.viewSalesReportALLUserAndCategory(entry, exit, connection);
		} catch (GoAdminException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * Function Name : viewDetailedSalesReportByProduct Input Parameters : category
	 * , entry , exit ,connection product Return Type : List Throws : Author : Rintu
	 * Creation Date : 21/9/2019 Description : To view amount change, percentage
	 * change, color code, month to month change of products
	 ********************************************************************************************************/

	public List<ViewDetailedSalesReportByProduct> viewDetailedSalesReportByProduct(Date entry, Date exit, int cat,
			Connection connection) throws GoAdminException {

		List<ViewDetailedSalesReportByProduct> result = new ArrayList<ViewDetailedSalesReportByProduct>();
		try {
			result = go.viewDetailedSalesReportByProduct(entry, exit, cat, connection);
		} catch (GoAdminException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;

	}

	@Override
	public List<RetailerInventoryBean> getShelfTimeReport(ReportType reportType, String retailerId,
			Calendar dateSelection) {
		RetailerInventory riDto = new RetailerInventory(retailerId, 0, null, null, null, dateSelection); // DTO object
																											// to pass
																											// arguments
		GoAdminDao goAdminDao = new GoAdminDaoImpl(); // Creating an object for Accessing Dao Layer Methods
		List<RetailerInventoryBean> result = null; // List to store results from Dao Layer Methods
		switch (reportType) {
		case MONTHLY_SHELF_TIME: {
			result = goAdminDao.getMonthlyShelfTime(riDto);
			break;
		}
		case QUARTERLY_SHELF_TIME: {
			result = goAdminDao.getQuarterlyShelfTime(riDto);
			break;
		}
		case YEARLY_SHELF_TIME: {
			result = goAdminDao.getYearlyShelfTime(riDto);
			break;
		}
		default: {
			// get monthly report for current month of current year
			riDto.setProductShelfTimeOut(Calendar.getInstance());
			result = goAdminDao.getMonthlyShelfTime(riDto);
			break;
		}
		}
		return result;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getDeliveryTimeReport - Input Parameters : ReportType
	 * reportType, String retailerId, int productCategory - Return Type :
	 * List<RetailerInventoryBean> - Throws : N/A - Author : Kunal - Creation Date :
	 * 21/9/2019 - Description : to get List of all products and their Delivery time
	 * periods
	 ********************************************************************************************************/
	@Override
	public List<RetailerInventoryBean> getDeliveryTimeReport(ReportType reportType, String retailerId,
			int productCategory) {
		RetailerInventory riDto = new RetailerInventory(retailerId, productCategory, null, null, null, null); // DTO
																												// object
																												// to
																												// pass
																												// arguments
		GoAdminDao goAdminDao = new GoAdminDaoImpl(); // Creating an object for Accessing Dao Layer Methods
		List<RetailerInventoryBean> result = null; // List to store results from Dao Layer Methods
		switch (reportType) {
		case OUTLIER_PRODUCT_CATEGORY_DELIVERY_TIME: {
			result = goAdminDao.getOutlierProductCategoryDeliveryTime(riDto);
			break;
		}
		case OUTLIER_ITEM_DELIVERY_TIME: {
			result = goAdminDao.getOutlierItemDeliveryTime(riDto);
			break;
		}
		case OUTLIER_ITEM_IN_OUTLIER_PRODUCT_CATEGORY_DELIVERY_TIME: {
			result = goAdminDao.getOutlierItemInOutlierProductCategoryDeliveryTime(riDto);
			break;
		}
		default: {
			// get monthly report for current month of current year
			result = goAdminDao.getOutlierItemDeliveryTime(riDto);
			break;
		}
		}
		return result;
	}
	// end of Shelf time report and delivery time report

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : suggestFreqOrderProducts - Input Parameters : - Return Type
	 * : void - Throws : - Author : CAPGEMINI - Creation Date : 21/9/2019 -
	 * Description : Admin will suggest products to retailer
	 * 
	 * @throws GoAdminException
	 ********************************************************************************************************/

	public void suggestFreqOrderProducts(String userID, Connection connection) throws GoAdminException {

		goAdmin.suggestFreqOrderProducts(userID, connection);
	}

	public void setBonus(SalesRep sr, double bonus) {

		GoAdminDaoImpl ga = new GoAdminDaoImpl();
		ga.setBonus(sr, bonus);

	}

	public double getBonus(SalesRep sr) throws SalesRepresentativeException {

		double bonus;
		GoAdminDaoImpl ga = new GoAdminDaoImpl();
		bonus = ga.getBonus(sr);
		return bonus;

	}

	public void setTarget(SalesRep sr, double target) {

		GoAdminDaoImpl ga = new GoAdminDaoImpl();
		ga.setTarget(sr, target);

	}

	public double getTarget(SalesRep sr) throws SalesRepresentativeException {

		double target;
		GoAdminDaoImpl ga = new GoAdminDaoImpl();
		target = ga.getTarget(sr);
		return target;
	}

	public void setDiscount(Retailer ret, double discount) {

		GoAdminDaoImpl ga = new GoAdminDaoImpl();
		ga.setDiscount(ret, discount);

	}

	public double getDiscount(Retailer ret) throws RetailerException {

		double discount;
		GoAdminDaoImpl ga = new GoAdminDaoImpl();
		discount = ga.getDiscount(ret);
		return discount;
	}
}
