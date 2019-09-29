package com.capgemini.go.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.dto.Retailer;
import com.capgemini.go.dto.RetailerInventory;
import com.capgemini.go.dto.ReturnReportRequest;
import com.capgemini.go.dto.SalesRep;
import com.capgemini.go.dto.User;
import com.capgemini.go.dto.ViewDetailedSalesReportByProduct;
import com.capgemini.go.dto.ViewSalesReportByUser;
import com.capgemini.go.dto.WrongProductNotification;
import com.capgemini.go.exception.DatabaseException;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.exception.ProductMasterException;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.exception.SalesRepresentativeException;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.utility.DbConnection;
import com.capgemini.go.utility.GoLog;
import com.capgemini.go.utility.PropertiesLoader;

public class GoAdminDaoImpl implements GoAdminDao {

	Properties exceptionProps = null;

	Connection connection = null;
	private static final String EXCEPTION_PROPERTIES_FILE = "exceptionStatement.properties";
	private static final String GO_PROPERTIES_FILE = "goUtility.properties";

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : viewProductMaster - Input Parameters : connection - Return
	 * Type : void - Throws : - Author : Agnibha - Creation Date : 21/9/2019 -
	 * Description : to get List of all product master
	 * 
	 * @throws GoAdminException
	 ********************************************************************************************************/
	public List<User> viewProductMaster(Connection connection) throws GoAdminException {
		List<User> productMasters = new ArrayList<>();
		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(QuerryMapper.GET_ALL_PRODUCT_MASTER);
			while (resultSet.next()) {
				String userName = resultSet.getString(1);
				String userId = resultSet.getString(2);
				String userMail = resultSet.getString(3);
				long contact = resultSet.getLong(4);
				User productMaster = new User(userName, userId, userMail, null, contact, 0, false);
				productMasters.add(productMaster);

			}
		} catch (SQLException | IOException e) {

			GoLog.logger.error(exceptionProps.getProperty("view_product_master_error"));
			throw new GoAdminException(exceptionProps.getProperty("view_product_master_error"));
		}
		return productMasters;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : sendNotification - Input Parameters : order Return Database
	 * - Return Type : List of OrderReturn - Throws : - Author : AGNIBHA - Creation
	 * Date : 21/9/2019 - Description : to send Notification if Wrong Product
	 * Shipped
	 * 
	 * @throws GoAdminException
	 ********************************************************************************************************/

	public List<WrongProductNotification> getNotification(Connection connection) throws GoAdminException {

		List<WrongProductNotification> notifications = new ArrayList<WrongProductNotification>();
		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(QuerryMapper.GET_WRONG_PRODUCT_NOTIFICATION);
			while (resultset.next()) {
				// int count = resultset.getInt(1);
				String returnProduct = resultset.getString(1);
				String customer = resultset.getString(2);
				int customerType = resultset.getInt(3);
				java.sql.Date returnDate = resultset.getDate(4);

				WrongProductNotification notify = new WrongProductNotification(1, returnProduct, customer, customerType,
						returnDate);
				notifications.add(notify);
			}

		} catch (IOException | SQLException e) {

			throw new GoAdminException(exceptionProps.getProperty("wrong_product_error") + ">>>" + e.getMessage());
		}

