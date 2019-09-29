package com.capgemini.go.service;

import com.capgemini.go.exception.SalesRepresentativeException;

public interface SalesRepresentativeService {

	boolean returnOrder(String orderId, String userId, String reason) throws SalesRepresentativeException;

	boolean returnProduct(String orderId, String userId, String productID, int qty, String reason)throws SalesRepresentativeException;

	String validateUser(String orderId) throws SalesRepresentativeException;
	
	String cancelOrder(String orderId, String userId) throws Exception;

	String cancelProduct(String orderId, String userId, String productId, int qty) throws Exception;

	String checkTargetSales(String userId) throws Exception;
	
	String checkBonus(String userId) throws Exception;


}
