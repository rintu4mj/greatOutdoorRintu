package com.capgemini.go.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.capgemini.go.bean.ProductBean;
import com.capgemini.go.dao.ProductMasterDao;
import com.capgemini.go.dao.UserDao;
import com.capgemini.go.dto.ProductFilter;
import com.capgemini.go.dto.User;
import com.capgemini.go.exception.DatabaseException;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.service.UserService;
import com.capgemini.go.service.UserServiceImpl;
import com.capgemini.go.utility.DbConnection;

class TestProductView {
	
	private static Connection connection = null;
	private static UserService user = null;
	@BeforeAll
	static void setDatabase()
	{
		try {
			connection = DbConnection.getInstance().getConnection();
		} catch (DatabaseException e) {
			
			e.printStackTrace();
		}
	}
	
	@BeforeEach
	void getUser()
	{
		user = new UserServiceImpl();
	}
	
	@AfterEach
	void closeUser()
	{
		user = null;
	}
	
	
	@AfterAll
	static void closeDatabase()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		connection = null;
	}
	
	@Test
	@DisplayName("Connection is null for get All products")
	void getAllProductsFailure() {
		Connection conn = null;
		assertThrows(NullPointerException.class, ()->{user.getAllProducts(conn);});
	}
	
	@Test
	@DisplayName("Succesfull Retrieval")
	void getAllProductsSuccess() throws UserException
	{
		assertEquals(ProductBean.class , user.getAllProducts(connection).get(0).getClass());
		
	}
	
	@Test
	@DisplayName("Connection is null for search products")
	void searchProductFailure()
	{
		Connection conn = null;
		assertThrows(NullPointerException.class,()-> {user.searchProduct("watch", conn);});
	}
	
	@Test
	@DisplayName("Invalid Product Name Searched")
	void searchProductFailure2() throws UserException
	{
		assertEquals(user.searchProduct("xxxx9999", connection).size(),0);
	}
	
	@Test
	@DisplayName("Valid Product Name serached with exact product name")
	void searchProductSuccess() throws UserException
	{
		assertEquals(user.searchProduct("Premium Golf ball", connection).size(),1);
	}
	
	@Test
	@DisplayName("Valid Product Name searched with half name and different cases")
	void searchProductSuccess2() throws UserException
	{
		assertEquals(user.searchProduct("waTCh", connection).size(),1);
	}
	
	@Test
	@DisplayName("Connection is Null for filtered Product")
	void filterProductFailure()
	{
		Connection conn = null;
		assertThrows(NullPointerException.class, ()->{user.filterProduct(null, conn);});
	}
	
	@Test
	@DisplayName("Filter Parameter Doesn't Matches any Product")
	void filterProductFailure2() throws UserException, IOException
	{
		ProductFilter filter = new ProductFilter("", "", 0, 1000, 1, "XXX999");
		assertEquals(user.filterProduct(filter, connection).size(), 0);
	}
	
	@Test
	@DisplayName("Price Range Mis Matches any Product")
	void filterProductFailure3() throws UserException, IOException
	{
		ProductFilter filter = new ProductFilter("", "", 10000, 100, 1, "");
		assertThrows(UserException.class,()->{user.filterProduct(filter, connection).size();});
	}
	
	
	@Test
	@DisplayName("Filter Parameter  Matches without Category")
	void filterProductSuccess() throws UserException, IOException
	{
		ProductFilter filter = new ProductFilter("", "Black", 0, 100000, 0, "");
		assertEquals(user.filterProduct(filter, connection).size(), 3);
	}

	@Test
	@DisplayName("Filter Parameter  Matches with  Category")
	void filterProductSuccess2() throws UserException, IOException
	{
		ProductFilter filter = new ProductFilter("", "Black", 0, 100000, 5, "Bose");
		assertEquals(user.filterProduct(filter, connection).size(), 1);
	}


}
