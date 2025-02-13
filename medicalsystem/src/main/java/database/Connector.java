package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connector {

	
	private static final String connectionUrl =
		    "jdbc:sqlserver://localhost:1433;databaseName=Medical Assistance System;encrypt=false;user=sa;password=parola1010";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connectionUrl);
	}
	
	public static void main(String[] args)
	{
		try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            String query = "SELECT DB_NAME() AS DatabaseName";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    String databaseName = rs.getString("DatabaseName");
                    System.out.println("Connected to Database: " + databaseName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

	