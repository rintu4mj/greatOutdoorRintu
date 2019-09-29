package com.capgemini.go.presentationLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.capgemini.go.dao.QuerryMapper;
import com.capgemini.go.dto.Address;
import com.capgemini.go.dto.Order;
import com.capgemini.go.dto.User;
import com.capgemini.go.exception.DatabaseException;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.service.RetailerService;
import com.capgemini.go.service.RetailerServiceImpl;
import com.capgemini.go.utility.DbConnection;

public class AddressUserInterface {
	public static Connection connection = null;

	@SuppressWarnings("static-access")
	static void addressUserInterface() throws DatabaseException, SQLException, RetailerException, UserException {
		Scanner scanner = new Scanner(System.in);
		RetailerService retailerservice = new RetailerServiceImpl();
		// RetailerDaoImpl rd = new RetailerDaoImpl();

		connection = DbConnection.getInstance().getConnection();
		System.out.println("Enter User id");
		String userId = scanner.next();
		User user = new User("null", userId, "null", "null", 0l, 0, true);
		boolean value = retailerservice.validateRetailerID(user, connection);
		if (value == true) {
			Address address = null;

			System.out.println("Welcom....." + userId);
			System.out.println(".......Enter Your Choices.......");
			System.out.println(".......Press 1 to add address.......");
			System.out.println(".......Press 2 to update address.......");
			System.out.println(".......Press 3 to change order address.......");
			System.out.println(".......Press 4 to delete address.......");
			int retailerchoice = scanner.nextInt();
			switch (retailerchoice)

			{
			case 1: {
				System.out.println("Welcome....." + userId + "/n <<<<proceed ahead to add address>>>>");

				System.out.println("Enter your buildingnum");
				String buildingnum2 = scanner.nextLine();
				System.out.println("Enter your city");
				String city2 = scanner.nextLine();
				System.out.println("Enter your state");
				String state2 = scanner.nextLine();
				System.out.println("Enter your country");
				String country2 = scanner.nextLine();
				System.out.println("Enter your zip");
				String zip2 = scanner.nextLine();

				PreparedStatement statement3 = connection.prepareStatement(QuerryMapper.COUNT_USERID_IN_ADDRESSDB);
				statement3.setString(1, userId);
				ResultSet rs3 = statement3.executeQuery();
				rs3.next();

				String addressId = (userId + "ADD" + "OO" + rs3.getInt(2));
				System.out.println("Your addressId is  " + addressId);
				address = new Address(addressId, userId, buildingnum2, city2, state2, country2, zip2, true);
				System.out.println(retailerservice.addAddress(address, connection));

			}

			case 2: {
				System.out.println("Welcome....." + userId + "/n <<<<proceed ahead to update address>>>>");
				System.out.println("Enter ADDRESS id");
				String addressId = scanner.next();
				System.out.println("Enter your buildingnum");
				String buildingnum2 = scanner.nextLine();
				System.out.println("Enter your city");
				String city2 = scanner.nextLine();
				System.out.println("Enter your state");
				String state2 = scanner.nextLine();
				System.out.println("Enter your country");
				String country2 = scanner.nextLine();
				System.out.println("Enter your zip");
				String zip2 = scanner.nextLine();
				address = new Address(addressId, userId, buildingnum2, city2, state2, country2, zip2, true);
				System.out.println(retailerservice.updateAddress(address, connection));
			}
			case 3: {
				System.out.println("Welcome....." + userId + "/n proceed ahead to change order address");
				System.out.println("Enter ADDRESS id");
				String addressId = scanner.next();
				System.out.println("Enter order id");
				String orderId = scanner.next();
				System.out.println(retailerservice.changeAddress(address, orderId, connection));
			}
			case 4: {
				System.out.println("Welcome....." + userId + "/n <<<<proceed ahead to delete address>>>>");

				System.out.println("Enter ADDRESS id");
				String addressId = scanner.next();
				address = new Address(addressId, userId, null, null, null, null, null, false);
				System.out.println(retailerservice.deleteAddress(address, connection));

			}
			default: {

			}
			}

		} else {

		}
		System.out.println("done with this");
		scanner.close();
		return;
	}
}
