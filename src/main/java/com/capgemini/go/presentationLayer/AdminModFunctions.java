package com.capgemini.go.presentationLayer;
import java.util.*;

import com.capgemini.go.dto.Retailer;
import com.capgemini.go.dto.SalesRep;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.exception.SalesRepresentativeException;
import com.capgemini.go.service.GoAdminService;
import com.capgemini.go.service.GoAdminServiceImpl;
public class AdminModFunctions {

	public static void adminFunctions(Scanner scanner) throws SalesRepresentativeException, RetailerException {
		
		int val = 0;
		
		try {
			System.out.println("Enter Choice: 1. Modify Bonus 2. Modify Target 3.Modify Discount");
			val=scanner.nextInt();
		} catch (Exception e) {
			
			System.out.println("INVALID INPUT FORMAT");
		}
	

		switch (val) {
		
		case 1: 
			
			System.out.println("Enter Sales rep ID");
			String id=scanner.next();
			SalesRep sr = new SalesRep(id);
			GoAdminService gas = new GoAdminServiceImpl();
			System.out.println("Enter the bonus");
			double bonus = scanner.nextDouble();
			gas.setBonus(sr, bonus);
			bonus = gas.getBonus(sr);
			System.out.println(bonus);
			break;
		

		case 2: 
			System.out.println("Enter Sales rep ID");
			String id2=scanner.next();
			SalesRep sr2 = new SalesRep(id2);
			GoAdminService gas2 = new GoAdminServiceImpl();
			System.out.println("Enter the Target");
			double target = scanner.nextDouble();
			gas2.setTarget(sr2, target);
			target = gas2.getTarget(sr2);
			System.out.println(target);
			break;
		

		case 3: 
			
			System.out.println("Enter Retailer ID");
			String id3=scanner.next();
			Retailer ret = new Retailer(id3);
			System.out.println("Enter the Discount");
			double discount = scanner.nextDouble();
			GoAdminService gas3 = new GoAdminServiceImpl();			
			gas3.setDiscount(ret, discount);
			discount = gas3.getDiscount(ret);
			System.out.println(discount);
			break;
			
		default:
			System.out.println("INVALID CHOICE");

		}
	}

}
