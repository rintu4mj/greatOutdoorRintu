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

public class RetailerInventoryServiceTest {
	public RetailerInventoryServiceTest() {
		// TODO Auto-generated constructor stub
	}
	private static Connection connection = null;
	private static GoAdminService goAdmin = null;
	
	@SuppressWarnings("static-access")
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
	
//	@Test
//	@DisplayName("Null user and connection send")
//	void addProductMasterFail(){
//		assertThrows(NullPointerException.class,()->{goAdmin.addProductMaster(null, null);} );
//	}
	
	// Vikas and Sujit will write this
	
	
}
