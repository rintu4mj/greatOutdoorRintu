package com.capgemini.go.dao;

import java.util.List;

import com.capgemini.go.dto.OrderCancel;
import com.capgemini.go.dto.OrderProductMap;
import com.capgemini.go.dto.OrderReturn;
import com.capgemini.go.dto.Product;
import com.capgemini.go.exception.SalesRepresentativeException;

public interface SalesRepresentativeDao {

	List<OrderProductMap> getOrderProductMap(String orderId) throws SalesRepresentativeException;

	boolean returnOrder(OrderReturn or) throws SalesRepresentativeException;

	boolean checkDispatchStatus(String orderId) throws SalesRepresentativeException;

	boolean updateOrderProductMap(String orderId) throws SalesRepresentativeException;

	String validateUser(String orderId) throws SalesRepresentativeException;

	int getCountProduct(String orderId,String productId) throws SalesRepresentativeException;

	boolean updateOrderProductMapByQty(String orderId, String productId, int qty) throws SalesRepresentativeException;

	boolean updateOrderReturn(String orderId,String productId,String userId,String reason,int qty) throws SalesRepresentativeException;
	
	String getOrderDetails(String orderId) throws Exception;

	boolean checkSalesRepId(String userId) throws Exception;

	Product getProductDetails(String orderId, String productId) throws Exception;

	boolean checkDispatchStatusForCancelling(String orderId) throws Exception;
	
	List<OrderProductMap> getOrderProductMapForCancelling(String orderId)  throws Exception;

	String cancelOrder(OrderCancel oc) throws Exception;

	int getProductQuantityOrdered(String orderId, String productId) throws Exception;

	String cancelProduct(String orderId, String productId, int productQty, int qty) throws Exception;

	String updateOrderCancelForProduct(String orderId, String productId, int productQtyOrdered, int qty, String userId)
			throws Exception;
	
	String getTargetSales(String userId) throws Exception;
	
	String getBonus(String userId) throws Exception;


}
