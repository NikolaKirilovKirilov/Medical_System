package uiComponents;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import javax.swing.*;

import customColors.ColorSchemes;
import database.QueryExecutor;

public class Form extends JFrame implements ActionListener {

	private static final long serialVersionUID = -698594458484167312L;

	StyledComponents sc;

	public Form() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/MASicon.png"));
		this.setIconImage(icon.getImage());
		this.setResizable(false);
		this.setVisible(false);  // Delay visibility until fully built

		sc = new StyledComponents();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Reserved for future action delegation
	}

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
		buttonPanel.add(confirmButton);
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

	// ----------------- Insertion Methods -----------------

	public void doctorInsertion() {
		buildForm("Вмъкване на Доктор", new String[]{"Име:", "Фамилия:", "Парола:"},
			values -> QueryExecutor.newDoctor(values[0], values[1], values[2].toCharArray()));
	}

	public void specializationInsertion() {
		buildForm("Вмъкване на Специализация", new String[]{"Име на специализация:"},
			values -> QueryExecutor.newSpecialization(values[0]));
	}

	public void patientInsertion() {
		buildForm("Вмъкване на Пациент", new String[]{"Име:", "Фамилия:", "ЕГН:"},
			values -> QueryExecutor.newPatient(values[0], values[1], values[2]));
	}

	public void diseaseInsertion() {
		buildForm("Вмъкване на Заболяване", new String[]{"Име на заболяване:"},
			values -> QueryExecutor.newDisease(values[0]));
	}

	public void medicationInsertion() {
		buildForm("Вмъкване на Лекарство", new String[]{"Име на лекарство:"},
			values -> QueryExecutor.newMedication(values[0]));
	}

	public void prescriptionInsertion() {
		buildForm("Вмъкване на Рецепта", new String[]{"Код на доктор:", "Код на пациент:", "Код на лекарство:", "Дата:"},
			values -> QueryExecutor.newPrescription(values[0], values[1], values[2], values[3]));
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
		buildForm("Изтриване на Пациент", new String[]{"ЕГН:"},
			values -> QueryExecutor.deletePatient(values[0]));
	}

	public void diseaseDeletion() {
		buildForm("Изтриване на Заболяване", new String[]{"Код на заболяване:"},
			values -> QueryExecutor.deleteDisease(values[0]));
	}

	public void prescriptionDeletion() {
		buildForm("Изтриване на Рецепта", new String[]{"Код на рецепта:"},
			values -> QueryExecutor.deletePrescription(values[0]));
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

	public void setAppointment(LocalDate date) {
		JDialog dialog = new JDialog(this, "Часове за " + date, true);
		dialog.setSize(500, 255);
		dialog.setLayout(new BorderLayout());


		JPanel timeSlotPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		String[] timeSlots = {
				"9:00 - 9:30",
				"9:30 - 10:00",
				"10:00 - 10:30",
				"10:30 - 11:00",
				"11:00 - 11:30",
				"11:30 - 12:00",
				"12:00 - 12:30",
				"12:30 - 13:00",
				"13:00 - 13:30",
				"13:30 - 14:00",
				"14:00 - 14:30",
				"14:30 - 15:00",
				"15:00 - 15:30",
				"15:30 - 16:00",
				"16:00 - 16:30",
				"16:30 - 17:00"
		};

		for (String slot : timeSlots) {
			JButton slotButton = sc.createStyledButton(slot, ColorSchemes.MENU_GREEN, Color.GRAY, Color.WHITE);
			slotButton.setPreferredSize(new Dimension(130, 30));
			slotButton.addActionListener(e -> {
				dialog.dispose();
			});
			timeSlotPanel.add(slotButton);
		}

		dialog.add(timeSlotPanel, BorderLayout.CENTER);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}



	// ----------------- Functional Interface -----------------

	@FunctionalInterface
	interface FormCallback {
		void onConfirm(String[] values);
	}
}