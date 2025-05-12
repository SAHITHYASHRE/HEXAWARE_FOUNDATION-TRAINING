package com.hexaware.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbconnUtil {
	
//	public static Connection connection = null;
//	
//	public DbconnUtil() {
//		super();
//	}
	


	 private static String url = DbpropertyUtil.get("db.url");
	    private static String username = DbpropertyUtil.get("db.username");
	    private static String password = DbpropertyUtil.get("db.password");
	    
	    public static Connection connection;

	    private DbconnUtil() {
	        super();
	    }

	    public static Connection getConnection() throws SQLException {
	        //singleton design pattern
	        try {
	            if (connection == null || connection.isClosed()) {
	                connection = DriverManager.getConnection(url, username, password);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return connection;
	    }

	    public static void closeConnection(Connection connection) {
	        if (connection != null) {
	            try {
	                if (!connection.isClosed()) {
	                    connection.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
