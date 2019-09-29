package com.capgemini.go.unitTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
class UserRegistrationUnitTest {

	private static Connection connection = null;
	UserServiceImpl newUser = new UserServiceImpl();

	@BeforeAll
	static void setDatabase() {
		try {
			DbConnection.getInstance();
			connection = DbConnection.getConnection();
		} catch (DatabaseException e) {

			e.printStackTrace();
		}
	}

	@BeforeEach
	void getUser() {
		new UserServiceImpl();
	}

	@Test
	@Order(1)
	@DisplayName("User Registration Without USER_ID")
	void testNoUserIdRegistration() throws Exception {
		User user = new User("Mayank", "", "mayan@gmail.com", "@zacAnd1", 8090683860L, 2, false);
		assertThrows(UserException.class, () -> {
			newUser.userRegistration(user);
		});
	}

	@Test
	@Order(2)
	@DisplayName("User Registration Without USER_MAIL")
	void testNoUserMailRegistration() throws Exception {
		User user = new User("Mayank", "MA1", "", "@zacAnd1", 8090683860L, 2, false);
		assertThrows(UserException.class, () -> {
			newUser.userRegistration(user);
		});
	}

	@Test
	@Order(3)
	@DisplayName("User Registration Without USER_NUMBER")
	void testNoNumberRegistration() throws Exception {
		User user = new User("Mayank", "MA1", "mayan@gmail.com", "@manA123", -111335, 2, false);
		assertThrows(UserException.class, () -> {
			newUser.userRegistration(user);
		});
	}

	@Test
	@Order(4)
	@DisplayName("User Registration With Wrong USER_CATEGORY")
	void testWrongCategoryRegistration() throws Exception {
		User user = new User("Mayank", "MA1", "mayan@gmail.com", "@manA123", 7654821390L, 1, false);
		assertThrows(UserException.class, () -> {
			newUser.userRegistration(user);
		});
	}

	@Test
	@Order(5)
	@DisplayName("User Registration With Wrong USER_NUMBER")
	void testWrongNumberRegistration() throws Exception {
		User user = new User("Mayank", "MA1", "mayan@gmail.com", "@manA123", 789231, 2, false);
		assertThrows(UserException.class, () -> {
			newUser.userRegistration(user);
		});
	}

	@Test
	@Order(6)
	@DisplayName("User Registration With Existing USER_ID")
	void testExistingIdRegistration() throws Exception {
		User user = new User("Mayank", "RT03", "mayan@gmail.com", "@zacAnd1", 8090683860L, 2, false);
		assertThrows(UserException.class, () -> {
			newUser.userRegistration(user);
		});
	}

	@Test
	@Order(7)
	@DisplayName("Successful User Registration")
	void testSuccessfulRegistration() throws Exception {
		User user = new User("Mayank", "MA1", "mayan@gmail.com", "@zacAnd1", 8890683860L, 2, false);
		assertTrue(newUser.userRegistration(user));
	}

	@AfterEach
	void closeUser() {
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
