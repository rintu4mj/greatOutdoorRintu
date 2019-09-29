package com.capgemini.go.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.capgemini.go.bean.ProductBean;
import com.capgemini.go.dto.ProductFilter;
import com.capgemini.go.dto.User;
import com.capgemini.go.exception.UserException;

public interface UserService {

	boolean userRegistration(User user) throws UserException;



	boolean userLogin(User user) throws UserException, Exception;
	
	boolean userLogout (User user) throws UserException, SQLException;


	List<ProductBean> getAllProducts(Connection connection) throws UserException;

	List<ProductBean> searchProduct(String productName,Connection connection) throws UserException;

	List<ProductBean> filterProduct(ProductFilter filterProduct,Connection connection) throws  UserException;

	// Functions for Retailer Inventory Manipulation
	public boolean updateProductReceived(String retailerId, String productUIN);
	public boolean updateProductSale(String retailerId, String productUIN);
	// end of Functions for Retailer Inventory Manipulation
}
