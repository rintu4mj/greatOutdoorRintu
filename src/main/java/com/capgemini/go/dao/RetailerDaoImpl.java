
package com.capgemini.go.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import com.capgemini.go.dto.Address;
import com.capgemini.go.dto.Cart;
import com.capgemini.go.dto.FrequentOrdered;
import com.capgemini.go.dto.Order;
import com.capgemini.go.dto.Product;
import com.capgemini.go.dto.Retailer;
import com.capgemini.go.dto.RetailerInventory;
import com.capgemini.go.dto.SalesRep;
import com.capgemini.go.dto.User;
import com.capgemini.go.dao.QuerryMapper;
import com.capgemini.go.exception.DatabaseException;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.exception.ProductMasterException;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.utility.DbConnection;
import com.capgemini.go.utility.GoLog;
import com.capgemini.go.utility.PropertiesLoader;

public class RetailerDaoImpl implements RetailerDao {

	private static Properties exceptionProps = null;
	private static Properties goProps = null;
	private static final String EXCEPTION_PROPERTIES_FILE = "exceptionStatement.properties";
	private static final String GO_PROPERTIES_FILE = "goUtility.properties";
	Connection connection = null;

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : returnOrder - Input Parameters : userID, reason, orderID,
	 * Date - Return Type : boolean - Throws : - Author : CAPGEMINI - Creation Date
	 * : 21/9/2019 - Description : to return the order received by the retailer
	 ********************************************************************************************************/

	public boolean returnOrder(String userId, String reason, String orderId, Date date) {

		return false;
	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : returnProductByRetailer - Input Parameters : userID,
	 * orderID, Accepted Product List - Return Type : Rejected Product List- Throws
	 * : - Author : CAPGEMINI - Creation Date : 21/9/2019 - Description : to return
	 * products received by the retailer
	 ********************************************************************************************************/

	public List<Product> returnProductByRetailer(String userId, String orderId, List<Product> acceptedProducts) {

		return null;
	}

	public boolean validateRetailerID(User user, Connection connection) throws UserException {
		boolean existsRetailerId = false;
		String retailerID = user.getUserId();
		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			goProps = PropertiesLoader.loadProperties(GO_PROPERTIES_FILE);

			PreparedStatement statement = connection.prepareStatement(QuerryMapper.USER_EXISTS);
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				if (retailerID.equals(resultset.getString(2))) {
					existsRetailerId = true;
					break;
				}
			}
		} catch (IOException | SQLException e) {
			GoLog.logger.error(exceptionProps.getProperty(EXCEPTION_PROPERTIES_FILE));
			throw new UserException("....." + e.getMessage());
		}
		return existsRetailerId;
	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : addAddress - Input Parameters : address - Return Type :
	 * boolean - Throws : - Author : CAPGEMINI - Creation Date : 21/9/2019 -
	 * Description : to add address to the database
	 ********************************************************************************************************/
	public boolean addAddress(Address address, Connection connection) throws RetailerException {
		boolean addAddressState = address.isAddressStatus();
		String addressID = address.getAddressId();
		String retailerID = address.getRetailerId();
		String city = address.getCity();
		String state = address.getState();
		String zip = address.getZip();
		String buildingNum = address.getBuildingNo();
		String country = address.getCountry();

		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			goProps = PropertiesLoader.loadProperties(GO_PROPERTIES_FILE);

			PreparedStatement statement = connection.prepareStatement(QuerryMapper.INSERT_NEW_ADDRESS_IN_ADDRESSDB);
			statement.setString(1, addressID);
			statement.setString(2, retailerID);
			statement.setString(3, city);
			statement.setString(4, state);
			statement.setString(5, zip);
			statement.setString(6, buildingNum);
			statement.setString(7, country);
			statement.setBoolean(8, addAddressState);
			int row = statement.executeUpdate();
			if (row == 1)
				return true;
		} catch (IOException | SQLException e)// SQLException
		{
			GoLog.logger.error(exceptionProps.getProperty(EXCEPTION_PROPERTIES_FILE));
			throw new RetailerException("....." + e.getMessage());
		}

