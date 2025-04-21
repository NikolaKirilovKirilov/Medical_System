package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DatabaseAuthenticator {
    
    // Database connection parameters
	private static final String connectionUrl =
		    "jdbc:sqlserver://localhost:1433;databaseName=Medical Assistance System;encrypt=false;user=sa;password=parola1010";
    
    public boolean authenticateUser(JComboBox<String> userTypeComboBox, 
                                  JTextField codeField, 
                                  JTextField usernameField, 
                                  JTextField passwordField) {
        
        String userType = (String) userTypeComboBox.getSelectedItem();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Establish database connection
            conn = DriverManager.getConnection(connectionUrl);
            
            switch (userType) {
                case "Administrator":
                    String adminCode = codeField.getText().trim();
                    if (adminCode.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter admin code");
                        return false;
                    }
                    
                    String adminQuery = "SELECT * FROM Admin WHERE AdminCode = ?";
                    stmt = conn.prepareStatement(adminQuery);
                    stmt.setString(1, adminCode);
                    rs = stmt.executeQuery();
                    
                    return rs.next(); // Returns true if admin exists
                    
                case "Doctor":
                    String doctorCode = codeField.getText().trim();
                    String doctorPassword = passwordField.getText().trim();
                    
                    if (doctorCode.isEmpty() || doctorPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter both code and password");
                        return false;
                    }
                    
                    String doctorQuery = "SELECT * FROM Doctor WHERE code = ? AND password = ?";
                    stmt = conn.prepareStatement(doctorQuery);
                    stmt.setString(1, doctorCode);
                    stmt.setString(2, doctorPassword);
                    rs = stmt.executeQuery();
                    
                    return rs.next(); // Returns true if doctor exists with matching credentials
                    
                case "Patient":
                    String patientUsername = usernameField.getText().trim();
                    String patientPassword = passwordField.getText().trim();
                    
                    if (patientUsername.isEmpty() || patientPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter both username and password");
                        return false;
                    }
                    
                    String patientQuery = "SELECT * FROM Patient WHERE username = ? AND password = ?";
                    stmt = conn.prepareStatement(patientQuery);
                    stmt.setString(1, patientUsername);
                    stmt.setString(2, patientPassword);
                    rs = stmt.executeQuery();
                    
                    return rs.next(); // Returns true if patient exists with matching credentials
                    
                default:
                    JOptionPane.showMessageDialog(null, "Invalid user type selected");
                    return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return false;
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}