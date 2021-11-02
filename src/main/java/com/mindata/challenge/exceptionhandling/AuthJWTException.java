package com.mindata.challenge.exceptionhandling;

public class AuthJWTException extends RuntimeException {

	/**
	 * @author anepa
	 */
	private static final long serialVersionUID = 1L;

	public AuthJWTException(String string) {
		super(string);
	}

}
