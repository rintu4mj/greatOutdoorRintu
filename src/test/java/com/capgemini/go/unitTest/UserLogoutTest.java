package com.capgemini.go.unitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
class UserLogoutTest {

	private static Connection connection = null;
	private static UserServiceImpl user = null;
	UserServiceImpl userOut = new UserServiceImpl();

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
	@DisplayName("Successful User LogOut")
	void testSuccessfulLogOut() throws Exception {
		User user = new User("", "RT03", "", "@manRaj1", 0L, 2, false);
		// DO THE LOG IN first
		try {
			userOut.userLogin(user);
		} catch (Exception e) {
		}
		// now attempt logout

		assertTrue(userOut.userLogout(user));
	}

	@Test
	@Order(2)
	@DisplayName("Invalid User_ID LogOut")
	void testInvalidLogOut() throws Exception {
		User user = new User("", "ml1", "", "", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			userOut.userLogout(user);
		});
	}

	@Test
	@Order(3)
	@DisplayName("No User_ID LogOut")
	void testNoLogOut() throws Exception {
		User user = new User("", "", "", "", 0L, 2, false);
		assertThrows(UserException.class, () -> {
			userOut.userLogout(user);
		});
	}

	@Test
	@Order(4)
	@DisplayName("User LogOut Again")
	void testRepeatedLogOut() throws Exception {

		User user = new User("", "RT03", "", "", 0L, 2, false);

		assertThrows(UserException.class, () -> {
			userOut.userLogout(user);
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
