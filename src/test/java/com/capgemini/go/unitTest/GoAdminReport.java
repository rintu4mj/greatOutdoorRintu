package com.capgemini.go.unitTest;


import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.capgemini.go.dao.GoAdminDao;
import com.capgemini.go.dao.GoAdminDaoImpl;
import com.capgemini.go.exception.DatabaseException;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.utility.DbConnection;

class GoAdminReport {

	private static Connection connection = null;
	private static GoAdminDao goAdmin = null;
	static Date dentry;
	static Date dexit;
	@BeforeAll
	static void setDatabase() throws ParseException
	{
		try {
			dentry=new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019");
	        dexit=new SimpleDateFormat("dd/MM/yyyy").parse("30/12/2019");
			
			
			connection = DbConnection.getInstance().getConnection();
		} catch (DatabaseException e) {
			
			e.printStackTrace();
		}
	}
	
	@BeforeEach
	void getUser()
	{
		goAdmin = new GoAdminDaoImpl();
	}
	
	@AfterEach
	void closeUser()
	{
		goAdmin = null;
	}
	
	
	@AfterAll
	static void closeDatabase()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		connection=null;
	}

	@Test
	@DisplayName("Connection does not exist")
	void viewSalesRepDataFail1()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesRepData("SR01",null);});
	}
	@Test
	@DisplayName("User does not exist")
	void viewSalesRepDataFail2()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesRepData("invalid",connection);});
	}
	@Test
	@DisplayName("UserId is null")
	void viewSalesRepDataFail3()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesRepData(null,connection);});
	}
	
	
	
	@Test
	@DisplayName("Connection does not exist")
	void viewAllSalesRepDataFail()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewAllSalesRepData(null);});
	}
	
	
	@Test
	@DisplayName("Connection does not exist")
	void viewRetailerDataFail1()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewRetailerData("RT01", null);});
	}
	@Test
	@DisplayName("User does not exist")
	void viewRetailerDataFail2()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewRetailerData("invalid",connection);});
	}
	@Test
	@DisplayName("UserId is null")
	void viewRetailerDataFail3()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewRetailerData(null,connection);});
	}
	
	@Test
	@DisplayName("Connection does not exist")
	void viewAllRetailerDataFail()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewAllRetailerData(null);});
	}
	
	
	
	@Test
	@DisplayName("Connection does not exist")
	void viewSalesReportALLUserAndCategoryFail1()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportALLUserAndCategory(dentry, dexit, null);});
	}
	@Test
	@DisplayName("Date is null")
	void viewSalesReportALLUserAndCategoryFail2()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportALLUserAndCategory(null, null, connection);});
	}
	
	
	
	
	@Test
	@DisplayName("Connection does not exist")
	void viewSalesReportByCategory1()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByCategory(dentry, dexit, 5, null);});
	}
	@Test
	@DisplayName("Invalid Category")
	void viewSalesReportByCategory2()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByCategory(dentry, dexit, 6, connection);});
	}
	@Test
	@DisplayName("Dates are Invalid")
	void viewSalesReportByCategory3()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByCategory(null, null, 5, connection);});
	}
	
	
	@Test
	@DisplayName("Connection does not exist")
	void viewSalesReportByUser1()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByUser(dentry, dexit, "SR01", null);});
	}
	@Test
	@DisplayName("Invalid Category")
	void viewSalesReportByUser2()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByUser(dentry, dexit, "invalid", connection);});
	}
	@Test
	@DisplayName("Dates are Invalid")
	void viewSalesReportByUser3()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByUser(null, null, "invalid", connection);});
	}
	@Test
	@DisplayName("UserId is null")
	void viewSalesReportByUser4()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByUser(dentry, dexit, null, connection);});
	}
	
	
	
	
	
	@Test
	@DisplayName("Connection does not exist")
	void viewSalesReportByUserAndCategory1()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByUserAndCategory(dentry, dexit, "SR01", 5, null);});
	}
	@Test
	@DisplayName("Invalid Category")
	void viewSalesReportByUserAndCategory2()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByUserAndCategory(dentry, dexit, "SR01", 6, connection);});
	}
	@Test
	@DisplayName("Dates are Invalid")
	void viewSalesReportByUserAndCategory3()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByUserAndCategory(null, null, "SR01", 5, connection);});
	}
	@Test
	@DisplayName("UserId is null")
	void viewSalesReportByUserAndCategory4()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByUserAndCategory(dentry, dexit, null, 5, connection);});
	}
	@Test
	@DisplayName("UserID does not exist")
	void viewSalesReportByUserAndCategory5()
	{
		assertThrows(GoAdminException.class, ()->{goAdmin.viewSalesReportByUserAndCategory(dentry, dexit, "invalid", 5, connection);});
	}
	
	
	
	
	
	
	

}
