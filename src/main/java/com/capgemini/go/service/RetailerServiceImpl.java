
package com.capgemini.go.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

import com.capgemini.go.dao.RetailerDao;
import com.capgemini.go.dao.RetailerDaoImpl;
import com.capgemini.go.dto.Address;
import com.capgemini.go.dto.Cart;
import com.capgemini.go.dto.FrequentOrdered;
import com.capgemini.go.dto.Order;
import com.capgemini.go.dto.Product;
import com.capgemini.go.dto.Retailer;
import com.capgemini.go.dto.SalesRep;
import com.capgemini.go.dto.User;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.utility.GoLog;
import com.capgemini.go.utility.PropertiesLoader;

public class RetailerServiceImpl implements RetailerService {

	private static Properties exceptionProps = null;

	private static final String EXCEPTION_PROPERTIES_FILE = "exceptionStatment.properties";

	private RetailerDao retailer = new RetailerDaoImpl();
	private RetailerDao retailerdao = new RetailerDaoImpl();

	private Cart cartItem;
	private Product product;
	private boolean ITEM_ADDED = true;
	private boolean ITEM_NOT_ADDED = false;

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

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : validateRetailerID - Input Parameters : addressId - Return
	 * Type : boolean - Throws : - Author : CAPGEMINI - Creation Date : 21/9/2019 -
	 * Description : to validate existence of retailerID in database
	 ********************************************************************************************************/

	public boolean validateRetailerID(User user, Connection connection) throws UserException {
		boolean existsRetailerId = false;
		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			existsRetailerId = retailerdao.validateRetailerID(user, connection);
		} catch (UserException | IOException e) {
			throw new UserException("USER_INVALID" + e.getMessage());
		}
		return existsRetailerId;
	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : addAddress - Input Parameters :addressID, retailerId,
	 * buildingnum, city, state, country, zip, addressStatus - Return Type : boolean
	 * - Throws: - Author : CAPGEMINI - Creation Date : 21/9/2019 - Description : to
	 * add address into the database
	 ********************************************************************************************************/
	public boolean addAddress(Address address, Connection connection) throws RetailerException {
		boolean addressAddStatus = false;
		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);

			addressAddStatus = retailerdao.addAddress(address, connection);
		} catch (RetailerException | IOException e) {
			throw new RetailerException(exceptionProps.getProperty("ADDRESS_NOT_ADDED" + e.getMessage()));
		}

		return addressAddStatus;
	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : updateAddress - Input Parameters :addressID, retailerId,
	 * buildingnum, city, state, country, zip- Return Type : boolean - Throws: -
	 * Author : CAPGEMINI - Creation Date : 21/9/2019 - Description : to update
	 * address into the database
	 ********************************************************************************************************/
	public boolean updateAddress(Address address, Connection connection) throws RetailerException {
		boolean addressUpdateStatus = false;
		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);

			addressUpdateStatus = retailerdao.updateAddress(address, connection);
		} catch (RetailerException | IOException e) {
			throw new RetailerException(exceptionProps.getProperty("ADDRESS_NOT_UPDATED" + e.getMessage()));
		}

		return addressUpdateStatus;
	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : changeAddress - Input Parameters :addressID, retailerId,
	 * buildingnum, city, state, country, zip- Return Type : boolean - Throws: -
	 * Author : CAPGEMINI - Creation Date : 21/9/2019 - Description : to change
	 * address for particular order in database
	 ********************************************************************************************************/
	public boolean changeAddress(Address address, String orderId, Connection connection) throws RetailerException {
		boolean addressChangeStatus = false;
		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);

			addressChangeStatus = retailerdao.changeAddress(address, orderId, connection);
		} catch (RetailerException | IOException e) {
			throw new RetailerException(exceptionProps.getProperty("ORDER_ADDRESS_NOT_CHANGED" + e.getMessage()));
		}

		return addressChangeStatus;

	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : deleteAddress - Input Parameters :addressID, retailerId,
	 * buildingnum, city, state, country, zip- Return Type : boolean - Throws: -
	 * Author : CAPGEMINI - Creation Date : 21/9/2019 - Description : to delete
	 * address for particular retailerId in database
	 ********************************************************************************************************/
	public boolean deleteAddress(Address address, Connection connection) throws RetailerException {
		boolean addressDeleteStatus = false;
		try {
			exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);

			addressDeleteStatus = retailerdao.deleteAddress(address, connection);
		} catch (RetailerException | IOException e) {
			throw new RetailerException(exceptionProps.getProperty("ORDER_ADDRESS_NOT_DELETED" + e.getMessage()));
		}

		return addressDeleteStatus;
	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : addProductToFreqOrderDB - Input Parameters : Product List,
	 * Address List - Return Type : boolean - Throws : - Author : CAPGEMINI -
	 * Creation Date : 21/9/2019 - Description : To add products to Frequently order
	 * database
	 ********************************************************************************************************/
	public boolean addProductToFreqOrderDB(FrequentOrdered freqOrder, Connection connection) throws RetailerException {
		boolean addProdToFreqDbStatus = false;
		addProdToFreqDbStatus = retailer.addProductToFreqOrderDB(freqOrder, connection);
		return addProdToFreqDbStatus;

	}

	// ------------------------ GreatOutdoor Application
	// --------------------------
	/*******************************************************************************************************
	 * - Function Name : changeProductAddress - Input Parameters : Product product,
	 * Address address - Return Type : boolean - Throws : - Author : CAPGEMINI -
	 * Creation Date : 21/9/2019 - Description : To change the address linked with
	 * product
	 ********************************************************************************************************/
	public boolean changeProductAddress(String userId, Product product, Address address) {

		return false;
	}

	public void changeProductAddress(FrequentOrdered freqOrder, Connection connection) throws RetailerException {

		retailer.changeProductAddress(freqOrder, connection);

	}

	/*******************************************************************************************************
	 * - Function Name : addItemToCart - Input Parameters : productId, quantity,
	 * retailerId - Return Type : boolean - Throws : - Author : Azhar/Agnibha-
	 * Creation Date : 24/9/2019 - Description : To add product items to cart
	 * 
	 * @throws RetailerException
	 ********************************************************************************************************/
	public boolean addItemToCart(Cart cartItem, Connection connection) throws RetailerException {

		boolean addItemToCartStatus = false;
		addItemToCartStatus = retailer.addItemToCart(cartItem, connection);

		return addItemToCartStatus;
	}

	/*******************************************************************************************************
	 * - Function Name : placingOrder - Input Parameters :userId, addressId - Return
	 * Type : boolean - Throws : - Author : Azhar/Agnibha - Creation Date :
	 * 24/9/2019 - Description : To place order
	 * 
	 * @throws RetailerException
	 ********************************************************************************************************/
	public boolean placeOrder(Order order, Connection connection) throws RetailerException {
		boolean placeOrderStatus = false;
		placeOrderStatus = retailer.placeOrder(order, connection);
		return placeOrderStatus;
	}

}
