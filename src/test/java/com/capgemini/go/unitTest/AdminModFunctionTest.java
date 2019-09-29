package com.capgemini.go.unitTest;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.capgemini.go.dao.GoAdminDao;
import com.capgemini.go.dao.GoAdminDaoImpl;
import com.capgemini.go.dto.Retailer;
import com.capgemini.go.dto.SalesRep;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.exception.SalesRepresentativeException;

class AdminModFunctionTest {

	@Test
	void testGetBonus() {
		GoAdminDao ga= new GoAdminDaoImpl();
		SalesRep sr=new SalesRep("SR01");
		try {
			assertEquals(200.0, ga.getBonus(sr));
		} 
		
		catch (SalesRepresentativeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testGetTarget() {
		GoAdminDao ga = new GoAdminDaoImpl();
		SalesRep sr = new SalesRep("SR01");
		try {
			assertEquals(200.0, ga.getTarget(sr));
		}

		catch (SalesRepresentativeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testGetDiscount() {
		GoAdminDao ga = new GoAdminDaoImpl();
		Retailer ret = new Retailer("RT01");
		try {
			assertEquals(10.0, ga.getDiscount(ret));
		}

		catch (RetailerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}