package com.capgemini.go.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.capgemini.go.dto.User;
import com.capgemini.go.exception.DatabaseException;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.service.UserServiceImpl;
import com.capgemini.go.utility.DbConnection;

@TestMethodOrder(OrderAnnotation.class)
class UserLoginUnitTest {

	private static Connection connection = null;
	private static UserServiceImpl user = null;
	UserServiceImpl user1 = new UserServiceImpl();

	@BeforeAll
	static void setDatabase() {
		try {
			connection = DbConnection.getInstance().getConnection();
		} catch (DatabaseException e) {

			e.printStackTrace();
		}
	}

	@BeforeEach
	void getUser() {
		user = new UserServiceImpl();

	}

	@Test
	@Order(1)
	@DisplayName("User LogIn Successful")
	void testSuccessLogIn() throws Exception {

		User user = new User("", "RT03", "", "@manRaj1", 0L, 2, false);
		// DO THE LOG OUT first
		try {
			user1.userLogout(user);
		} catch (Exception e) {
		}
		// now attempt login
		assertTrue(user1.userLogin(user)); // Expected Successful Login
	}

	@Test
	@Order(2)
	@DisplayName("User LogIn Again")
	void testRepeatedLogIn() throws Exception {

		User user = new User("", "RT03", "", "@manRaj1", 0L, 2, false);

		assertThrows(UserException.class, () -> {
			user1.userLogin(user);
		});
	}

	@Test
	@Order(3)
	@DisplayName("User LogIn Without Registration")
	void testUnregisterdLogIn() throws Exception {
		User user = new User("", "MN02", "", "m@N123", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			user1.userLogin(user);
		});
	}

	@Test
	@Order(4)
	@DisplayName("User LogIn Without Password")
	void testNoPasswordLogIn() throws Exception {
		User user = new User("", "RT03", "", "", 0L, 2, false);
		// DO THE LOG OUT first
		try {
			user1.userLogout(user);
		} catch (Exception e) {
		}
		// now attempt login

		assertThrows(UserException.class, () -> {
			user1.userLogin(user);
		});
	}

	@Test
	@Order(5)
	@DisplayName("User LogIn With Empty User_Id and Correct Password")
	void testNoUserIdLogIn() throws Exception {
		User user = new User("", "", "", "@manRaj1", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			user1.userLogin(user);
		});
	}

	@Test
	@Order(6)
	@DisplayName("User LogIn With Empty User_Id and Empty Password")
	void testNoCredentialLogIn() throws Exception {
		User user = new User("", "", "", "", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			user1.userLogin(user);
		});
	}

	@Test
	@Order(7)
	@DisplayName("User LogIn With Case Changed Password")
	void testCaseSensitivePasswordLogIn() throws Exception {
		User user = new User("", "RT03", "", "@MANrAJ1", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			user1.userLogin(user);
		});
	}

	@Test
	@Order(8)
	@DisplayName("User LogIn With Case Changed User_Id")
	void testCaseSensitiveUserIdLogIn() throws Exception {
		User user = new User("", "rt03", "", "@manRaj1", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			user1.userLogin(user);
		});
	}

	@AfterEach
	void closeUser() {
		user = null;
	}

	@AfterAll
	static void closeDatabase() {
		try {
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		connection = null;
	}
}
