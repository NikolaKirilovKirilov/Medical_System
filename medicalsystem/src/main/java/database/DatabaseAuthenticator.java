package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DatabaseAuthenticator {  
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
            conn = Connector.getConnection();
            
            switch (userType) {
                case "Администратор":
                    String adminCode = codeField.getText().trim();
                    if (adminCode.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Моля въведете администраторски код!");
                        return false;
                    }
                    
                    String adminQuery = "SELECT * FROM Admin WHERE AdminCode = ?";
                    stmt = conn.prepareStatement(adminQuery);
                    stmt.setString(1, adminCode);
                    rs = stmt.executeQuery();
                    
                    if (rs.next()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Грешни данни!");
                        return false;
                    }
                    
                case "Доктор":
                    String doctorCode = codeField.getText().trim();
                    String doctorPassword = passwordField.getText().trim();
                    
                    if (doctorCode.isEmpty() || doctorPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Моля въведете лекарски код и парола!");
                        return false;
                    }
                    
                    String doctorQuery = "SELECT * FROM Doctor WHERE DoctorCode = ? AND Password = ?";
                    stmt = conn.prepareStatement(doctorQuery);
                    stmt.setString(1, doctorCode);
                    stmt.setString(2, doctorPassword);
                    rs = stmt.executeQuery();
                    
                    if (rs.next()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Грешни данни!");
                        return false;
                    }
                    
                case "Пациент":
                    String patientUsername = usernameField.getText().trim();
                    String patientPassword = passwordField.getText().trim();
                    
                    if (patientUsername.isEmpty() || patientPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Моля въведете потребителско име и парола!");
                        return false;
                    }
                    
                    String patientQuery = "SELECT * FROM Patient WHERE PatientName = ? AND Password = ?";
                    stmt = conn.prepareStatement(patientQuery);
                    stmt.setString(1, patientUsername);
                    stmt.setString(2, patientPassword);
                    rs = stmt.executeQuery();
                    
                    if (rs.next()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Грешни данни!");
                        return false;
                    }
                    
                default:
                    JOptionPane.showMessageDialog(null, "Невалиден потребител!");
                    return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Грешка в базата данни: " + e.getMessage());
            return false;
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}