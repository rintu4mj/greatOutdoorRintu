package com.capgemini.go.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.capgemini.go.dto.Address;
import com.capgemini.go.dto.Cart;
import com.capgemini.go.dto.FrequentOrdered;
import com.capgemini.go.dto.Order;
import com.capgemini.go.dto.Product;
import com.capgemini.go.dto.Retailer;
import com.capgemini.go.dto.RetailerInventory;
import com.capgemini.go.dto.SalesRep;
import com.capgemini.go.dto.User;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.exception.UserException;

public interface RetailerDao {

	boolean returnOrder(String userId, String reason, String orderId, Date date);

	List<Product> returnProductByRetailer(String userId, String orderId, List<Product> acceptedProducts);

	//

	boolean validateRetailerID(User user, Connection connection) throws UserException;

	boolean addAddress(Address address, Connection connection) throws RetailerException;

	boolean updateAddress(Address address, Connection connection) throws RetailerException;

	boolean changeAddress(Address address, String orderId, Connection connection) throws RetailerException;

	boolean deleteAddress(Address address, Connection connection) throws RetailerException;

	//
	boolean addProductToFreqOrderDB(FrequentOrdered freqOrder, Connection connection) throws RetailerException;

	void changeProductAddress(FrequentOrdered freqOrder, Connection connection) throws RetailerException;

	boolean addItemToCart(Cart cartItem, Connection connection) throws RetailerException;

	boolean placeOrder(Order order, Connection connection) throws RetailerException;

	// Functions for Retailer Inventory Manipulation
	boolean updateProductReceiveTimeStamp(RetailerInventory queryArguments);

	boolean updateProductSaleTimeStamp(RetailerInventory queryArguments);
	// end of Functions for Retailer Inventory Manipulation

}
