package database;

import java.sql.*;
import java.util.*;

import com.example.model.*;

public class QueryExecutor {

	static ArrayList<User> entries = new ArrayList<>();
	// -------------------------------- Insertion Queries --------------------------------

	public static void newPatient(String name, String surname, String password) {
		String query = "INSERT INTO Patient (PatientCode, PatientName, PatientSurname, Password) VALUES (?, ?, ?, ?)";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(2, name);
			stmt.setString(3, surname);
			stmt.setString(4, password);
			System.out.println("Rows inserted: " + stmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void newDoctor(String name, String surname, char[] password) {
		String query = "INSERT INTO Doctor (DoctorName, DoctorSurname, Password) VALUES (?, ?, ?)";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, name);
			stmt.setString(2, surname);
			stmt.setBytes(3, new String(password).getBytes());
			System.out.println("Rows inserted: " + stmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void newSpecialization(String name) {
		String query = "INSERT INTO Specialization (SpecializationName) VALUES (?)";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, name);
			System.out.println("Rows inserted: " + stmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void newDisease(String name) {
		String query = "INSERT INTO Illness (IllnessName) VALUES (?)";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, name);
			System.out.println("Rows inserted: " + stmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void newMedication(String name) {
		String query = "INSERT INTO Medication (MedicationName) VALUES (?)";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, name);
			System.out.println("Rows inserted: " + stmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void newPrescription(String doctorCode, String patientCode, String medicationCode, String date) {
		String query = "INSERT INTO Prescription (DoctorCode, PatientCode, MedicationCode, PrescriptionDate) VALUES (?, ?, ?, ?)";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, doctorCode);
			stmt.setString(2, patientCode);
			stmt.setString(3, medicationCode);
			stmt.setString(4, date);
			System.out.println("Rows inserted: " + stmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------- Deletion Queries --------------------------------

	public static void deletePatient(String code) {
		executeDelete("DELETE FROM Patient WHERE PatientCode = ?", code);
	}

	public static void deleteDoctor(String code) {
		executeDelete("DELETE FROM Doctor WHERE DoctorCode = ?", code);
	}

	public static void deleteSpecialization(String code) {
		executeDelete("DELETE FROM Specialization WHERE SpecializationCode = ?", code);
	}

	public static void deleteDisease(String code) {
		executeDelete("DELETE FROM Illness WHERE IllnessCode = ?", code);
	}

	public static void deleteMedication(String code) {
		executeDelete("DELETE FROM Medication WHERE MedicationCode = ?", code);
	}

	public static void deletePrescription(String code) {
		executeDelete("DELETE FROM Prescription WHERE PrescriptionCode = ?", code);
	}

	private static void executeDelete(String query, String code) {
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, code);
			System.out.println("Rows deleted: " + stmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------- Select Queries --------------------------------

	public static Patient getPatientByNamePassword(String name, String password) throws SQLException {
		String query = "SELECT PatientCode, PatientName, PatientSurname FROM Patient WHERE PatientName = ? AND Password = ?";
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt("PatientCode");
					String patName = rs.getString("PatientName");
					String surname = rs.getString("PatientSurname");
					return new Patient (id, patName, surname);
				}
			}
		}
		return null; // Patient not found
	}

	public static Doctor getDoctorByCodePassword(int code, String password) throws SQLException {
		String query = "SELECT DoctorCode, DoctorName, DoctorSurname FROM Doctor WHERE DoctorCode = ? AND Password = ?";
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, code);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int docCode = rs.getInt("DoctorCode");
					String docName = rs.getString("DoctorName");
					String docSurname = rs.getString("DoctorSurname");
					return new Doctor(docCode, docName, docSurname);
				}
			}
		}
		return null; // Patient not found
	}

	public static Administrator getAdministratorByCode(int code) throws SQLException {
		String query = "SELECT AdminCode FROM Admin WHERE AdminCode = ?";
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, code);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int adminCode = rs.getInt("AdminCode");
					return new Administrator(adminCode);
				}
			}
		}
		return null; // Patient not found
	}

	public static void getSpecializationByCode(int docCode) {
		String query = "SELECT s.SpecializationName FROM Specialization s " +
		               "JOIN SpecializationDoctor sd ON s.SpecializationCode = sd.SpecializationCode " +
		               "WHERE sd.DoctorCode = ?";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, docCode);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("Specialization: " + rs.getString("SpecializationName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void getIllnessByCode(int illnessCode) {
		String query = "SELECT IllnessCode, IllnesName, IllnesDescription, Treatment FROM Illness WHERE IllnessCode = ?";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, illnessCode);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				entries.add(new Disease(
						rs.getInt("IllnessCode"),
						rs.getString("IllnesName"),
						rs.getString("IllnesDescription"),
						rs.getString("Treatment")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//---------------------------------------------------Get All Data Queries------------------------------------------

	public static ArrayList<User> getAllDoctors() {
		entries.clear();
		String query = "SELECT DoctorCode, DoctorName, DoctorSurname FROM Doctor";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				entries.add(new Doctor(
					rs.getInt("DoctorCode"),
					rs.getString("DoctorName"),
					rs.getString("DoctorSurname")));
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
				entries.add(new Patient(
					rs.getInt("PatientCode"),
					rs.getString("PatientName"),
					rs.getString("PatientSurname")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entries;
	}
	
	public static ArrayList<User> getAllDiseases() {
		entries.clear();
		String query = "SELECT IllnessCode, IllnesName, IllnesDescription, Treatment FROM Illness";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				entries.add(new Disease(
					rs.getInt("IllnessCode"),
					rs.getString("IllnesName"),
					rs.getString("IllnesDescription"),
					rs.getString("Treatment")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entries;
	}
	
	public static ArrayList<User> getAllMedications() {
		entries.clear();
		String query = "SELECT MedicatonCode, MedicationName, MedicationDescription, MedicationDosage FROM Medication";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				entries.add(new Medication(
					rs.getInt("MedicatonCode"),
					rs.getString("MedicationName"),
					rs.getString("MedicationDescription"),
					rs.getString("MedicationDosage")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entries;
	}

	//---------------------------------------------------Get All Appointments Queries------------------------------------------

	public static ArrayList<User> getAllAppointments() {
		entries.clear();
		String query = "SELECT AppointmentCode, DoctorCode, PatientCode, Reason, Description FROM Medication";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				entries.add(new Appointment(
						rs.getInt("AppointmentCode"),
						rs.getInt("DoctorCode"),
						rs.getInt("PatientCode"),
						rs.getString("Reason"),
						rs.getString("Description")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entries;
	}

	public static ArrayList<User> getAllAppointmentsByPatient(int patCode) {
		entries.clear();
		String query = "SELECT AppointmentCode, DoctorCode, PatientCode, Reason, Description FROM Appointment WHERE PatientCode = ?";
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, patCode);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					entries.add(new Appointment(
							rs.getInt("AppointmentCode"),
							rs.getInt("DoctorCode"),
							rs.getInt("PatientCode"),
							rs.getString("Reason"),
							rs.getString("Description")));
				}
			}
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return entries;
	}

	public static ArrayList<User> getAllAppointmentsByDoctor(int docCode) {
		entries.clear();
		String query = "SELECT AppointmentCode, DoctorCode, PatientCode, Reason, Description FROM Appointment WHERE DoctorCode = ?";
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, docCode);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					entries.add(new Appointment(
							rs.getInt("AppointmentCode"),
							rs.getInt("DoctorCode"),
							rs.getInt("PatientCode"),
							rs.getString("Reason"),
							rs.getString("Description")));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return entries;
	}

	//---------------------------------------------------Get All Prescription Queries------------------------------------------

	public static ArrayList<User> getAllPrescriptions() {
		entries.clear();
		String query = "SELECT PrescriptionCode, DoctorCode, PatientCode, IllnessCode, MedicationCode FROM Prescription";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				entries.add(new Prescription(
						rs.getInt("PrescriptionCode"),
						rs.getInt("DoctorCode"),
						rs.getInt("PatientCode"),
						rs.getInt("IllnessCode"),
						rs.getInt("MedicationCode")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entries;
	}

	public static ArrayList<User> getAllPrescriptionsByPatient(int patCode) {
		entries.clear();
		String query = "SELECT AppointmentCode, DoctorCode, PatientCode, IllnessCode, MedicationCode FROM Prescription WHERE PatientCode = ?";
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, patCode);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					entries.add(new Prescription(
							rs.getInt("ReferralCode"),
							rs.getInt("DoctorCode"),
							rs.getInt("PatientCode"),
							rs.getInt("IllnessCode"),
							rs.getInt("MedicationCode")));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return entries;
	}

	public static ArrayList<User> getAllPrescriptionsByDoctor(int docCode) {
		entries.clear();
		String query = "SELECT AppointmentCode, DoctorCode, PatientCode, IllnessCode, MedicationCode FROM Prescription WHERE DoctorCode = ?";
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, docCode);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					entries.add(new Prescription(
							rs.getInt("ReferralCode"),
							rs.getInt("DoctorCode"),
							rs.getInt("PatientCode"),
							rs.getInt("IllnessCode"),
							rs.getInt("MedicationCode")));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return entries;
	}

	//---------------------------------------------------Get All Referrals Queries------------------------------------------

	public static ArrayList<User> getAllReferrals() {
		entries.clear();
		String query = "SELECT ReferralCode, DoctorCode, PatientCode, IllnessCode, Description FROM Referral";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				entries.add(new Referral(
						rs.getInt("ReferralCode"),
						rs.getInt("DoctorCode"),
						rs.getInt("PatientCode"),
						rs.getInt("IllnessCode"),
						rs.getString("Description")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entries;
	}

	public static ArrayList<User> getAllReferralsByPatient(int patCode) {
		entries.clear();
		String query = "SELECT AppointmentCode, DoctorCode, PatientCode, IllnessCode, Description FROM Referral WHERE PatientCode = ?";
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, patCode);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					entries.add(new Referral(
							rs.getInt("ReferralCode"),
							rs.getInt("DoctorCode"),
							rs.getInt("PatientCode"),
							rs.getInt("IllnessCode"),
							rs.getString("Description")));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return entries;
	}

	public static ArrayList<User> getAllReferralsByDoctor(int docCode) {
		entries.clear();
		String query = "SELECT ReferralCode, DoctorCode, PatientCode, IllnessCode, Description FROM Referral WHERE DoctorCode = ?";
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, docCode);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					entries.add(new Referral(
							rs.getInt("AppointmentCode"),
							rs.getInt("DoctorCode"),
							rs.getInt("PatientCode"),
							rs.getInt("IllnessCode"),
							rs.getString("Description")));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return entries;
	}
}