/**
 * 
 * Exception raised if Order not Found
 */
package com.capgemini.go.exception;

public class OrderNotFoundException extends Exception {

	private static final long serialVersionUID = -563781369114628754L;

	public OrderNotFoundException(String s) {
		super(s);
	}
}

