package com.capgemini.go.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.capgemini.go.dao.QuerryMapper;
import com.capgemini.go.exception.IdGenerationException;

public class GenerateOrderID {

	/*******************************************************************************************************
	 * - Function Name : generateOrderID - Input Parameters :connection
	 * Return Type :String Throws :- Author : Agnibha 
	 * Creation Date : 21/9/2019 - Description : to authenticate the user
	 * @throws IdGenerationException
	 ********************************************************************************************************/
	public static String generate(Connection connection) throws IdGenerationException{
		try {
		Statement Stmt = connection.createStatement();
		ResultSet resultSet = Stmt.executeQuery(QuerryMapper.ORDER_COUNT);
		resultSet.next();
		int count = resultSet.getInt(1);
		return Integer.toString(count);
		}
		catch(SQLException e)
		{
			throw new IdGenerationException(e.getMessage());
		}
	}
		
}
