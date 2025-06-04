package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Connector {

	private static final String connectionUrl =
			"jdbc:sqlserver://localhost:1433;databaseName=[Medical Assistance System];encrypt=false;user=sa;password=parola1010"
;

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connectionUrl);
	}
}
