package com.capgemini.go.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.capgemini.go.dto.User;
import com.capgemini.go.dto.WrongProductNotification;
import com.capgemini.go.exception.DatabaseException;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.service.GoAdminService;
import com.capgemini.go.service.GoAdminServiceImpl;
import com.capgemini.go.service.ProductMasterServiceImpl;
import com.capgemini.go.utility.DbConnection;

class GoAdminProductMasterFunctionalityTest {

	private static Connection connection = null;
	private static GoAdminService goAdmin = null;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try {
			connection = DbConnection.getInstance().getConnection();
		} catch (DatabaseException e) {
			
			e.printStackTrace();
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		connection.close();
		connection = null;
		
	}

	@BeforeEach
	void setUp() throws Exception {
		goAdmin = new GoAdminServiceImpl();
		
	}

	@AfterEach
	void tearDown() throws Exception {
		goAdmin = null;
	}

	@Test
	@DisplayName("Null user and connection send")
	void addProductMasterFail()
	{
		assertThrows(NullPointerException.class,()->{goAdmin.addProductMaster(null, null);} );
	}
	
	@Test
	@DisplayName("Invalid Phone Number Entered")
	void addProductMasterFail2()
	{
		User newUser = new User("xxx", "xxx", "agni@gmail.com",  "xxx123", 80153, 2, false);
		assertThrows(GoAdminException.class, ()->{goAdmin.addProductMaster(newUser, connection);});
	}
	
	@Test
	@DisplayName("Invalid Email Entered")
	void addProductMasterFail3()
	{
		User newUser = new User("xxx", "xxx", "agnigmai.com",  "xxx123", 8015366666L, 2, false);
		assertThrows(GoAdminException.class, ()->{goAdmin.addProductMaster(newUser, connection);});
	}
	@Test
	@DisplayName("User Id Already Exist")
	void addProductMasterFail4()
	{
		User newUser = new User("xxx", "RT03", "agnigmai.com",  "xxx123", 8015366666L, 2, false);
		assertThrows(GoAdminException.class, ()->{goAdmin.addProductMaster(newUser, connection);});
	}
	
	@Test
	@DisplayName(" User Number Already Registered")
	void addProductMasterFail5()
	{
		User newUser = new User("xxx", "xxx", "xxx@gmail.com",  "xxx123", 8017354644L, 2, false);
		assertThrows(GoAdminException.class, ()->{goAdmin.addProductMaster(newUser, connection);});
	}
	
	@Test
	@DisplayName("User Mail Already Registered")
	void addProductMasterFail6()
	{
		User newUser = new User("xxx", "xxx", "aks123@gmail.com",  "xxx123", 0000000000L, 2, false);
		assertThrows(GoAdminException.class, ()->{goAdmin.addProductMaster(newUser, connection);});
	}
	
	@Test
	@DisplayName("Product Master Successfully registered")
	void addProductMasterSuccess() throws GoAdminException
	{
		User newUser = new User("xxx", "xxx", "xxx@gmai.com",  "xxx123", 8015366666L, 2, false);
		assertTrue(goAdmin.addProductMaster(newUser, connection));
	}
	
	@Test
	@DisplayName("Connection is null for retrieving Notification for Wrong Product Shipped")
	void getNotificationForWrongProductFail()
	{
		assertThrows(NullPointerException.class, ()->{goAdmin.getNotification(null);});
	}
	
	@Test
	@DisplayName("Return Notification Retrieved Successfully")
	void getNotificationForWrongProductSuccess() throws GoAdminException
	{
		assertEquals(WrongProductNotification.class, goAdmin.getNotification(connection).get(0).getClass());
	}
	
	@Test
	@DisplayName("Connection is null for getting the list of Product Master")
	void viewProductMastersFail()
	{
		assertThrows(NullPointerException.class, ()->{goAdmin.viewProductMaster(null);});
	}
	
	@Test
	@DisplayName("Getting Product Master List Successfuly")
	void viewProductMastersSuccess() throws GoAdminException
	{
		assertEquals(User.class, goAdmin.viewProductMaster(connection).get(0).getClass());
	}

}
