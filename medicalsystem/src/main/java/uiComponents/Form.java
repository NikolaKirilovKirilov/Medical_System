package uiComponents;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.*;

import com.example.model.*;
import customColors.ColorSchemes;
import database.QueryExecutor;

import static java.lang.System.in;

public class Form extends JFrame implements ActionListener {

	private static final long serialVersionUID = -698594458484167312L;

	Color headerColor = ColorSchemes.MENU_GREEN, hoverBackgroundColor = Color.DARK_GRAY, hoverTextColor = Color.WHITE,
			contentColor = ColorSchemes.BACKGROUND_BEIGE;

	StyledComponents sc;

	public Form() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/MASicon.png"));
		this.setBackground(contentColor);
		this.setIconImage(icon.getImage());
		this.setResizable(false);
		this.setVisible(false);  // Delay visibility until fully built

		sc = new StyledComponents();
	}

	@Override
	public void actionPerformed(ActionEvent e) { }

	private void buildForm(String title, String[] labels, FormCallback callback) {
		this.setTitle(title);
		this.getContentPane().removeAll();

		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JTextField[] fields = new JTextField[labels.length];

		for (int i = 0; i < labels.length; i++) {
			JLabel label = new JLabel(labels[i]);
			JTextField field = (labels[i].toLowerCase().contains("парола")) ? new JPasswordField(20) : new JTextField(20);
			fields[i] = field;

			gbc.gridx = 0; gbc.gridy = i;
			formPanel.add(label, gbc);
			gbc.gridx = 1;
			formPanel.add(field, gbc);
		}

		JButton confirmButton = new JButton("Потвърди");
		JButton cancelButton = new JButton("Отказ");

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		confirmButton.addActionListener(e -> {
			//QueryExecutor.newPrescription();
			this.dispose();
		});
		buttonPanel.add(confirmButton);
		cancelButton.addActionListener(e -> this.dispose());
		buttonPanel.add(cancelButton);

		confirmButton.addActionListener(e -> {
			String[] values = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				if (fields[i] instanceof JPasswordField) {
					values[i] = new String(((JPasswordField) fields[i]).getPassword());
				} else {
					values[i] = fields[i].getText();
				}
			}
			callback.onConfirm(values);
			this.dispose();
		});

		cancelButton.addActionListener(e -> {
			for (JTextField field : fields) field.setText("");
			this.dispose();
		});

		this.setLayout(new BorderLayout());
		this.add(formPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void buildDoctorForm(String title, String[] labels, ArrayList<Specialization> specializations, FormCallback callback) {
		if (labels.length != 4) {
			throw new IllegalArgumentException("Exactly 4 labels are required.");
		}

		this.setTitle(title);
		this.setSize(450, 250);
		this.setLocationRelativeTo(null);

		// Input components
		JTextField nameField = new JTextField(20);
		JTextField surnameField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField(20);
		JComboBox<Specialization> specializationComboBox = new JComboBox<>(specializations.toArray(new Specialization[0]));

		// For gathering text inputs
		JTextField[] fields = {nameField, surnameField, passwordField};

		// Main form panel
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(contentColor);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Row 0 - Name
		gbc.gridx = 0; gbc.gridy = 0;
		formPanel.add(new JLabel(labels[0]), gbc);
		gbc.gridx = 1;
		formPanel.add(nameField, gbc);

		// Row 1 - Surname
		gbc.gridx = 0; gbc.gridy = 1;
		formPanel.add(new JLabel(labels[1]), gbc);
		gbc.gridx = 1;
		formPanel.add(surnameField, gbc);

		// Row 2 - Password
		gbc.gridx = 0; gbc.gridy = 2;
		formPanel.add(new JLabel(labels[2]), gbc);
		gbc.gridx = 1;
		formPanel.add(passwordField, gbc);

		// Row 3 - Specialization
		gbc.gridx = 0; gbc.gridy = 3;
		formPanel.add(new JLabel(labels[3]), gbc);
		gbc.gridx = 1;
		formPanel.add(specializationComboBox, gbc);

		// Buttons
		JButton confirmButton = new JButton("Потвърди");
		JButton cancelButton = new JButton("Отказ");

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonPanel.setBackground(contentColor);
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		// Confirm button action
		confirmButton.addActionListener(e -> {
			String[] values = new String[4];
			values[0] = nameField.getText();
			values[1] = surnameField.getText();
			values[2] = new String(passwordField.getPassword());
			values[3] = String.valueOf(((Specialization) specializationComboBox.getSelectedItem()).getCode());

			callback.onConfirm(values);
			this.dispose();
		});

		// Cancel button action
		cancelButton.addActionListener(e -> this.dispose());

		// Layout
		this.setLayout(new BorderLayout());
		this.add(formPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void AppointmentForm(Patient pat, LocalDate date, String selectedTime) {
		this.setTitle("Запазване на час");
		this.getContentPane().removeAll();

		Doctor doc = new Doctor();
		doc = QueryExecutor.getDoctorByPatientCode(pat.getCode());

		List<User> patients = QueryExecutor.getAllPatients();
		List<String> stringifiedPatients = patients.stream()
				.filter(u -> u instanceof Patient)
				.map(u -> {
					Patient p = (Patient) u;
					return p.getId() + ". " + p.getName();
				})
				.collect(Collectors.toList());

		JComboBox<String> patientBox = new JComboBox<>(stringifiedPatients.toArray(new String[0]));

		String[] reasons = {"Административна причина", "Преглед", "Лечение"};
		JComboBox<String> reasonBox = new JComboBox<>(reasons);
		JTextField description =  new JTextField();
		description.setMaximumSize(new Dimension(200, 100));
		description.setPreferredSize(new Dimension(200, 100));

		JComboBox<String>[] comboBoxes = new JComboBox[]{patientBox, reasonBox};
		for (JComboBox<String> box : comboBoxes) {
			box.setPreferredSize(new Dimension(200, 25));
			box.setSelectedItem(null);
			box.setBackground(Color.WHITE);
		}

		// Create labels
		JLabel doctorLable = new JLabel("Доктор:");
		JLabel patientLable = new JLabel("Пациент:");
		JLabel reasonLable = new JLabel("Причина:");
		JLabel descriptionLable = new JLabel("Описамие:");
		JLabel dateLable = new JLabel("Дата:");
		JLabel hourLable = new JLabel("Час:");

		JLabel doctorNameLable = new JLabel(doc.getFullName());
		JLabel chosenDateLable = new JLabel(date.toString());
		JLabel chosenHourLable = new JLabel(selectedTime);

		Dimension labelSize = new Dimension(80, 25);
		for (JLabel label : new JLabel[]{doctorLable, patientLable, reasonLable, descriptionLable, dateLable, hourLable}) {
			label.setPreferredSize(labelSize);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
		}

		// Panel layout using GridBagLayout
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;

		// Row 1 - Doctor
		gbc.gridx = 0; gbc.gridy = 0;
		formPanel.add(doctorLable, gbc);
		gbc.gridx = 1;

		formPanel.add(doctorNameLable, gbc);

		// Row 2 - Patient
		gbc.gridx = 0; gbc.gridy = 1;
		formPanel.add(patientLable, gbc);
		gbc.gridx = 1;
		formPanel.add(new JLabel(pat.getFullName()), gbc);

		// Row 3 - Reason
		gbc.gridx = 0; gbc.gridy = 2;
		formPanel.add(reasonLable, gbc);
		gbc.gridx = 1;
		formPanel.add(reasonBox, gbc);

		//Row 4 - Description
		gbc.gridx = 0; gbc.gridy = 3;
		formPanel.add(descriptionLable, gbc);
		gbc.gridx = 1;
		formPanel.add(description, gbc);

		//Row 4 - Date
		gbc.gridx = 0; gbc.gridy = 4;
		formPanel.add(dateLable, gbc);
		gbc.gridx = 1;
		formPanel.add(chosenDateLable, gbc);

		//Row 4 - Hour
		gbc.gridx = 0; gbc.gridy = 5;
		formPanel.add(hourLable, gbc);
		gbc.gridx = 1;
		formPanel.add(chosenHourLable, gbc);

		// Buttons
		JButton acceptButton = sc.createStyledButton("Потвърди", headerColor, hoverBackgroundColor, hoverTextColor);
		Doctor finalDoc = doc;
		acceptButton.addActionListener(e -> {
            assert finalDoc != null;
            QueryExecutor.newAppointment(
                    pat.getCode(),
                    finalDoc.getCode(),
                    reasonBox.getSelectedItem().toString(),
                    description.getSelectedText(),
                    date.toString(),
                    selectedTime,
                    "Изчакващ");
            this.dispose();
		});

		JButton cancelButton = sc.createStyledButton("Отказ", headerColor, hoverBackgroundColor, hoverTextColor);
		cancelButton.addActionListener(e -> this.dispose());

		// Wrap each button to align them left and right
		JPanel leftButtonPanel = new JPanel(new BorderLayout());
		leftButtonPanel.add(acceptButton, BorderLayout.WEST);

		JPanel rightButtonPanel = new JPanel(new BorderLayout());
		rightButtonPanel.add(cancelButton, BorderLayout.EAST);

		// GridLayout with no gap
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		buttonPanel.add(leftButtonPanel);
		buttonPanel.add(rightButtonPanel);


		// Final container
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(formPanel, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.add(container, BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void AppointmentForm(Doctor doc, LocalDate date, String selectedTime) {
		this.setTitle("Запазване на час");
		this.getContentPane().removeAll();

		List<User> patients = QueryExecutor.getAllPatients();
		List<String> stringifiedPatients = patients.stream()
				.filter(u -> u instanceof Patient)
				.map(u -> {
					Patient p = (Patient) u;
					return p.getId() + ". " + p.getName();
				})
				.collect(Collectors.toList());

		List<User> illnesses = QueryExecutor.getAllDiseases();
		List<String> stringifiedIllnesses = illnesses.stream()
				.filter(u -> u instanceof Disease)
				.map(u -> {
					Disease d = (Disease) u;
					return d.getId() + ". " + d.getName();
				})
				.collect(Collectors.toList());

		List<User> medications = QueryExecutor.getAllMedications();
		List<String> stringifiedMedications = medications.stream()
				.filter(u -> u instanceof Medication)
				.map(u -> {
					Medication m = (Medication) u;
					return m.getId() + ". " + m.getName();
				})
				.collect(Collectors.toList());

		JComboBox<String> patientsBox = new JComboBox<>(stringifiedPatients.toArray(new String[0]));
		JComboBox<String> illnessesBox = new JComboBox<>(stringifiedIllnesses.toArray(new String[0]));
		JComboBox<String> medicationsBox = new JComboBox<>(stringifiedMedications.toArray(new String[0]));


		JComboBox<String>[] comboBoxes = new JComboBox[]{patientsBox, illnessesBox, medicationsBox};
		for (JComboBox<String> box : comboBoxes) {
			box.setPreferredSize(new Dimension(200, 25));
			box.setSelectedItem(null);
			box.setBackground(Color.WHITE);
		}

		// Create labels
		JLabel patientLabel = new JLabel("Пациент:");
		JLabel illnessLabel = new JLabel("Заболяване:");
		JLabel medicationLabel = new JLabel("Медикамент:");

		Dimension labelSize = new Dimension(80, 25);
		for (JLabel label : new JLabel[]{patientLabel, illnessLabel, medicationLabel}) {
			label.setPreferredSize(labelSize);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
		}

		// Panel layout using GridBagLayout
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;

		// Row 1 - Patient
		gbc.gridx = 0; gbc.gridy = 0;
		formPanel.add(patientLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(patientsBox, gbc);

		// Row 2 - Illness
		gbc.gridx = 0; gbc.gridy = 1;
		formPanel.add(illnessLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(illnessesBox, gbc);

		// Row 3 - Medication
		gbc.gridx = 0; gbc.gridy = 2;
		formPanel.add(medicationLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(medicationsBox, gbc);

		// Buttons
		JButton acceptButton = sc.createStyledButton("Потвърди", headerColor, hoverBackgroundColor, hoverTextColor);
		acceptButton.addActionListener(e -> {
			int patientCode = Character.getNumericValue(patientsBox.getSelectedItem().toString().charAt(0));
			int illnessCode = Character.getNumericValue(illnessesBox.getSelectedItem().toString().charAt(0));
			int medicationCode = Character.getNumericValue(medicationsBox.getSelectedItem().toString().charAt(0));

			try {
				QueryExecutor.insertPrescription(doc.getCode(), patientCode, illnessCode, medicationCode);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
			this.dispose();
		});

		JButton cancelButton = sc.createStyledButton("Отказ", headerColor, hoverBackgroundColor, hoverTextColor);
		cancelButton.addActionListener(e -> this.dispose());

		// Wrap each button to align them left and right
		JPanel leftButtonPanel = new JPanel(new BorderLayout());
		leftButtonPanel.add(acceptButton, BorderLayout.WEST);

		JPanel rightButtonPanel = new JPanel(new BorderLayout());
		rightButtonPanel.add(cancelButton, BorderLayout.EAST);

		// GridLayout with no gap
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		buttonPanel.add(leftButtonPanel);
		buttonPanel.add(rightButtonPanel);


		// Final container
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(formPanel, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.add(container, BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void prescriptionForm(Doctor doc) {
		this.setTitle("Издаване на рецепта");
		this.getContentPane().removeAll();

		List<User> patients = QueryExecutor.getAllPatients();
		List<String> stringifiedPatients = patients.stream()
				.filter(u -> u instanceof Patient)
				.map(u -> {
					Patient p = (Patient) u;
					return p.getId() + ". " + p.getName();
				})
				.collect(Collectors.toList());

		List<User> illnesses = QueryExecutor.getAllDiseases();
		List<String> stringifiedIllnesses = illnesses.stream()
				.filter(u -> u instanceof Disease)
				.map(u -> {
					Disease d = (Disease) u;
					return d.getId() + ". " + d.getName();
				})
				.collect(Collectors.toList());

		List<User> medications = QueryExecutor.getAllMedications();
		List<String> stringifiedMedications = medications.stream()
				.filter(u -> u instanceof Medication)
				.map(u -> {
					Medication m = (Medication) u;
					return m.getId() + ". " + m.getName();
				})
				.collect(Collectors.toList());

		JComboBox<String> patientsBox = new JComboBox<>(stringifiedPatients.toArray(new String[0]));
		JComboBox<String> illnessesBox = new JComboBox<>(stringifiedIllnesses.toArray(new String[0]));
		JComboBox<String> medicationsBox = new JComboBox<>(stringifiedMedications.toArray(new String[0]));


		JComboBox<String>[] comboBoxes = new JComboBox[]{patientsBox, illnessesBox, medicationsBox};
		for (JComboBox<String> box : comboBoxes) {
			box.setPreferredSize(new Dimension(200, 25));
			box.setSelectedItem(null);
			box.setBackground(Color.WHITE);
		}

		// Create labels
		JLabel patientLabel = new JLabel("Пациент:");
		JLabel illnessLabel = new JLabel("Заболяване:");
		JLabel medicationLabel = new JLabel("Медикамент:");

		Dimension labelSize = new Dimension(80, 25);
		for (JLabel label : new JLabel[]{patientLabel, illnessLabel, medicationLabel}) {
			label.setPreferredSize(labelSize);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
		}

		// Panel layout using GridBagLayout
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;

		// Row 1 - Patient
		gbc.gridx = 0; gbc.gridy = 0;
		formPanel.add(patientLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(patientsBox, gbc);

		// Row 2 - Illness
		gbc.gridx = 0; gbc.gridy = 1;
		formPanel.add(illnessLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(illnessesBox, gbc);

		// Row 3 - Medication
		gbc.gridx = 0; gbc.gridy = 2;
		formPanel.add(medicationLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(medicationsBox, gbc);

		// Buttons
		JButton acceptButton = sc.createStyledButton("Потвърди", headerColor, hoverBackgroundColor, hoverTextColor);
		acceptButton.addActionListener(e -> {
			int patientCode = Character.getNumericValue(patientsBox.getSelectedItem().toString().charAt(0));
			int illnessCode = Character.getNumericValue(illnessesBox.getSelectedItem().toString().charAt(0));
			int medicationCode = Character.getNumericValue(medicationsBox.getSelectedItem().toString().charAt(0));

            try {
                QueryExecutor.insertPrescription(doc.getCode(), patientCode, illnessCode, medicationCode);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
		});

		JButton cancelButton = sc.createStyledButton("Отказ", headerColor, hoverBackgroundColor, hoverTextColor);
		cancelButton.addActionListener(e -> this.dispose());

		// Wrap each button to align them left and right
		JPanel leftButtonPanel = new JPanel(new BorderLayout());
		leftButtonPanel.add(acceptButton, BorderLayout.WEST);

		JPanel rightButtonPanel = new JPanel(new BorderLayout());
		rightButtonPanel.add(cancelButton, BorderLayout.EAST);

		// GridLayout with no gap
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		buttonPanel.add(leftButtonPanel);
		buttonPanel.add(rightButtonPanel);


		// Final container
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(formPanel, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.add(container, BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void referralForm(Doctor doc) {
		this.setTitle("Издаване на направление");

		List<User> patients = QueryExecutor.getAllPatients();
		List<String> stringifiedPatients = patients.stream()
				.filter(u -> u instanceof Patient)
				.map(u -> {
					Patient p = (Patient) u;
					return p.getId() + ". " + p.getName();
				})
				.collect(Collectors.toList());

		List<User> illnesses = QueryExecutor.getAllDiseases();
		List<String> stringifiedIllnesses = illnesses.stream()
				.filter(u -> u instanceof Disease)
				.map(u -> {
					Disease d = (Disease) u;
					return d.getId() + ". " + d.getName();
				})
				.collect(Collectors.toList());

		JComboBox<String> patientsBox = new JComboBox<>(stringifiedPatients.toArray(new String[0]));
		JComboBox<String> illnessesBox = new JComboBox<>(stringifiedIllnesses.toArray(new String[0]));

		JComboBox<String>[] comboBoxes = new JComboBox[]{patientsBox, illnessesBox};
		for (JComboBox<String> box : comboBoxes) {
			box.setPreferredSize(new Dimension(200, 25));
			box.setSelectedItem(null);
			box.setBackground(Color.WHITE);
		}

		// Create labels
		JLabel patientLabel = new JLabel("Пациент:");
		JLabel illnessLabel = new JLabel("Заболяване:");
		JLabel medicationLabel = new JLabel("Описание:");

		Dimension labelSize = new Dimension(80, 25);
		for (JLabel label : new JLabel[]{patientLabel, illnessLabel, medicationLabel}) {
			label.setPreferredSize(labelSize);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
		}

		JTextField diseaseDescription = new JTextField();
		diseaseDescription.setPreferredSize(new Dimension(200, 200));
		diseaseDescription.setHorizontalAlignment(SwingConstants.CENTER);
		diseaseDescription.setLayout(new FlowLayout());

		// Panel layout using GridBagLayout
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;

		// Row 1 - Patient
		gbc.gridx = 0; gbc.gridy = 0;
		formPanel.add(patientLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(patientsBox, gbc);

		// Row 2 - Illness
		gbc.gridx = 0; gbc.gridy = 1;
		formPanel.add(illnessLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(illnessesBox, gbc);

		// Row 3 - Medication
		gbc.gridx = 0; gbc.gridy = 2;
		formPanel.add(medicationLabel, gbc);
		gbc.gridx = 1;
		formPanel.add(diseaseDescription, gbc);

		// Buttons
		JButton acceptButton = sc.createStyledButton("Потвърди", headerColor, hoverBackgroundColor, hoverTextColor);
		acceptButton.addActionListener(e -> {
			int patientCode = Character.getNumericValue(patientsBox.getSelectedItem().toString().charAt(0));
			int illnessCode = Character.getNumericValue(illnessesBox.getSelectedItem().toString().charAt(0));

			try {
				QueryExecutor.insertReferral(doc.getCode(), patientCode, illnessCode, diseaseDescription.getText());
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
			this.dispose();
			this.dispose();
		});

		JButton cancelButton = sc.createStyledButton("Отказ", headerColor, hoverBackgroundColor, hoverTextColor);
		cancelButton.addActionListener(e -> this.dispose());

		// Wrap each button to align them left and right
		JPanel leftButtonPanel = new JPanel(new BorderLayout());
		leftButtonPanel.add(acceptButton, BorderLayout.WEST);

		JPanel rightButtonPanel = new JPanel(new BorderLayout());
		rightButtonPanel.add(cancelButton, BorderLayout.EAST);

		// GridLayout with no gap
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		buttonPanel.add(leftButtonPanel);
		buttonPanel.add(rightButtonPanel);


		// Final container
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(formPanel, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.add(container, BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	// ----------------- Insertion Methods -----------------

	public void doctorInsertion() {
		ArrayList<Specialization> specs = QueryExecutor.getAllSpecializations();
		buildDoctorForm(
				"Вмъкване на Доктор",
				new String[]{"Име:", "Фамилия:", "Парола:", "Специализация:"},
				specs,
				values -> QueryExecutor.newDoctor(values[0], values[1], values[2], Integer.parseInt(values[3]))
		);
	}

	public void specializationInsertion() {
		buildForm("Вписване на Специализация", new String[]{"Име на специализация:"},
			values -> QueryExecutor.newSpecialization(values[0]));
	}

	public void patientInsertion() {
		buildForm("Вписване на Пациент", new String[]{"Име:", "Фамилия:", "Парола:"},
			values -> QueryExecutor.newPatient(values[0], values[1], values[2]));
	}

	public void diseaseInsertion() {
		buildForm("Вписване на Заболяване", new String[]{"Име на заболяване:", "Описание на заболяванет:", "Лечение:"},
			values -> QueryExecutor.newDisease(values[0], values[1], values[2]));
	}

	public void medicationInsertion() {
		buildForm("Вписване на Лекарство", new String[]{"Име на медикаменти:", "Описание:", "Дозировка"},
			values -> QueryExecutor.newMedication(values[0], values[1], values[2]));
	}

	// ----------------- Deletion Methods (Stub Example) -----------------

	public void doctorDeletion() {
		buildForm("Изтриване на Доктор", new String[]{"Код:"},
			values -> QueryExecutor.deleteDoctor(values[0]));
	}

	public void specializationDeletion() {
		buildForm("Изтриване на Специализация", new String[]{"Код на специализация:"},
			values -> QueryExecutor.deleteSpecialization(values[0]));
	}

	public void patientDeletion() {
		buildForm("Изтриване на Пациент", new String[]{"Код на пациент:"},
			values -> QueryExecutor.deletePatient(values[0]));
	}

	public void diseaseDeletion() {
		buildForm("Изтриване на Заболяване", new String[]{"Код на заболяване:"},
			values -> QueryExecutor.deleteDisease(values[0]));
	}

	public void medicationDeletion() {
		buildForm("Изтриване на Лекарство", new String[]{"Код на лекарство:"},
			values -> QueryExecutor.deleteMedication(values[0]));
	}

	// ----------------- Correction Methods (To Be Implemented Similarly) -----------------

	public void doctorCorrection() {
		// Sample logic or leave as is for now
		this.setVisible(true);
	}

	public void patientCorrection() {
		this.setVisible(true);
	}

	public void diseaseCorrection() {
		this.setVisible(true);
	}

	public void prescriptionCorrection() {
		this.setVisible(true);
	}

	// ----------------- Appointment Methods -----------------

	public void insertAppointment() {


	}

	public void setAppointment(LocalDate date, Doctor doc, ArrayList<Appointment> apps) {
		JDialog dialog = new JDialog(this, "Часове за " + date, true);
		dialog.setSize(500, 255);
		dialog.setLayout(new BorderLayout());

		JPanel timeSlotPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		String[] timeSlots = {
				"9:00-9:30",
				"9:30-10:00",
				"10:00-10:30",
				"10:30-11:00",
				"11:00-11:30",
				"11:30-12:00",
				"12:00-12:30",
				"12:30-13:00",
				"13:00-13:30",
				"13:30-14:00",
				"14:00-14:30",
				"14:30-15:00",
				"15:00-15:30",
				"15:30-16:00",
				"16:00-16:30",
				"16:30-17:00"
		};

		for (String slot : timeSlots) {
			JButton slotButton = new JButton();
			for(Appointment timeStamp : apps)
				if (slot.equals(timeStamp.getHours())) {
					slotButton = sc.createDisabledButton(slot);
					slotButton.setPreferredSize(new Dimension(130, 30));
				} else {
					slotButton = sc.createStyledButton(slot, ColorSchemes.MENU_GREEN, Color.GRAY, Color.WHITE);
					slotButton.setPreferredSize(new Dimension(130, 30));
					slotButton.addActionListener(e -> {
						String selectedTime = slot.split(" - ")[0]; // Get start time only (e.g., "9:00")
						AppointmentForm(doc, date, selectedTime);
						dialog.dispose();
					});
				}
			timeSlotPanel.add(slotButton);
		}

		dialog.add(timeSlotPanel, BorderLayout.CENTER);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	public void setAppointment(LocalDate date, Patient pat, ArrayList<Appointment> entries) {
		JDialog dialog = new JDialog(this, "Часове за " + date, true);
		dialog.setSize(500, 255);
		dialog.setLayout(new BorderLayout());

		JPanel timeSlotPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		String[] timeSlots = {
				"9:00-9:30",
				"9:30-10:00",
				"10:00-10:30",
				"10:30-11:00",
				"11:00-11:30",
				"11:30-12:00",
				"12:00-12:30",
				"12:30-13:00",
				"13:00-13:30",
				"13:30-14:00",
				"14:00-14:30",
				"14:30-15:00",
				"15:00-15:30",
				"15:30-16:00",
				"16:00-16:30",
				"16:30-17:00"
		};

		for (String slot : timeSlots) {
			JButton slotButton = sc.createStyledButton(slot, ColorSchemes.MENU_GREEN, Color.GRAY, Color.WHITE);
			slotButton.setPreferredSize(new Dimension(130, 30));
			slotButton.addActionListener(e -> {
				String selectedTime = slot.split(" - ")[0]; // Get start time only (e.g., "9:00")
				AppointmentForm(pat, date, selectedTime);
				dialog.dispose();
			});
			timeSlotPanel.add(slotButton);
		}

		dialog.add(timeSlotPanel, BorderLayout.CENTER);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	public void insertAppointmentData(LocalDate date, String timeStr, String instance) {
		if(Objects.equals(instance, "Пациент"))
			buildForm("Запитване за час", new String[]{
						"Код на доктор:", "Код на пациент:", "Причина:", "Описание:", "Статус:"},
				values -> {
					try {
						int doctorCode = Integer.parseInt(values[0]);
						int patientCode = Integer.parseInt(values[1]);
						String reason = values[2];
						String description = values[3];
						String status = values[4];

						QueryExecutor.newAppointment(
								doctorCode, patientCode, reason, description,
								date.toString(), timeStr + ":00", // time as HH:mm:ss
								status
						);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this, "Грешка при записване на час: " + ex.getMessage(),
								"Грешка", JOptionPane.ERROR_MESSAGE);
					}
				});
		if(Objects.equals(instance, "Доктор"))
			buildForm("Одобряване на час", new String[]{
							"Код на доктор:", "Код на пациент:", "Причина:", "Описание:", "Статус:"},
					values -> {
						try {
							int doctorCode = Integer.parseInt(values[0]);
							int patientCode = Integer.parseInt(values[1]);
							String reason = values[2];
							String description = values[3];
							String status = values[4];

							QueryExecutor.newAppointment(
									doctorCode, patientCode, reason, description,
									date.toString(), timeStr + ":00", // time as HH:mm:ss
									status
							);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, "Грешка при записване на час: " + ex.getMessage(),
									"Грешка", JOptionPane.ERROR_MESSAGE);
						}
					});
	}


	// ----------------- Functional Interface -----------------

	@FunctionalInterface
	interface FormCallback {
		void onConfirm(String[] values);
	}
}