package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;

public class CrudExecutor {

    public static void executeSelectQuery() {
        String query = "SELECT * FROM your_table_name";

        try (Connection connection = Connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // Retrieve data by column name or index
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                // Display values
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void LogInQuery(JComboBox<String> combobox) {
        String query = "SELECT * FROM Admin";

        try (Connection connection = Connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // Retrieve data by column name or index
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                // Display values
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
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

    public static void newDoctor(String code, String name, String surname, String password)
    {
        String insertQuery = "INSERT INTO Doctor (DoctorCode, DoctorName, DoctorSurname, Password) VALUES (?, ?, ?, ?)";

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
    
    	public static void main(String[] args) {
    		executeSelectQuery();
    	}
    }