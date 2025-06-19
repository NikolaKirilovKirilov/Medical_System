package database;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;

import com.example.model.*;

public class QueryExecutor {
	
	static ArrayList<User> entries = new ArrayList<User>();
	static Administrator admin;
	static Doctor doc;
	static Patient patient;
	
	
	public QueryExecutor()
	{
		
	}
	
	//-----------------------------------------------------Insertion Queries----------------------------------------------------------------------------------------
    
    public static void newPatient(String code, String name, String surname, String password)
    {
        String insertQuery = "INSERT INTO Patient (PatientCode, PatientName, PatientSurname, Password) VALUES (?, ?, ?, ?)";

        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, password);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void newDoctor(String name, String surname, String password)
    {
        String insertQuery = "INSERT INTO Doctor (DoctorName, DoctorSurname, Password) VALUES (?, ?, ?)";

        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setBytes(3, password.getBytes());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
	//-----------------------------------------------------Deletion Queries----------------------------------------------------------------------------------------
    
    public static void delPatient(String code)
    {
    	String deleteQuery = "DELETE FROM Patient WHERE PatientCode = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, code);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void delDoctor(String code)
    {
    	String deleteQuery = "DELETE FROM Doctor WHERE DoctorCode = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, code);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	//-----------------------------------------------------Get Data Queries----------------------------------------------------------------------------------------
    
    public static void getSpecialization(String code) 
    {
    	String deleteQuery = "SELECT * FROM Specialization, SpecializationDoctor WHERE SpecializationDoctor.DoctorCode IN (SELECT DoctorCode FROM Doctor WHERE DoctorCode = ?)";

        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, code);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void getPrescription()
    {
    	
    }
    
    public static void getIllnesData(String code)
    {
    	String deleteQuery = "Select * FROM Illness WHERE IllnessCode = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, code);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<User> getAllDoctors() {
    	entries.clear();
    	String query = "SELECT DoctorCode, DoctorName, DoctorSurname FROM Doctor";

        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int doctorCode = rs.getInt("DoctorCode");
                String doctorName = rs.getString("DoctorName");
                String doctorSurname = rs.getString("DoctorSurname");
                entries.add(new Doctor(doctorCode, doctorName, doctorSurname));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return entries;
    }
    
    public static ArrayList<User> getAllPatients() {
    	entries.clear();
    	String query = "SELECT PatientCode, PatientName, PatientSurname FROM Patient";

        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int patientCode = rs.getInt("PatientCode");
                String patientName = rs.getString("PatientName");
                String patientSurname = rs.getString("PatientSurname");
                entries.add(new Patient(patientCode, patientName, patientSurname));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return entries;
    }
}