		return notifications;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : addProductMaster - Input Parameters :User - Return Type :
	 * boolean - Throws : - Author : AGNIBHA - Creation Date : 21/9/2019 -
	 * Description : add product Master
	 * 
	 * @throws ProductMasterException
	 * @throws GoAdminException
	 ********************************************************************************************************/
	public boolean addProductMaster(User productmaster, Connection connection) throws GoAdminException {

		boolean productMasterRegistration = false;
		UserDao user = new UserDaoImpl();
		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			productMasterRegistration = user.userRegistration(productmaster);
			PreparedStatement statement = connection.prepareStatement(QuerryMapper.PRODUCT_MASTER_REGISTRATION);
			statement.setString(1, productmaster.getUserId());
			statement.executeUpdate();
		} catch (UserException | SQLException | IOException e) {

			GoLog.logger.error(exceptionProps.getProperty("product_master_registering_error"));
			throw new GoAdminException(exceptionProps.getProperty("product_master_registering_error"));
		}
		return productMasterRegistration;

	}

	public void setBonus(SalesRep sr, double bonus) {

		Connection connection = null;

		try {

			// Connecting Database
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			DbConnection.getInstance();
			connection = DbConnection.getConnection();

			Statement stmt = connection.createStatement();
			String sql = "UPDATE `SALES_REP` SET `BONUS`=" + bonus + " WHERE USER_ID='" + sr.getUserId() + "'";
			stmt.executeUpdate(sql);
		}

		catch (DatabaseException | SQLException | IOException e) {
			System.out.println("DATABASE EXCEPTION");

		}

	}

	public double getBonus(SalesRep sr) throws SalesRepresentativeException {

		Connection connection = null;
		double bonus = -1;

		// Finds the bonus from the database
		try {

			// Connecting Database
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			DbConnection.getInstance();
			connection = DbConnection.getConnection();

			// Searching for the bonus information
			PreparedStatement statement = connection.prepareStatement(QuerryMapper.SELECT_SALES_REP_BONUS);
			statement.setString(1, sr.getUserId());

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {

				bonus = rs.getDouble("BONUS");
			}

			else {
				throw new SalesRepresentativeException(exceptionProps.getProperty("SALES_REP_NOT_FOUND"));
			}

		}

		catch (DatabaseException | SQLException | IOException | SalesRepresentativeException e) {

			GoLog.logger.error(e.getMessage());
			throw new SalesRepresentativeException(e.getMessage());

		}

		// returns -1 if bonus not found.
		return bonus;
	}

	public void setTarget(SalesRep sr, double target) {

		Connection connection = null;

		try {

			// Connecting Database
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			DbConnection.getInstance();
			connection = DbConnection.getConnection();

			Statement stmt = connection.createStatement();
			String sql = "UPDATE `SALES_REP` SET `TARGET_SALES`=" + target + " WHERE USER_ID='" + sr.getUserId() + "'";
			stmt.executeUpdate(sql);
		}

		catch (DatabaseException | SQLException | IOException e) {
			System.out.println("EXCEPTION");

		}

	}

	public double getTarget(SalesRep sr) throws SalesRepresentativeException {

		Connection connection = null;
		double target = -1;

		// Finds the bonus from the database
		try {

			// Connecting Database
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			DbConnection.getInstance();
			connection = DbConnection.getConnection();

			// Searching for the bonus information
			PreparedStatement statement = connection.prepareStatement(QuerryMapper.SELECT_SALES_REP_TARGET);
			statement.setString(1, sr.getUserId());

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				target = rs.getDouble("TARGET_SALES");

			}

			else {
				throw new SalesRepresentativeException(exceptionProps.getProperty("SALES_REP_NOT_FOUND"));
			}
		}

		catch (DatabaseException | SQLException | IOException | SalesRepresentativeException e) {
			GoLog.logger.error(e.getMessage());
			throw new SalesRepresentativeException(e.getMessage());
		}

		// returns -1 if bonus not found.
		return target;

	}

	public void setDiscount(Retailer ret, double discount) {
		Connection connection = null;

		try {

			// Connecting Database
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			DbConnection.getInstance();
			connection = DbConnection.getConnection();

			Statement stmt = connection.createStatement();
			String sql = "UPDATE `RETAILER` SET `DISCOUNT`=" + discount + " WHERE USER_ID='" + ret.getUserId() + "'";
			stmt.executeUpdate(sql);
		}

		catch (DatabaseException | SQLException | IOException e) {
			System.out.println("EXCEPTION");
			e.printStackTrace();
		}

	}

	public double getDiscount(Retailer ret) throws RetailerException {
		Connection connection = null;
		double discount = -1;

		// Finds the bonus from the database
		try {

			// Connecting Database
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			DbConnection.getInstance();
			connection = DbConnection.getConnection();

			// Searching for the bonus information
			PreparedStatement statement = connection.prepareStatement(QuerryMapper.SELECT_RETAILER_DISCOUNT);
			statement.setString(1, ret.getUserId());

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {

				discount = rs.getDouble("DISCOUNT");
			}

			else {
				throw new RetailerException(exceptionProps.getProperty("RETAILER NOT FOUND"));
			}
		}

		catch (DatabaseException | SQLException | IOException | RetailerException e) {
			GoLog.logger.error(e.getMessage());
			throw new RetailerException(e.getMessage());
		}

		// returns -1 if bonus not found.
		return discount;

	}

	public void suggestFreqOrderProducts(String retailerId, Connection connection) throws GoAdminException {

		Properties goProps = null;
		try {

			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			PreparedStatement statement = connection.prepareStatement(QuerryMapper.SUGGEST_FREQ_PRODUCTS);
			statement.setString(1, retailerId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt("count(*)") > 3) {
					System.out.println(rs.getString("PRODUCT_ID"));
				}
			}
		}

		catch (SQLException | IOException e) {

			GoLog.logger.error(exceptionProps.getProperty("no_freq_product"));
			throw new GoAdminException(exceptionProps.getProperty("no_freq_product"));
		}
	}

	// Shelf Time Report and Delivery Time Report
	/*******************************************************************************************************
	 * - Function Name : getMonthlyTimeReport - Input Parameters : RetailerInventory
	 * queryArguments - Return Type : List<RetailerInventoryBean> - Throws : N/A -
	 * Author : Kunal - Creation Date : 21/9/2019 - Description : to get List of all
	 * products and their Monthly Shelf time periods
	 ********************************************************************************************************/
	@Override
	public List<RetailerInventoryBean> getMonthlyShelfTime(RetailerInventory queryArguments) {
		// Declaring List where valid objects returned by query will be stored
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean>();
		// Storing given arguments
		String retailerUserId = queryArguments.getRetailerUserId();
		try {
			@SuppressWarnings("static-access")
			Connection connection = DbConnection.getInstance().getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					QuerryMapper.GET_PRODUCTS_AND_SHELFTIMEPERIOD_BY_RETAILER_ID_AND_GIVEN_YEAR_AND_MONTH);
			// filling query statement fields
			stmt.setString(1, retailerUserId);
			stmt.setInt(2, queryArguments.getProductShelfTimeOut().get(Calendar.YEAR));
			stmt.setInt(3, queryArguments.getProductShelfTimeOut().get(Calendar.MONTH));
			// end of filling query statement fields
			// executing query
			ResultSet resultSet = stmt.executeQuery();
			// populating beans with resultant objects
			while (resultSet.next()) {
				RetailerInventoryBean temp = new RetailerInventoryBean();
				temp.setRetailerUserId(retailerUserId);
				temp.setProductCategory(resultSet.getInt(1));
				temp.setProductUIN(resultSet.getString(2));
				Period p = Period.of(0, 0, resultSet.getInt(3));
				temp.setProductDeliveryTimePeriod(null);
				temp.setProductShelfTimePeriod(p);
				result.add(temp);
			}
		} catch (SQLException | DatabaseException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;
	}

	/*******************************************************************************************************
	 * - Function Name : getQuarterlyTimeReport - Input Parameters :
	 * RetailerInventory queryArguments - Return Type : List<RetailerInventoryBean>
	 * - Throws : N/A - Author : Kunal - Creation Date : 21/9/2019 - Description :
	 * to get List of all products and their Quarterly Shelf time periods
	 ********************************************************************************************************/
	@Override
	public List<RetailerInventoryBean> getQuarterlyShelfTime(RetailerInventory queryArguments) {
		// Declaring List where valid objects returned by query will be stored
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean>();
		// Storing given arguments
		String retailerUserId = queryArguments.getRetailerUserId();
		try {
			@SuppressWarnings("static-access")
			Connection connection = DbConnection.getInstance().getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					QuerryMapper.GET_PRODUCTS_AND_SHELFTIMEPERIOD_BY_RETAILER_ID_AND_GIVEN_YEAR_AND_QUARTER);
			// filling query statement fields
			stmt.setString(1, retailerUserId);
			stmt.setInt(2, queryArguments.getProductShelfTimeOut().get(Calendar.YEAR));
			stmt.setInt(3, queryArguments.getProductShelfTimeOut().get(Calendar.MONTH)); // input expected is first
																							// month of quarter
			stmt.setInt(4, queryArguments.getProductShelfTimeOut().get(Calendar.MONTH) + 2);
			// end of filling query statement fields
			// executing query
			ResultSet resultSet = stmt.executeQuery();
			// populating beans with resultant objects
			while (resultSet.next()) {
				RetailerInventoryBean temp = new RetailerInventoryBean();
				temp.setRetailerUserId(retailerUserId);
				temp.setProductCategory(resultSet.getInt(1));
				temp.setProductUIN(resultSet.getString(2));
				Period p = Period.of(0, 0, resultSet.getInt(3));
				temp.setProductDeliveryTimePeriod(null);
				temp.setProductShelfTimePeriod(p);
				result.add(temp);
			}
		} catch (SQLException | DatabaseException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;
	}

	/*******************************************************************************************************
	 * - Function Name : getYearlyTimeReport - Input Parameters : RetailerInventory
	 * queryArguments - Return Type : List<RetailerInventoryBean> - Throws : N/A -
	 * Author : Kunal - Creation Date : 21/9/2019 - Description : to get List of all
	 * products and their Yearly Shelf time periods
	 ********************************************************************************************************/
	@Override
	public List<RetailerInventoryBean> getYearlyShelfTime(RetailerInventory queryArguments) {
		// Declaring List where valid objects returned by query will be stored
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean>();
		// Storing given arguments
		String retailerUserId = queryArguments.getRetailerUserId();
		try {
			@SuppressWarnings("static-access")
			Connection connection = DbConnection.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement(QuerryMapper.GET_PRODUCTS_AND_SHELFTIMEPERIOD_BY_RETAILER_ID_AND_GIVEN_YEAR);
			// filling query statement fields
			stmt.setString(1, retailerUserId);
			stmt.setInt(2, queryArguments.getProductShelfTimeOut().get(Calendar.YEAR));
			// end of filling query statement fields
			// executing query
			ResultSet resultSet = stmt.executeQuery();
			// populating beans with resultant objects
			while (resultSet.next()) {
				RetailerInventoryBean temp = new RetailerInventoryBean();
				temp.setRetailerUserId(retailerUserId);
				temp.setProductCategory(resultSet.getInt(1));
				temp.setProductUIN(resultSet.getString(2));
				Period p = Period.of(0, 0, resultSet.getInt(3));
				temp.setProductDeliveryTimePeriod(null);
				temp.setProductShelfTimePeriod(p);
				result.add(temp);
			}
		} catch (SQLException | DatabaseException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;
	}

	/*******************************************************************************************************
	 * - Function Name : getOutlierProductCategoryDeliveryTime - Input Parameters :
	 * RetailerInventory queryArguments - Return Type : List<RetailerInventoryBean>
	 * - Throws : N/A - Author : Kunal - Creation Date : 21/9/2019 - Description :
	 * to get List of all product categories and their Delivery time periods
	 ********************************************************************************************************/
	@Override
	public List<RetailerInventoryBean> getOutlierProductCategoryDeliveryTime(RetailerInventory queryArguments) {
		String retailerId = queryArguments.getRetailerUserId();
		// Declaring List where valid objects returned by query will be stored
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean>();
		try {
			@SuppressWarnings("static-access")
			Connection connection = DbConnection.getInstance().getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					QuerryMapper.GET_PRODUCTS_AND_DELIVERYTIMEPERIOD_BY_RETAILER_ID_AND_PRODUCTCATEGORY);
			// filling query statement fields
			stmt.setString(1, retailerId);
			// end of filling query statement fields
			// executing query
			ResultSet resultSet = stmt.executeQuery();
			// populating beans with resultant objects
			while (resultSet.next()) {
				RetailerInventoryBean temp = new RetailerInventoryBean();
				temp.setRetailerUserId(retailerId);
				temp.setProductCategory(resultSet.getInt(1));
				Period p = Period.of(0, 0, resultSet.getInt(2));
				temp.setProductDeliveryTimePeriod(p);
				temp.setProductShelfTimePeriod(null);
				result.add(temp);
			}
		} catch (SQLException | DatabaseException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;
	}

	/*******************************************************************************************************
	 * - Function Name : getOutlierItemDeliveryTime - Input Parameters :
	 * RetailerInventory queryArguments - Return Type : List<RetailerInventoryBean>
	 * - Throws : N/A - Author : Kunal - Creation Date : 21/9/2019 - Description :
	 * to get List of all products and their Delivery time periods
	 ********************************************************************************************************/
	@Override
	public List<RetailerInventoryBean> getOutlierItemDeliveryTime(RetailerInventory queryArguments) {
		String retailerId = queryArguments.getRetailerUserId();
		// Declaring List where valid objects returned by query will be stored
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean>();
		try {
			@SuppressWarnings("static-access")
			Connection connection = DbConnection.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement(QuerryMapper.GET_PRODUCTS_AND_DELIVERYTIMEPERIOD_BY_RETAILER_ID);
			// filling query statement fields
			stmt.setString(1, retailerId);
			// end of filling query statement fields
			// executing query
			ResultSet resultSet = stmt.executeQuery();
			// populating beans with resultant objects
			while (resultSet.next()) {
				RetailerInventoryBean temp = new RetailerInventoryBean();
				temp.setRetailerUserId(retailerId);
				temp.setProductCategory(resultSet.getInt(1));
				temp.setProductUIN(resultSet.getString(2));
				Period p = Period.of(0, 0, resultSet.getInt(3));
				temp.setProductDeliveryTimePeriod(p); // Average Delivery Time Period for that product category
				temp.setProductShelfTimePeriod(null);
				result.add(temp);
			}
		} catch (SQLException | DatabaseException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;
	}

	/*******************************************************************************************************
	 * - Function Name : getOutlierItemInOutlierProductCategoryDeliveryTime - Input
	 * Parameters : RetailerInventory queryArguments - Return Type :
	 * List<RetailerInventoryBean> - Throws : N/A - Author : Kunal - Creation Date :
	 * 21/9/2019 - Description : to get List of all products in outlier categories
	 * and their Delivery time periods
	 ********************************************************************************************************/
	@Override
	public List<RetailerInventoryBean> getOutlierItemInOutlierProductCategoryDeliveryTime(
			RetailerInventory queryArguments) {
		// get outlier product categories
		List<RetailerInventoryBean> outlierCategories = this.getOutlierProductCategoryDeliveryTime(queryArguments);
		int minDeliveryTimeCategory = outlierCategories.get(0).getProductCategory();
		int maxDeliveryTimeCategory = outlierCategories.get(outlierCategories.size() - 1).getProductCategory();
		String retailerId = queryArguments.getRetailerUserId();
		// Declaring List where valid objects returned by query will be stored
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean>();
		try {
			@SuppressWarnings("static-access")
			Connection connection = DbConnection.getInstance().getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					QuerryMapper.GET_OUTLIER_PRODUCTS_AND_DELIVERYTIMEPERIOD_BY_RETAILER_ID_AND_PRODUCTCATEGORY);
			// filling query statement fields
			stmt.setString(1, retailerId);
			stmt.setInt(2, minDeliveryTimeCategory);
			// end of filling query statement fields
			// executing query
			ResultSet resultSet = stmt.executeQuery();
			// populating beans with resultant objects
			while (resultSet.next()) {
				RetailerInventoryBean temp = new RetailerInventoryBean();
				temp.setRetailerUserId(retailerId);
				temp.setProductCategory(resultSet.getInt(1));
				temp.setProductUIN(resultSet.getString(2));
				Period p = Period.of(0, 0, resultSet.getInt(3));
				temp.setProductDeliveryTimePeriod(p);
				temp.setProductShelfTimePeriod(null);
				result.add(temp);
			}

			stmt = connection.prepareStatement(
					QuerryMapper.GET_OUTLIER_PRODUCTS_AND_DELIVERYTIMEPERIOD_BY_RETAILER_ID_AND_PRODUCTCATEGORY);
			// filling query statement fields
			stmt.setString(1, retailerId);
			stmt.setInt(2, maxDeliveryTimeCategory);
			// end of filling query statement fields
			// executing query
			resultSet = stmt.executeQuery();
			// populating beans with resultant objects
			while (resultSet.next()) {
				RetailerInventoryBean temp = new RetailerInventoryBean();
				temp.setRetailerUserId(retailerId);
				temp.setProductCategory(resultSet.getInt(1));
				temp.setProductUIN(resultSet.getString(2));
				Period p = Period.of(0, 0, resultSet.getInt(3));
				temp.setProductDeliveryTimePeriod(p);
				temp.setProductShelfTimePeriod(null);
				result.add(temp);
			}
		} catch (SQLException | DatabaseException e) {
			GoLog.logger.error(e.getMessage());
		}
		return result;
	}
	// end of Shelf Time Report and Delivery Time Report

	
	
	
	
	
	
	
	// ------------------------ GreatOutdoor Application --------------------------
		/*******************************************************************************************************
		 * Function Name : viewSalesRepData Input Parameters : salesRepId Return Type :
		 * boolean Throws : - Author : CAPGEMINI Creation Date : 21/9/2019 Description :
		 * To view report of specific sales representative
		 * 
		 * @throws NoConnectionException
		 * @throws UserDoesNotExist
		 ********************************************************************************************************/

		public SalesRep viewSalesRepData(String salesRepId, Connection connection) throws GoAdminException {

			if (connection == null)
				throw new GoAdminException("Connection does not exist");
			if (salesRepId == null)
				throw new GoAdminException("User Id is null");

			SalesRep salesRep = new SalesRep();
			try {

				exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);

				PreparedStatement stmt = connection.prepareStatement(QuerryMapper.SELECT_USER);
				stmt.setString(1, salesRepId);
				ResultSet user = stmt.executeQuery();
				if (!user.isBeforeFirst())
					throw new GoAdminException("No Such UserID Exists");

				PreparedStatement statement = connection.prepareStatement(QuerryMapper.SELECT_SALES_REP);
				statement.setString(1, salesRepId);
				ResultSet rs = statement.executeQuery();

				if (rs.next()) {
					salesRep.setUserId(salesRepId);
					salesRep.setTargetStatus(rs.getInt("TARGET_STATUS"));
					salesRep.setCurrentTargetStatus(rs.getDouble("CURRENT_SALES"));
					salesRep.setTarget(rs.getDouble("TARGET_SALES"));

				} else {
					throw new GoAdminException("User does not Exist");
				}

			} catch (SQLException | IOException e) {
				GoLog.logger.error(exceptionProps.getProperty(e.getMessage()));

			}
			return salesRep;

			// return false;
		}

	// ------------------------ GreatOutdoor Application --------------------------
		/*******************************************************************************************************
		 * Function Name : viewSalesRepData - Input Parameters : - Return Type : boolean
		 * - Throws : - Author : CAPGEMINI - Creation Date : 21/9/2019 - Description :
		 * To view report of all sales representative
		 * 
		 * @throws GoAdminException
		 ********************************************************************************************************/

		public List<SalesRep> viewAllSalesRepData(Connection connection) throws GoAdminException {

			if (connection == null)
				throw new GoAdminException("Connection does not exist");

			List<SalesRep> salesRep = new ArrayList<SalesRep>();
			SalesRep temp;

			Statement statement = null;
			try {

				exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(QuerryMapper.SELECT_ALL_SALES_REP);

				if (!rs.isBeforeFirst())
					throw new GoAdminException("Empty_Database");

				while (rs.next()) {

					temp = new SalesRep();

					temp.setUserId(rs.getString("USER_ID"));
					temp.setTargetStatus(rs.getInt("TARGET_STATUS"));
					temp.setCurrentTargetStatus(rs.getDouble("CURRENT_SALES"));
					temp.setTarget(rs.getDouble("TARGET_SALES"));
					salesRep.add(temp);

				}

			} catch (SQLException | IOException e) {

				GoLog.logger.error(exceptionProps.getProperty(e.getMessage()));

			}
			return salesRep;

		}

	// ------------------------ GreatOutdoor Application --------------------------
		/*******************************************************************************************************
		 * Function Name : viewRetailerData viewSalesRepData Input Parameters :
		 * RetailerId Return Type : boolean Throws : Author : CAPGEMINI Creation Date :
		 * 21/9/2019 Description : To view report of specific retailer
		 ********************************************************************************************************/

		public Retailer viewRetailerData(String RetailerId, Connection connection) throws GoAdminException {

			if (connection == null)
				throw new GoAdminException("Connection does not exist");

			Retailer retailer = new Retailer();
			try {

				if (RetailerId == null)
					throw new GoAdminException("User Id is null");

				PreparedStatement stmt = connection.prepareStatement(QuerryMapper.SELECT_USER);
				stmt.setString(1, RetailerId);
				ResultSet user = stmt.executeQuery();
				if (!user.isBeforeFirst())
					throw new GoAdminException("No Such UserID Exists");

				exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);

				PreparedStatement statement = connection.prepareStatement(QuerryMapper.SELECT_RETAILER);
				statement.setString(1, RetailerId);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {

					retailer.setUserId(RetailerId);
					retailer.setDiscount(rs.getDouble("DISCOUNT"));
				} else
					throw new GoAdminException("User does not Exist");

			} catch (SQLException | IOException e) {
				GoLog.logger.error(exceptionProps.getProperty(e.getMessage()));
			}
			return retailer;

		}

	// ------------------------ GreatOutdoor Application --------------------------
		/*******************************************************************************************************
		 * Function Name : viewAllRetailerData Input Parameters : Return Type : boolean
		 * Throws : Author : CAPGEMINI Creation Date : 21/9/2019 Description : To view
		 * report of all the retailer
		 ********************************************************************************************************/

		public List<Retailer> viewAllRetailerData(Connection connection) throws GoAdminException {

			if (connection == null)
				throw new GoAdminException("Connection does not exist");

			List<Retailer> retailer = new ArrayList<Retailer>();

			Retailer temp;

			try {

				exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);

				PreparedStatement statement = connection.prepareStatement(QuerryMapper.SELECT_ALL_RETAILER);
				ResultSet rs = statement.executeQuery();

				if (!rs.isBeforeFirst())
					throw new GoAdminException("Empty_Database");

				while (rs.next()) {
					temp = new Retailer();
					temp.setUserId(rs.getString("USER_ID"));
					temp.setDiscount(rs.getDouble("DISCOUNT"));
					retailer.add(temp);

				}

			} catch (SQLException | IOException e) {
				GoLog.logger.error(exceptionProps.getProperty(e.getMessage()));

			}
			return retailer;

		}

	// ------------------------ GreatOutdoor Application --------------------------
		/*******************************************************************************************************
		 * Function Name : viewSalesReportByUser Input Parameters : entry , exit ,
		 * TargetuserId Return Type : boolean Throws : Author : CAPGEMINI Creation Date
		 * : 21/9/2019 Description : To view sales report of specific user ID within
		 * given date
		 ********************************************************************************************************/

		public List<ViewSalesReportByUser> viewSalesReportByCategory(Date entry, Date exit, int cat, Connection connection)
				throws GoAdminException {

			if (connection == null)
				throw new GoAdminException("Connection does not exist");

			if (cat < 1 || cat > 5)
				throw new GoAdminException("Invalid Category");
			if (entry == null || exit == null)
				throw new GoAdminException("Invalid Date");

			List<ViewSalesReportByUser> viewSales = new ArrayList<ViewSalesReportByUser>();

			Statement statement = null;
			try {

				exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);

				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(QuerryMapper.SELECT_DATA_FROM_DATABASE);

				if (!rs.isBeforeFirst())
					throw new GoAdminException("Empty_Database");

				while (rs.next()) {

					ViewSalesReportByUser temp = new ViewSalesReportByUser();
					if (rs.getInt("PRODUCT_CATEGORY") == cat) {

						temp.setUserId(rs.getString("USER_ID"));
						temp.setDate(rs.getDate("ORDER_INITIATE_TIME"));
						temp.setOrderId(rs.getString("ORDER_ID"));
						temp.setProductId(rs.getString("PRODUCT_ID"));
						temp.setProductPrice(rs.getDouble("PRODUCT_PRICE"));
						temp.setProductCategory(rs.getInt("PRODUCT_CATEGORY"));
						viewSales.add(temp);
					}

				}
				return viewSales;

			} catch (SQLException | IOException e) {
				GoLog.logger.error(exceptionProps.getProperty(e.getMessage()));

			}
			return viewSales;

		}

		public List<ViewSalesReportByUser> viewSalesReportByUser(Date entry, Date exit, String TargetuserId,
				Connection connection) throws GoAdminException {

			if (connection == null)
				throw new GoAdminException("Connection does not exist");
			if (TargetuserId == null)
				throw new GoAdminException("User Id is null");
			if (entry == null || exit == null)
				throw new GoAdminException("Invalid Date");
			

			List<ViewSalesReportByUser> viewSalesList = new ArrayList<ViewSalesReportByUser>();

			ViewSalesReportByUser temp1;

			Statement statement = null;

			try {

				PreparedStatement stmt = connection.prepareStatement(QuerryMapper.SELECT_USER);
				stmt.setString(1, TargetuserId);
				ResultSet user = stmt.executeQuery();
				if (!user.isBeforeFirst())
					throw new GoAdminException("No Such UserID Exists");

				exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(QuerryMapper.SELECT_DATA_FROM_DATABASE);

				if (!rs.isBeforeFirst())
					throw new GoAdminException("Empty_Database");

				while (rs.next()) {
					if (TargetuserId.equalsIgnoreCase(rs.getString("USER_ID"))) {
						temp1 = new ViewSalesReportByUser();
						temp1.setUserId(rs.getString("USER_ID"));
						temp1.setDate(rs.getDate("ORDER_INITIATE_TIME"));
						temp1.setOrderId(rs.getString("ORDER_ID"));
						temp1.setProductId(rs.getString("PRODUCT_ID"));
						temp1.setProductPrice(rs.getDouble("PRODUCT_PRICE"));
						temp1.setProductCategory(rs.getInt("PRODUCT_CATEGORY"));

						viewSalesList.add(temp1);

					}

				}

			} catch (SQLException | IOException e) {

				GoLog.logger.error(exceptionProps.getProperty(e.getMessage()));
			}
			return viewSalesList;
		}

		public List<ViewSalesReportByUser> viewSalesReportByUserAndCategory(Date entry, Date exit, String TargetuserId,
				int category, Connection connection) throws GoAdminException {

			if (connection == null)
				throw new GoAdminException("Connection does not exist");
			if (category < 1 || category > 5)
				throw new GoAdminException("Invalid Category");
			if (entry == null || exit == null)
				throw new GoAdminException("Invalid Date");

			Statement statement = null;

			List<ViewSalesReportByUser> viewSales = new ArrayList<ViewSalesReportByUser>();
			ViewSalesReportByUser temp;

			try {

				exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);

				PreparedStatement stmt = connection.prepareStatement(QuerryMapper.SELECT_USER);
				stmt.setString(1, TargetuserId);
				ResultSet user = stmt.executeQuery();
				if (!user.isBeforeFirst())
					throw new GoAdminException("No Such UserID Exists");

				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(QuerryMapper.SELECT_DATA_FROM_DATABASE);

				if (!rs.isBeforeFirst())
					throw new GoAdminException("Empty_Database");

				while (rs.next()) {

					if (TargetuserId.equalsIgnoreCase(rs.getString("USER_ID"))
							&& category == rs.getInt("PRODUCT_CATEGORY")) {

						temp = new ViewSalesReportByUser();
						temp.setUserId(rs.getString("USER_ID"));
						temp.setDate(rs.getDate("ORDER_INITIATE_TIME"));
						temp.setOrderId(rs.getString("ORDER_ID"));
						temp.setProductId(rs.getString("PRODUCT_ID"));
						temp.setProductPrice(rs.getDouble("PRODUCT_PRICE"));
						temp.setProductCategory(rs.getInt("PRODUCT_CATEGORY"));
						viewSales.add(temp);
					}
				}
				// if(viewSales.isEmpty())
				// throw new NoDataExistException

			} catch (SQLException | IOException e) {
				GoLog.logger.error(exceptionProps.getProperty(e.getMessage()));

			}
			return viewSales;
		}

		public List<ViewSalesReportByUser> viewSalesReportALLUserAndCategory(Date entry, Date exit, Connection connection)
				throws GoAdminException {

			if (connection == null)
				throw new GoAdminException("Connection does not exist");
			if (entry == null || exit == null)
				throw new GoAdminException("Invalid Date");

			List<ViewSalesReportByUser> viewSales = new ArrayList<ViewSalesReportByUser>();

			ViewSalesReportByUser temp;

			Statement stmt = null;

			try {

				exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
				DbConnection.getInstance();
				connection = DbConnection.getConnection();

				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(QuerryMapper.SELECT_DATA_FROM_DATABASE);

				if (!rs.isBeforeFirst())
					throw new GoAdminException("Empty_Database");

				while (rs.next()) {

					temp = new ViewSalesReportByUser();

					temp.setUserId(rs.getString("USER_ID"));
					temp.setDate(rs.getDate("ORDER_INITIATE_TIME"));
					temp.setOrderId(rs.getString("ORDER_ID"));
					temp.setProductId(rs.getString("PRODUCT_ID"));
					temp.setProductPrice(rs.getDouble("PRODUCT_PRICE"));
					temp.setProductCategory(rs.getInt("PRODUCT_CATEGORY"));
					viewSales.add(temp);
				}
				// if(viewSales.isEmpty())
				// throw new NoDataExistException

			} catch (DatabaseException | SQLException | IOException e) {
				GoLog.logger.error(exceptionProps.getProperty(e.getMessage()));

			}
			return viewSales;

		}

	// ------------------------ GreatOutdoor Application --------------------------
		/*******************************************************************************************************
		 * Function Name : viewDetailedSalesReportByProduct Input Parameters : entry ,
		 * exit , product Return Type : boolean Throws : Author : CAPGEMINI Creation
		 * Date : 21/9/2019 Description : To view amount change, percentage change,
		 * color code, month to month, quarter to quarter, year to year change of
		 * specific product
		 ********************************************************************************************************/

		public List<ViewDetailedSalesReportByProduct> viewDetailedSalesReportByProduct(Date entry, Date exit, int category,
				Connection connection) throws GoAdminException {

			if (connection == null)
				throw new GoAdminException("Connection does not exist");
			if (category < 1 || category > 5)
				throw new GoAdminException("Invalid Category");
			if (entry == null || exit == null)
				throw new GoAdminException("Invalid Date");

			List<ViewDetailedSalesReportByProduct> viewDetailedSalesReportByProduct = new ArrayList<ViewDetailedSalesReportByProduct>();
			ViewDetailedSalesReportByProduct temp;

			Statement stmt = null;

			// to get month name use java.time framework
			// Month.of(monthNumber).name();

			int startYear = entry.getYear();
			int endYear = exit.getYear();
			int j = 0;
			// CONSIDERING THE REVENUE DATA FOR PREVIOUS MONTH,QUARTER,YEAR IS ZERO
			double prevM = 0.0, prevQ = 0.0, prevY;

			// DECLARING AMOUNT CHANGE, GROWTH , COLOR CODE ARRAY FOR MONTH TO MONTH BASIS
			double[] amtM = new double[12];
			double[] perChngM = new double[12];
			String[] codeM = new String[12];

			long[] arrQty = new long[12];
			double[] arrRev = new double[12];

			try {

				exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
				DbConnection.getInstance();
				connection = DbConnection.getConnection();

				stmt = connection.createStatement();

				ResultSet rs = stmt.executeQuery(QuerryMapper.SELECT_REVENUE_BY_MONTH);

				if (!rs.isBeforeFirst())
					throw new GoAdminException("Empty_Database");

				// loop from start year to end year
				for (int index = startYear; index <= endYear; index++) {
					// loop for going through order list
					while (rs.next()) {

						int d = rs.getInt(3);
						for (j = 0; j <= 11; j++) {
							// if(viewDetailedSalesReportByProduct.get(j).getMonth()==0 && j!=0)
							// viewDetailedSalesReportByProduct.get(j).setMonth(j);

							// finding out revenue data,quantity for all the 12 months
							if (j == d) {

								// arrQty[j]++;
								arrRev[j] += rs.getDouble("PRODUCT_PRICE");

							}

						}
					}
				}

				amtM[0] = arrRev[0] - prevM;
				// loop for going from January to December
				for (j = 0; j <= 11; j++) {

					temp = new ViewDetailedSalesReportByProduct();
					// initialising the amount change of current month and previous month

					if (j == 0) {

						perChngM[j] = amtM[j] / prevM;
					} else {
						amtM[j] = arrRev[j] - arrRev[j - 1];
						perChngM[j] = 100 * (arrRev[j] - arrRev[j - 1]) / arrRev[j - 1];

					}

					// checking the necessary condition for color code
					if (perChngM[j] >= 10.0)
						codeM[j] = "GREEN";
					else if (perChngM[j] >= 2.0 && perChngM[j] <= 10)
						codeM[j] = "AMBER";
					else
						codeM[j] = "RED";

					temp.setMonth(j);
					temp.setRevenue(arrRev[j]);
					temp.setAmountChange(amtM[j]);
					temp.setpercentageGrowth(perChngM[j]);
					temp.setCode(codeM[j]);

					viewDetailedSalesReportByProduct.add(temp);

				}
				prevM = arrRev[11];

			} catch (DatabaseException | SQLException | IOException e) {
				GoLog.logger.error(exceptionProps.getProperty(e.getMessage()));

			}
			return viewDetailedSalesReportByProduct;

		}

	@Override
	public List<Double> retrieveReturnReport(ReturnReportRequest request, Connection connection)
			throws GoAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
}