		return addAddressState;
	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : updateAddress - Input Parameters : address - Return Type :
	 * boolean - Throws : - Author : CAPGEMINI - Creation Date : 21/9/2019 -
	 * Description : to update address to the database
	 * 
	 * @throws RetailerException
	 ********************************************************************************************************/
	public boolean updateAddress(Address address, Connection connection) throws RetailerException {
		boolean addAddressState = false;
		String addressID = address.getAddressId();
		String retailerID = address.getRetailerId();
		String city = address.getCity();
		String state = address.getState();
		String zip = address.getZip();
		String buildingNum = address.getBuildingNo();
		String country = address.getCountry();

		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			goProps = PropertiesLoader.loadProperties(GO_PROPERTIES_FILE);

			PreparedStatement statement = connection
					.prepareStatement(QuerryMapper.CHECK_USERID_AND_ADDRESSID_IN_ADDRESSDB);

			statement.setString(1, addressID);
			statement.setString(2, retailerID);
			ResultSet rs = statement.executeQuery();
			if (rs.next() == true) {

				PreparedStatement statement2 = connection.prepareStatement(QuerryMapper.UPDATE_ADDRESS_IN_ADDRESSDB);
				statement2.setString(9, addressID);
				if (rs.getString(1).equals(addressID) && rs.getString(2).equals(retailerID)) {

					statement2.setString(1, addressID);
					statement2.setString(2, retailerID);
					statement2.setString(3, city);
					statement2.setString(4, state);
					statement2.setString(5, zip);
					statement2.setString(6, buildingNum);
					statement2.setString(7, country);
					statement2.setBoolean(8, addAddressState);
				}

				int row = 0;
				row = statement2.executeUpdate();

				if (row == 1) {
					return true;
				} else {
					System.out.println("cannot update address");
				}

			}

		} catch (IOException | SQLException e)// SQLException
		{
			GoLog.logger.error(exceptionProps.getProperty(EXCEPTION_PROPERTIES_FILE));
			throw new RetailerException("....." + e.getMessage());
		}

		return addAddressState;
	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : changeAddress - Input Parameters : address and orderId -
	 * Return Type : boolean - Throws : - Author : CAPGEMINI - Creation Date :
	 * 21/9/2019 - Description : to change address of a particular orderId in the
	 * database
	 * 
	 * @throws RetailerException
	 ********************************************************************************************************/
	public boolean changeAddress(Address address, String orderId, Connection connection) throws RetailerException {

		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			goProps = PropertiesLoader.loadProperties(GO_PROPERTIES_FILE);
			String addressID = address.getAddressId();
			String retailerID = address.getRetailerId();
			String city = address.getCity();
			String state = address.getState();
			String zip = address.getZip();
			String buildingNum = address.getBuildingNo();
			String country = address.getCountry();
			PreparedStatement statement = connection.prepareStatement(QuerryMapper.CHECK_ORDERID_IN_ORDERDB);

			statement.setString(1, addressID);
			statement.setString(2, orderId);
			ResultSet rs = statement.executeQuery();
			if (rs.next() == true) {
				PreparedStatement statement2 = connection.prepareStatement(QuerryMapper.CHANGE_ORDER_ADDRESS);
				statement2.setString(9, addressID);
				if (rs.getString(1).equals(addressID) && rs.getString(2).equals(retailerID)) {

					statement2.setString(1, addressID);
					statement2.setString(2, retailerID);
					statement2.setString(3, city);
					statement2.setString(4, state);
					statement2.setString(5, zip);
					statement2.setString(6, buildingNum);
					statement2.setString(7, country);

				}

				int row = 0;
				row = statement2.executeUpdate();

				if (row == 1)
					return true;
				else {
					System.out.println("cannot change order address");
				}
			}

		}

		catch (IOException | SQLException e)// SQLException
		{
			GoLog.logger.error(exceptionProps.getProperty(EXCEPTION_PROPERTIES_FILE));
			throw new RetailerException("....." + e.getMessage());
		}

		return false;
	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : deleteAddress - Input Parameters : address - Return Type :
	 * boolean - Throws : - Author : CAPGEMINI - Creation Date : 21/9/2019 -
	 * Description : to delete address of a particular order in the database
	 * 
	 * @throws RetailerException
	 ********************************************************************************************************/

