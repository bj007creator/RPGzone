package com.rpgzonewebrest.authExceptions;

public class InvalidTokenException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTokenException() {
		super("Invalid Token");
	}
}
