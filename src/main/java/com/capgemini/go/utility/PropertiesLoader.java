package com.capgemini.go.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	private static Properties props = null;

	/*****************************************************************
	 * - Method Name:loadProperties() - Input Parameters : filename - Return Type
	 * :Properties object - Throws : IOException - Author : CAPGEMINI - Creation
	 * Date : 18/11/2016 - Description : Returns Properties object
	 *******************************************************************/

	public static Properties loadProperties(String file) throws IOException {

		if (props == null) {
			Properties newProps = new Properties();
			String fileName = "src/main/resources/" + file;

			InputStream inputStream = new FileInputStream(fileName);
			newProps.load(inputStream);

			inputStream.close();

			return newProps;
		} else {
			return props;
		}
	}

}