	public boolean deleteAddress(Address address, Connection connection) throws RetailerException {
		String addressID = address.getAddressId();

		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			goProps = PropertiesLoader.loadProperties(GO_PROPERTIES_FILE);

			PreparedStatement statement = connection.prepareStatement(QuerryMapper.DELETE_ADDRESS_IN_ADDRESSDB);
			statement.setString(1, addressID);
			int row = 0;
			row = statement.executeUpdate();

			if (row == 1)
				return true;
		}

		catch (IOException | SQLException e)// SQLException
		{
			GoLog.logger.error(exceptionProps.getProperty(EXCEPTION_PROPERTIES_FILE));
			throw new RetailerException("....." + e.getMessage());
		}

		return false;
	}

	@Override

	/*******************************************************************************************************
	 * - Function Name : addProductToFreqOrderDB - Input Parameters : Product List,
	 * Address List - Return Type : boolean - Throws : - Author : CAPGEMINI -
	 * Creation Date : 21/9/2019 - Description : To add products to Frequently order
	 * database
	 ********************************************************************************************************/
	public boolean addProductToFreqOrderDB(FrequentOrdered freqOrder, Connection connection) throws RetailerException {

		boolean addProductToFreqOrderDB = false;
		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			String userID = freqOrder.getRetailerID();
			String productID = freqOrder.getProductID();
			/**
			 * checking if user exist
			 */
			PreparedStatement state = connection.prepareStatement(QuerryMapper.USER_EXISTS);
			ResultSet rs = state.executeQuery();
			int flag = 0;
			while (rs.next()) {

				if (userID.equals(rs.getString(2))) {
					flag = 1;
					/**
					 * Adding Product to Frequently order list
					 */

					PreparedStatement Statement = connection.prepareStatement(QuerryMapper.ADD_FREQ_PRODUCTS);
					Statement.setString(1, userID);
					Statement.setString(2, productID);
					PreparedStatement Stmt = connection.prepareStatement(QuerryMapper.SUGGEST_FREQ_PRODUCTS);
					Stmt.setString(1, userID);
					ResultSet res = Stmt.executeQuery();
					while (res.next()) {

						if (res.getInt("count(*)") > 3) {
							Statement.executeUpdate();

						}
					}

				}
			}
			if (flag == 0) {
				throw new UserException("User not found");
			}
			addProductToFreqOrderDB = true;
		}

		catch (SQLException | IOException | UserException e) {

			System.out.println(exceptionProps.getProperty("whishlist_error"));
			throw new RetailerException(exceptionProps.getProperty("whishlist_error"));
		}
		return addProductToFreqOrderDB;
	}

	// Azhar Functions
	/*******************************************************************************************************
	 * - Function Name : placingOrder - Input Parameters : cart, retailerID, Date
	 * date, address Return Type :boolean Throws : - Author : Agnibha , Azhar -
	 * Creation Date : 21/9/2019 - Description : to place order for items in the
	 * cart
	 ********************************************************************************************************/
	@Override
	public boolean placeOrder(Order order, Connection connection) throws RetailerException {
		boolean checkOutStatus = false;
		boolean isCartEmpty = true;
		String retailerID = order.getUserId();
		String addressID = order.getAddressId();
		String orderID = order.getOrderId();
		Date ordInitTime = order.getOrderInitiationTime();
		PreparedStatement prestmt = null;

		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			prestmt = connection.prepareStatement(QuerryMapper.GET_CART_ITEM);
			prestmt.setString(1, retailerID);
			ResultSet resultset = prestmt.executeQuery();
			PreparedStatement initOrd = connection.prepareStatement(QuerryMapper.INITIATE_ORDER);
			initOrd.setString(1, orderID);
			initOrd.setString(2, retailerID);
			initOrd.setString(3, addressID);
			initOrd.setDate(4, ordInitTime);
			initOrd.executeUpdate();
			while (resultset.next()) {
				isCartEmpty = false;
				String prodId = resultset.getString(1);
				int qty = resultset.getInt(2);
				prestmt = connection.prepareStatement(QuerryMapper.GET_PRODUCT_DETAILS);
				prestmt.setString(1, prodId);
				prestmt.setInt(2, qty);
				ResultSet rset = prestmt.executeQuery();
				boolean isProductAvailable = false;
				while (rset.next()) {
					isProductAvailable = true;
					PreparedStatement updtProdStr = connection.prepareStatement(QuerryMapper.UPDATE_PRODUCT_STORE);
					updtProdStr.setString(1, rset.getString(1));
					updtProdStr.executeUpdate();
					PreparedStatement updtOrdProdMap = connection
							.prepareStatement(QuerryMapper.UPDATE_ORDER_PRODUCT_MAP_ENTRY);
					updtOrdProdMap.setString(1, orderID);
					updtOrdProdMap.setString(2, prodId);
					updtOrdProdMap.setString(3, rset.getString(1));
					updtOrdProdMap.executeUpdate();
				}
				if (isProductAvailable == false) {
					GoLog.logger.error(exceptionProps.getProperty("prod_not_available"));
					throw new RetailerException(exceptionProps.getProperty("prod_not_available"));
				}
				PreparedStatement updtProdInv = connection.prepareStatement(QuerryMapper.UPDATE_PRODUCT_INVENTORY);
				updtProdInv.setInt(1, qty);
				updtProdInv.setString(2, prodId);
				updtProdInv.executeUpdate();
			}
			if (isCartEmpty == true) {
				PreparedStatement delOrd = connection.prepareStatement(QuerryMapper.DELETE_ORDER);
				delOrd.setString(1, orderID);
				GoLog.logger.error(exceptionProps.getProperty("cart_empty"));
				throw new RetailerException(exceptionProps.getProperty("cart_empty"));
			}
			PreparedStatement delcart = connection.prepareStatement(QuerryMapper.DELETE_CART);
			delcart.setString(1, retailerID);
			delcart.executeUpdate();
			checkOutStatus = true;

		} catch (RetailerException | SQLException | IOException e) {

			GoLog.logger.error(e.getMessage());
			throw new RetailerException(e.getMessage());
		}

		return checkOutStatus;
	}

	/*******************************************************************************************************
	 * Function Name : addItemToCart Input Parameters : Product Return Type :
	 * boolean Throws : Author : Agnibha, Azhar Creation Date : 27/9/2019
	 * Description : to add item to a cart
	 ********************************************************************************************************/
	@Override
	public boolean addItemToCart(Cart cartItem, Connection connection) throws RetailerException {
		boolean itemAddedToCart = false;
		String retailerId = cartItem.getRetailerId();
		String productId = cartItem.getProductId();
		int quantity = cartItem.getQuantity();

		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			PreparedStatement isProdPres = connection.prepareStatement(QuerryMapper.GET_PROD_PRESENT_STATUS);
			isProdPres.setString(1, productId);
			isProdPres.setString(2, retailerId);
			ResultSet rset = isProdPres.executeQuery();
			rset.next();
			if (rset.getInt(1) == 1) {
				PreparedStatement cartItemQty = connection.prepareStatement(QuerryMapper.CART_ITEM_QTY);
				cartItemQty.setString(1, productId);
				cartItemQty.setString(2, retailerId);
				ResultSet resultSet = cartItemQty.executeQuery();
				resultSet.next();
				int beforeQty = resultSet.getInt(1);
				quantity = quantity + beforeQty;
				PreparedStatement getProdCount = connection.prepareStatement(QuerryMapper.GET_PROD_QTY);
				getProdCount.setString(1, productId);
				resultSet = getProdCount.executeQuery();
				resultSet.next();
				int initQty = resultSet.getInt(1);
				if (initQty > quantity) {
					PreparedStatement addCartItem = connection.prepareStatement(QuerryMapper.INCREASE_CART_ITEM);
					addCartItem.setString(2, retailerId);
					addCartItem.setString(3, productId);
					addCartItem.setInt(1, quantity);
					addCartItem.executeUpdate();
					itemAddedToCart = true;
				} else {
					GoLog.logger.error(exceptionProps.getProperty("prod_not_available"));
					throw new RetailerException(exceptionProps.getProperty("prod_not_available"));
				}
			} else {
				PreparedStatement getProdCount = connection.prepareStatement(QuerryMapper.GET_PROD_QTY);
				getProdCount.setString(1, productId);
				ResultSet resultSet = getProdCount.executeQuery();
				resultSet.next();
				int initQty = resultSet.getInt(1);
				if (initQty > quantity) {
					PreparedStatement addCartItem = connection.prepareStatement(QuerryMapper.ADD_ITEM_TO_CART);
					addCartItem.setString(1, retailerId);
					addCartItem.setString(2, productId);
					addCartItem.setInt(3, quantity);
					addCartItem.executeUpdate();
					itemAddedToCart = true;
				} else {
					GoLog.logger.error(exceptionProps.getProperty("prod_not_available"));
					throw new RetailerException(exceptionProps.getProperty("prod_not_available"));
				}
			}

		} catch (RetailerException | IOException | SQLException e) {

			throw new RetailerException(e.getMessage());

		}

		return itemAddedToCart;
	}
	// end of Azhar Functions

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : changeProductAddress - Input Parameters : userID, Product,
	 * Address - Return Type : boolean - Throws : - Author : CAPGEMINI - Creation
	 * Date : 21/9/2019 - Description : To change the linked shipping address of
	 * product
	 * 
	 * @throws RetailerException
	 ********************************************************************************************************/
	public void changeProductAddress(FrequentOrdered freqOrder, Connection connection) throws RetailerException {

		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			String userID = freqOrder.getRetailerID();
			String productID = freqOrder.getProductID();
			String addressID = freqOrder.getAddressID();

			PreparedStatement preparedStatement = connection.prepareStatement(QuerryMapper.CHANGE_PRODUCT_ADDRESS);

			preparedStatement.setString(1, addressID);

			preparedStatement.setString(2, userID);

			preparedStatement.setString(3, productID);

			preparedStatement.executeUpdate();

		} catch (SQLException | IOException e) {

			GoLog.logger.error(e.getMessage());
			throw new RetailerException(e.getMessage());

		}

	}

	// Functions for Retailer Inventory Manipulation
	@Override
	public boolean updateProductReceiveTimeStamp(RetailerInventory queryArguments) {
		int querySuccess = -1;
		try {
			@SuppressWarnings("static-access")
			Connection connection = DbConnection.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement(QuerryMapper.UPDATE_PRODUCT_RECEIVE_TIMESTAMP_BY_RETAILER_ID_AND_PRODUCT_UIN);
			// filling query statement fields
			java.sql.Date c = new java.sql.Date(queryArguments.getProductRecieveTime().getTimeInMillis());
			stmt.setString(1, c.toString());
			stmt.setString(2, queryArguments.getRetailerUserId());
			stmt.setString(3, queryArguments.getProductUIN());
			// end of filling query statement fields
			querySuccess = stmt.executeUpdate();
		} catch (SQLException | DatabaseException e) {
			GoLog.logger.error(e.getMessage());
		}
		if (querySuccess > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateProductSaleTimeStamp(RetailerInventory queryArguments) {
		int querySuccess = -1;
		try {
			@SuppressWarnings("static-access")
			Connection connection = DbConnection.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement(QuerryMapper.UPDATE_PRODUCT_SALE_TIMESTAMP_BY_RETAILER_ID_AND_PRODUCT_UIN);
			// filling query statement fields
			java.sql.Date c = new java.sql.Date(queryArguments.getProductShelfTimeOut().getTimeInMillis());
			stmt.setString(1, c.toString());
			stmt.setString(2, queryArguments.getRetailerUserId());
			stmt.setString(3, queryArguments.getProductUIN());
			// end of filling query statement fields
			querySuccess = stmt.executeUpdate();
		} catch (SQLException | DatabaseException e) {
			GoLog.logger.error(e.getMessage());
		}
		if (querySuccess > 0) {
			return true;
		} else {
			return false;
		}
	}
	// end of Functions for Retailer Inventory Manipulation
}
