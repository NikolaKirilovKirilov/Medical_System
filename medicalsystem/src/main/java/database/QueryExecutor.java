package database;

import java.sql.*;
import java.util.*;

import com.example.model.*;

import javax.swing.*;

public class QueryExecutor {

	static ArrayList<User> entries = new ArrayList<>();
	// -------------------------------- Insertion Queries --------------------------------

	public static void newPatient(String name, String surname, String password) {
		String query = "INSERT INTO Patient (PatientName, PatientSurname, Password) VALUES (?, ?, ?)";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, name);
			stmt.setString(2, surname);
			stmt.setString(3, password);
			System.out.println("Rows inserted: " + stmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void newDoctor(String name, String surname, String password, int specCode) {
		String insertDoctorSQL = "INSERT INTO Doctor (DoctorName, DoctorSurname, Password) VALUES (?, ?, ?)";
		String insertSpecializationSQL = "INSERT INTO DoctorSpecialization (DoctorCode, SpecializationCode) VALUES (?, ?)";

		try (Connection conn = Connector.getConnection();
			 PreparedStatement insertDoctorStmt = conn.prepareStatement(insertDoctorSQL, Statement.RETURN_GENERATED_KEYS);
			 PreparedStatement insertSpecStmt = conn.prepareStatement(insertSpecializationSQL)) {

			// Step 1: Insert into Doctor
			insertDoctorStmt.setString(1, name);
			insertDoctorStmt.setString(2, surname);
			insertDoctorStmt.setString(3, new String(password)); // convert char[] to String (consider hashing)
			insertDoctorStmt.executeUpdate();

			// Step 2: Retrieve generated DoctorCode
			int doctorCode = -1;
			try (ResultSet rs = insertDoctorStmt.getGeneratedKeys()) {
				if (rs.next()) {
					doctorCode = rs.getInt(1);
				} else {
					throw new SQLException("Failed to retrieve DoctorCode.");
				}
			}

			// Step 3: Insert into DoctorSpecialization
			insertSpecStmt.setInt(1, doctorCode);
			insertSpecStmt.setInt(2, specCode);
			insertSpecStmt.executeUpdate();

			System.out.println("Doctor and specialization inserted successfully.");

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

	public static void newDisease(String name, String description, String treatment) {
		String query = "INSERT INTO Illness (IllnesName, IllnesDescription, Treatment) VALUES (?, ?, ?)";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, name);
			stmt.setString(2, description);
			stmt.setString(3, treatment);
			System.out.println("Rows inserted: " + stmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void newMedication(String name, String description, String dosage) {
		String query = "INSERT INTO Medication (MedicationName, MedicationDescription, MedicationDosage) VALUES (?, ?, ?)";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, name);
			stmt.setString(2, description);
			stmt.setString(3, dosage);
			System.out.println("Rows inserted: " + stmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertPrescription(int doctorCode, int patientCode, int illnessCode, int medicationCode) throws SQLException {
		String sql = "INSERT INTO Prescription (PrescriptionCode, DoctorCode, PatientCode, IllnessCode, MedicationCode) " +
				"VALUES (?, ?, ?, ?, ?)";

		// You need to generate PrescriptionCode (since it's part of the PK). Example uses UUID-based int.
		int prescriptionCode = (int)(System.currentTimeMillis() % Integer.MAX_VALUE); // simplistic unique ID

		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, prescriptionCode);
			stmt.setInt(2, doctorCode);
			stmt.setInt(3, patientCode);
			stmt.setInt(4, illnessCode);
			stmt.setInt(5, medicationCode);
			stmt.executeUpdate();
			System.out.println("Inserted Prescription with code: " + prescriptionCode);
		}
	}

	public static void insertReferral(int doctorCode, int patientCode, int illnessCode, String description) throws SQLException {
		String sql = "INSERT INTO Referral (ReferralCode, DoctorCode, PatientCode, IllnessCode, Description) " +
				"VALUES (?, ?, ?, ?, ?)";

		int referralCode = (int)(System.nanoTime() % Integer.MAX_VALUE); // simplistic unique ID

		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, referralCode);
			stmt.setInt(2, doctorCode);
			stmt.setInt(3, patientCode);
			stmt.setInt(4, illnessCode);
			stmt.setString(5, description);
			stmt.executeUpdate();
			System.out.println("Inserted Referral with code: " + referralCode);
		}
	}


	public static void newAppointment(
			int doctorCode,
			int patientCode,
			String reason,
			String description,
			String date,     // format: yyyy-MM-dd
			String time,     // format: HH:mm:ss
			String status
	) {
		String sql = "INSERT INTO Appointment (DoctorCode, PatientCode, Reason, Description, Date, Hours, Status) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, doctorCode);
			stmt.setInt(2, patientCode);
			stmt.setString(3, reason);
			stmt.setString(4, description);
			stmt.setString(5, date);
			stmt.setString(6, time);      // time format should fit into NVARCHAR(11), e.g. "12:30"
			stmt.setString(7, status);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace(); // Use proper logging in real code
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
		executeDelete("DELETE FROM Medication WHERE MedicatonCode = ?", code);
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
		String query = """
        SELECT d.DoctorCode, d.DoctorName, d.DoctorSurname, s.SpecializationName
        FROM Doctor d
        JOIN DoctorSpecialization ds ON d.DoctorCode = ds.DoctorCode
        JOIN Specialization s ON ds.SpecializationCode = s.SpecializationCode
        WHERE d.DoctorCode = ? AND d.Password = ?
        """;

		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, code);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int docCode = rs.getInt("DoctorCode");
					String docName = rs.getString("DoctorName");
					String docSurname = rs.getString("DoctorSurname");
					String specialization = rs.getString("SpecializationName");
					return new Doctor(docCode, docName, docSurname, specialization);
				}
			}
		}
		return null; // Doctor not found
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

    public static Doctor getDoctorByCode(int code) throws SQLException {
        String query = """
        SELECT d.DoctorCode, d.DoctorName, d.DoctorSurname, s.SpecializationName
        FROM Doctor d
        JOIN DoctorSpecialization ds ON d.DoctorCode = ds.DoctorCode
        JOIN Specialization s ON ds.SpecializationCode = s.SpecializationCode
        WHERE d.DoctorCode = ?
        """;

        try (Connection conn = Connector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int docCode = rs.getInt("DoctorCode");
                    String docName = rs.getString("DoctorName");
                    String docSurname = rs.getString("DoctorSurname");
                    String specialization = rs.getString("SpecializationName");
                    return new Doctor(docCode, docName, docSurname, specialization);
                }
            }
        }
        return null;
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
        String query = """
        SELECT d.DoctorCode, d.DoctorName, d.DoctorSurname, s.SpecializationName
        FROM Doctor d
        JOIN DoctorSpecialization ds ON d.DoctorCode = ds.DoctorCode
        JOIN Specialization s ON ds.SpecializationCode = s.SpecializationCode
        """;

        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                entries.add(new Doctor(
                        rs.getInt("DoctorCode"),
                        rs.getString("DoctorName"),
                        rs.getString("DoctorSurname"),
                        rs.getString("SpecializationName")
                ));
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

	public static ArrayList<Specialization> getAllSpecializations() {
		ArrayList<Specialization> specializations = new ArrayList<>();

		String query = "SELECT SpecializationCode, SpecializationName, Description FROM Specialization";

		try (Connection conn = Connector.getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				int code = rs.getInt("SpecializationCode");
				String name = rs.getString("SpecializationName");
				String description = rs.getString("Description");

				specializations.add(new Specialization(code, name, description));
			}

		} catch (SQLException e) {
			e.printStackTrace(); // or use logging
		}

		return specializations;
	}

	//---------------------------------------------------Get All Appointments Queries------------------------------------------

	public static ArrayList<User> getAllAppointments() {
		entries.clear();
		String query = "SELECT DoctorCode, PatientCode, Reason, Description, Date, Hours, Status, AppointmentCode FROM Appointment";
		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				entries.add(new Appointment(
						rs.getInt("DoctorCode"),
						rs.getInt("PatientCode"),
						rs.getString("Reason"),
						rs.getString("Description"),
						rs.getString("Date"),
						rs.getString("Hours"),
						rs.getString("Status"),
						rs.getInt("AppointmentCode")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entries;
	}

	public static ArrayList<Appointment> getAppointmentsByDoctorCode(int doctorCode) {
		ArrayList<Appointment> appointments = new ArrayList<>();

		String sql = "SELECT DoctorCode, PatientCode, Reason, Description, Date, Hours, Status, AppointmentCode " +
				"FROM Appointment WHERE DoctorCode = ?";

		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, 1);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int docCode = rs.getInt("DoctorCode");
				int patCode = rs.getInt("PatientCode");
				String reason = rs.getString("Reason");
				String description = rs.getString("Description");
				String date = rs.getString("Date");
				String hours = rs.getString("Hours");
				String status = rs.getString("Status");
				int code = rs.getInt("AppointmentCode");

				Appointment appointment = new Appointment(docCode, patCode, reason, description, date, hours, status, code);
				appointments.add(appointment);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return appointments;
	}

	public static ArrayList<Appointment> getAppointmentsByPatientCode(int doctorCode) {
		ArrayList<Appointment> appointments = new ArrayList<>();

		String sql = "SELECT DoctorCode, PatientCode, Reason, Description, Date, Hours, Status, AppointmentCode " +
				"FROM Appointment WHERE DoctorCode = ?";

		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, doctorCode);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int docCode = rs.getInt("DoctorCode");
				int patCode = rs.getInt("PatientCode");
				String reason = rs.getString("Reason");
				String description = rs.getString("Description");
				String date = rs.getString("Date");
				String hours = rs.getString("Hours");
				String status = rs.getString("Status");
				int code = rs.getInt("AppointmentCode");

				Appointment appointment = new Appointment(docCode, patCode, reason, description, date, hours, status, code);
				appointments.add(appointment);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return appointments;
	}

	//---------------------------------------------------Get All Prescription Queries------------------------------------------

    public static ArrayList<User> getPatientsByDoctorCode(int doctorCode) throws SQLException {
        String query = """
        SELECT p.PatientCode, p.PatientName, p.PatientSurname, p.Password
        FROM Patient p
        INNER JOIN DoctorPatient dp ON p.PatientCode = dp.PatientCode
        WHERE dp.DoctorCode = ?
    """;

        ArrayList<User> patients = new ArrayList<>();
        try (Connection conn = Connector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorCode);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("PatientCode"),
                        rs.getString("PatientName"),
                        rs.getString("PatientSurname")
                );
                patients.add(patient);
            }
        }
        return patients;
    }


    public static Doctor getDoctorByPatientCode(int patientCode) {
		String query = """
        SELECT d.DoctorCode, d.DoctorName, d.DoctorSurname, s.SpecializationName
        FROM Doctor d
        JOIN DoctorPatient dp ON d.DoctorCode = dp.DoctorCode
        LEFT JOIN DoctorSpecialization sd ON d.DoctorCode = sd.DoctorCode
        LEFT JOIN Specialization s ON sd.SpecializationCode = s.SpecializationCode
        WHERE dp.PatientCode = ?
    """;

		try (Connection conn = Connector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, patientCode);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new Doctor(
						rs.getInt("DoctorCode"),
						rs.getString("DoctorName"),
						rs.getString("DoctorSurname"),
						rs.getString("SpecializationName")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


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
		String query = "SELECT PrescriptionCode, DoctorCode, PatientCode, IllnessCode, MedicationCode FROM Prescription WHERE PatientCode = ?";
		try (Connection conn = Connector.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, patCode);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					entries.add(new Prescription(
							rs.getInt("PrescriptionCode"),
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
		String query = "SELECT ReferralCode, DoctorCode, PatientCode, IllnessCode, Description FROM Referral WHERE DoctorCode = ?";
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
							rs.getInt("Description")));
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
		String query = "SELECT ReferralCode, DoctorCode, PatientCode, IllnessCode, Description FROM Referral WHERE PatientCode = ?";
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