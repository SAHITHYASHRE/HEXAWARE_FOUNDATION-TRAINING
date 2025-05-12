package com.hexaware.exception;

import java.sql.SQLException;

public class DatabaseConnectionException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DatabaseConnectionException(String message, SQLException e) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
