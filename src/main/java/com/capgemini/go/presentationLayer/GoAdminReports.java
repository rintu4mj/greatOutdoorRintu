package com.capgemini.go.presentationLayer;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import com.capgemini.go.dto.Retailer;
import com.capgemini.go.dto.SalesRep;
import com.capgemini.go.dto.ViewDetailedSalesReportByProduct;
import com.capgemini.go.dto.ViewSalesReportByUser;
import com.capgemini.go.exception.DatabaseException;
import com.capgemini.go.exception.GoAdminException;
//import com.capgemini.go.exception.NoConnectionException;
import com.capgemini.go.service.GoAdminService;
import com.capgemini.go.service.GoAdminServiceImpl;
import com.capgemini.go.utility.DbConnection;

public class GoAdminReports {

	static void reportInterfaces()  {

		Connection connection = null;
		try {
			DbConnection.getInstance();
			connection = DbConnection.getConnection();
		} catch (Exception e) {
			System.out.println("Database couldn't be connected");
		}

		GoAdminService goAdmin = new GoAdminServiceImpl();

		Scanner sc = new Scanner(System.in);
		try {
		System.out.println("------------------WELCOME TO GREAT OUTDOOR-------------------");
		System.out.println("CHOOSE FROM THE BELOW OPTION:");
		System.out.println("	1.)VIEW REPORTS OF SPECIFIC RETAILER				");
		System.out.println("	2.)VIEW REPORTS OF ALL RETAILER						");
		System.out.println("	3.)VIEW REPORTS OF SPECIFIC SALES REPRESENTATIVE	");
		System.out.println("	4.)VIEW REPORTS OF ALL SALES REPRESENTATIVE			");
		System.out.println("	5.)VIEW DETAILED REVENUE REPORTS OF PARTICULAR PRODUCT(ALL USER) WITHIN GIVEN DATES");
		System.out
				.println("	6.)VIEW DETAILED REVENUE REPORTS OF PARTICULAR PRODUCT(SPECIFIC USER) WITHIN GIVEN DATES");
		System.out.println("	7.)VIEW DETAILED REVENUE REPORTS OF SPECIFIC USER(ALL PRODUCTS) WITHIN GIVEN DATES");
		System.out.println("	8.)VIEW DETAILED REVENUE REPORTS OF ALL USER(ALL PRODUCTS) WITHIN GIVEN DATES");
		System.out
				.println("	9.)VIEW MONTH TO MONTH CHANGE REVENUE REPORTS OF PARTICULAR PRODUCT WITHIN GIVEN DATES");

		int choice = sc.nextInt();
		switch (choice) {
		case 1: {
			System.out.println("ENTER THE USER ID");
			String user = sc.next();
			// Object o=GoAdmin.viewRetailerData(user);
			Retailer report = goAdmin.viewRetailerData(user, connection);
			if (report != null) {
				System.out.printf("%-20s %-20s %n", "RETAILER ID", "DISCOUNT");
				goAdmin.viewRetailerData(user, connection).printData();
			}
			// System.out.printf("%-20s %-20s","RETAILER ID","DISCOUNT");
			// System.out.printf("%-20s
			// %-20.2f",GoAdmin.viewRetailerData(user).getUserId(),GoAdmin.viewRetailerData(user).getDiscount());
			break;

		}
		case 2: {
			int i = 0, n;
			List<Retailer> reports = goAdmin.viewAllRetailerData(connection);
			if (reports != null) {
				System.out.printf("%-20s %-20s %n", "RETAILER ID", "DISCOUNT");

				n = reports.size();
				// GoAdmin.viewAllRetailerData().toString();
				// System.out.println(n);
				while (i < n) {
					reports.get(i).printData();
					i++;
				}
			}

			break;
		}
		case 3: {
			System.out.println("ENTER THE USER ID");
			String user = sc.next();

			SalesRep reports = goAdmin.viewSalesRepData(user, connection);
			if (reports != null) {
				System.out.printf("%-25s %-25s %-25s %-25s%n", "USER ID", "TARGET SALES", "TARGET STATUS",
						"CURRENT SALES");
				goAdmin.viewSalesRepData(user, connection).printData();
			}
			// System.out.println(GoAdmin.viewSalesRepData(user));
			break;
		}
		case 4: {

			int i = 0, n;
			List<SalesRep> reports = goAdmin.viewAllSalesRepData(connection);
			if (reports != null) {
				System.out.printf("%-25s %-25s %-25s %-25s %n", "USER ID", "TARGET SALES", "TARGET STATUS",
						"CURRENT SALES");

				n = reports.size();
				while (i < n) {
					goAdmin.viewAllSalesRepData(connection).get(i).printData();
					i++;
				}
			}

			break;

		}
		case 5: {
			int i = 0;
			System.out.println("ENTER THE DATES in dd/MM/yyyy format");
			String dateEntry = sc.next();
			String dateExit = sc.next();
			Date dentry = new SimpleDateFormat("dd/MM/yyyy").parse(dateEntry);
			Date dexit = new SimpleDateFormat("dd/MM/yyyy").parse(dateExit);
			System.out.println("ENTER THE PRODUCT CATEGORY IN INTEGER");
			System.out.println("1.CAMPING");
			System.out.println("2.GOLF");
			System.out.println("3.MOUNTANEERING");
			System.out.println("4.OUTDOOR");
			System.out.println("5.PERSONAL");
			int category = sc.nextInt();
			List<ViewSalesReportByUser> reports = goAdmin.viewSalesReportByCategory(dentry, dexit, category,
					connection);

			if (reports != null) {

				int n = reports.size();
				System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s %n", "USER ID", "DATE", "ORDER ID", "PRODUCT ID",
						"PRODUCT CATEGORY", "PRODUCT PRICE");
				while (i < n) {
					reports.get(i).printData();
					i++;
				}
			}
			break;

		}
		case 6: {
			int i = 0;
			int n;
			System.out.println("ENTER THE USER ID");
			String user = sc.next();

			System.out.println("ENTER THE DATES in dd/MM/yyyy format");
			String dateEntry = sc.next();
			String dateExit = sc.next();
			Date dentry = new SimpleDateFormat("dd/MM/yyyy").parse(dateEntry);
			Date dexit = new SimpleDateFormat("dd/MM/yyyy").parse(dateExit);

			System.out.println("ENTER THE PRODUCT CATEGORY IN INTEGER");
			System.out.println("1.CAMPING");
			System.out.println("2.GOLF");
			System.out.println("3.MOUNTANEERING");
			System.out.println("4.OUTDOOR");
			System.out.println("5.PERSONAL");
			int category = sc.nextInt();
			List<ViewSalesReportByUser> reports = goAdmin.viewSalesReportByUserAndCategory(dentry, dexit, user,
					category, connection);

			if (reports != null) {
				n = reports.size();
				System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s %n", "USER ID", "DATE", "ORDER ID", "PRODUCT ID",
						"PRODUCT CATEGORY", "PRODUCT PRICE");
				while (i < n) {
					reports.get(i).printData();
					i++;
				}
			}
			break;

		}
		case 7: {
			int i = 0;
			int n;
			System.out.println("ENTER THE USER ID");
			String user = sc.next();

			System.out.println("ENTER THE DATES in dd/MM/yyyy format");
			String dateEntry = sc.next();
			String dateExit = sc.next();
			Date dentry = new SimpleDateFormat("dd/MM/yyyy").parse(dateEntry);
			Date dexit = new SimpleDateFormat("dd/MM/yyyy").parse(dateExit);

			List<ViewSalesReportByUser> reports = goAdmin.viewSalesReportByUser(dentry, dexit, user, connection);

			if (reports != null) {
				n = reports.size();

				System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s %n", "USER ID", "DATE", "ORDER ID", "PRODUCT ID",
						"PRODUCT CATEGORY", "PRODUCT PRICE");
				while (i < n) {
					reports.get(i).printData();
					i++;
				}
			}
			break;

		}
		case 8: {
			int i = 0, n;

			System.out.println("ENTER THE DATES in dd/MM/yyyy format");
			String dateEntry = sc.next();
			String dateExit = sc.next();
			Date dentry = new SimpleDateFormat("dd/MM/yyyy").parse(dateEntry);
			Date dexit = new SimpleDateFormat("dd/MM/yyyy").parse(dateExit);

			List<ViewSalesReportByUser> reports = goAdmin.viewSalesReportALLUserAndCategory(dentry, dexit, connection);

			if (reports != null) {
				n = reports.size();
				System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s %n", "USER ID", "DATE", "ORDER ID", "PRODUCT ID",
						"PRODUCT CATEGORY", "PRODUCT PRICE");
				while (i < n) {
					reports.get(i).printData();
					i++;
				}
			}
			break;

		}
		case 9: {

			int i = 0;
			System.out.println("ENTER THE DATES in dd/MM/yyyy format");
			String dateEntry = sc.next();
			String dateExit = sc.next();
			Date dentry = new SimpleDateFormat("dd/MM/yyyy").parse(dateEntry);
			Date dexit = new SimpleDateFormat("dd/MM/yyyy").parse(dateExit);

			System.out.println("ENTER THE PRODUCT CATEGORY IN INTEGER");
			System.out.println("1.CAMPING");
			System.out.println("2.GOLF");
			System.out.println("3.MOUNTANEERING");
			System.out.println("4.OUTDOOR");
			System.out.println("5.PERSONAL");

			int category = sc.nextInt();

			List<ViewDetailedSalesReportByProduct> reports = goAdmin.viewDetailedSalesReportByProduct(dentry, dexit,
					category, connection);
			if (reports != null) {
				System.out.printf("%-25s %-25s %-25s %-25s %-25s %n", "MONTH", "REVENUE", "AMOUNT CHANGE",
						"PERCENTAGE GROWTH", "COLOR CODE");
				// reports.get(0).printData();
				int n = reports.size();

				while (i < n) {
					reports.get(i).printData();
					i++;
				}
			}
			break;

		}
		default: {
			System.out.println("INVALID CHOICE");
			break;
		}
		}}
		catch(ParseException | GoAdminException e)
		{
			System.out.println(e.getMessage());
		}

	}
}
