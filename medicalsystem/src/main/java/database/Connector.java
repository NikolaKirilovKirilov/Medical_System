package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	
	private static final String connectionUrl =
		    "jdbc:sqlserver://localhost:1433;databaseName=Medical Assistance System;encrypt=false;user=sa;password=password";

	private static final String URL = "jdbc:sqlserver://DESKTOP-UJ6DMLF;Database=Medical Assistance System;IntegratedSecurity=true";
	private static final String USER = "Nikola";
	private static final String PASSWORD = "";


	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connectionUrl);
	}
	
	public static void main(String[] args) {
    try {
    	
    	try (Connection connection = DriverManager.getConnection(connectionUrl)) {
    		System.out.println("Connection Established!");
    	}
    	
    	} catch (SQLException e) {
    			System.out.println("Error connction to the database");
    			e.printStackTrace();
    		}
	}
    
	/*
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */
    
}