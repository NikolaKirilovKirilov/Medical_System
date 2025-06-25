package ui;

import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.function.BiConsumer;
import javax.swing.*;

import backendFunctionalities.ContentOrganizer;
import customColors.*;
import com.example.model.*;
import database.QueryExecutor;
import uiComponents.Form;
import uiComponents.SettingsSetter;
import uiComponents.StyledComponents;

public class administratorView extends JFrame implements ActionListener {
	@Serial
	private static final long serialVersionUID = 1L;

	Administrator admin;

	JPanel mainPanel, headerPanel, menuPanel, contentPanel, contentButtonPanel;
	JButton insertButton, deleteButton;
	StyledComponents sc;
	SettingsSetter setSetter;
	Color headerColor = ColorSchemes.MENU_GREEN, hoverBackgroundColor = Color.DARK_GRAY, hoverTextColor = Color.WHITE;
	Form caller;
	ContentOrganizer organizer;

	static ArrayList<com.example.model.User> entries;
	final private String[] doctorOptions = { "Код", "Име", "Специализация" };
	final private String[] patDisMedOptions = { "Код", "Име"};


	public administratorView(int code) throws SQLException {
		admin = QueryExecutor.getAdministratorByCode(code);

		this.setTitle("Здравейте Администратор: " + admin.getCode());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(false);
		this.setVisible(true);

		ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/MASicon.png")));
		this.setIconImage(icon.getImage());

		initializePanels();
		sc = new StyledComponents();
		setSetter = new SettingsSetter();
		caller = new Form();
		organizer = new ContentOrganizer();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;

		int menuPanelWidth = (int) (screenWidth * 0.3);
		Dimension fixedSize = new Dimension(menuPanelWidth, Integer.MAX_VALUE);

		menuPanel.setPreferredSize(fixedSize);
		menuPanel.setMinimumSize(fixedSize);
		menuPanel.setMaximumSize(fixedSize);

		// -------------------------------------------------------------------------------------------------------------------------------------------------------

		headerPanel = new JPanel();
		headerPanel.setLayout(new GridLayout(1, 4));
		headerPanel.setBackground(new Color(82, 194, 34));

		JButton patientButton = sc.createStyledButton("Пациенти", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton doctorButton = sc.createStyledButton("Доктори", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton diseaseButton = sc.createStyledButton("Заболявания", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton medicationButton = sc.createStyledButton("Медикаменти", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton settingsButton = sc.createStyledButton("Настройки", headerColor, hoverBackgroundColor, hoverTextColor);

		// Add buttons to the header panel
		headerPanel.add(patientButton);
		headerPanel.add(doctorButton);
		headerPanel.add(diseaseButton);
		headerPanel.add(medicationButton);
		headerPanel.add(settingsButton);

		// Add the header panel to the JFrame
		add(headerPanel, BorderLayout.NORTH);

		// Create the main work area

		add(mainPanel, FlowLayout.CENTER);

		patientButton.addActionListener(e -> setDbExploitationPage("Пациент"));
		doctorButton.addActionListener(e -> setDbExploitationPage("Доктор"));
		diseaseButton.addActionListener(e -> setDbExploitationPage("Заболяване"));
		medicationButton.addActionListener(e -> setDbExploitationPage("Медикамент"));
		settingsButton.addActionListener(e -> {
			mainPanel = setSetter.setSettingsPage(mainPanel, menuPanel, contentPanel);
			mainPanel.revalidate();
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) { };

	private void initializePanels() {
	        mainPanel = new JPanel();
	        menuPanel = new JPanel();
	        contentPanel = new JPanel();
	        contentButtonPanel = new JPanel();

			setPanels();
	 }

	 private void setPanels() {
		 mainPanel.removeAll();
		 menuPanel.removeAll();
		 contentPanel.removeAll();
		 contentButtonPanel.removeAll();

		 mainPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);
		 mainPanel.setLayout(new GridBagLayout());

		 menuPanel.setBackground(ColorSchemes.MENU_GREEN);

		 contentPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);
		 contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // vertical stack

		 contentButtonPanel.setLayout(new GridLayout(1, 2)); // 1 row, 2 columns
		 contentButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
	 }

	private void setDbExploitationPage(String instance) {
		setPanels();

		// Scrollable contentPanel wrapper
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		// === TOP BUTTONS ===
		insertButton = sc.createStyledButton("Вписване на " + instance, headerColor, hoverBackgroundColor, hoverTextColor);
		deleteButton = sc.createStyledButton("Изтриване на " + instance, headerColor, hoverBackgroundColor, hoverTextColor);

		JComboBox<String> doctorDropBox = new JComboBox<>(doctorOptions);
		doctorDropBox.setPreferredSize(new Dimension(150, 30));
		doctorDropBox.setMaximumSize(doctorDropBox.getPreferredSize());
		doctorDropBox.addActionListener(e -> {
			if (doctorDropBox.getSelectedItem() != null) {
				contentPanel = organizer.sortEntries(doctorDropBox, entries, contentPanel, contentButtonPanel, instance);
				contentPanel.revalidate();
			}
		});
		doctorDropBox.setSelectedItem(null);
		doctorDropBox.setBackground(Color.WHITE);

		JComboBox<String> patDisMedDropBox = new JComboBox<>(patDisMedOptions);
		patDisMedDropBox.setPreferredSize(new Dimension(150, 30));
		patDisMedDropBox.setMaximumSize(patDisMedDropBox.getPreferredSize());
		patDisMedDropBox.addActionListener(e -> {
			if (patDisMedDropBox.getSelectedItem() != null) {
				contentPanel = organizer.sortEntries(patDisMedDropBox, entries,contentPanel, contentButtonPanel, instance);
				contentPanel.revalidate();
			}
		});
		patDisMedDropBox.setSelectedItem(null);
		patDisMedDropBox.setBackground(Color.WHITE);
		menuPanel.add(new JLabel("Сортиране по "));

		switch (instance) {
			case "Пациент":
				menuPanel.add(patDisMedDropBox);
				insertButton.addActionListener(e -> caller.patientInsertion());
				deleteButton.addActionListener(e -> caller.patientDeletion());
				entries = QueryExecutor.getAllPatients();
				break;
			case "Доктор":
				menuPanel.add(doctorDropBox);
				insertButton.addActionListener(e -> caller.doctorInsertion());
				deleteButton.addActionListener(e -> caller.doctorDeletion());
				entries = QueryExecutor.getAllDoctors();
				break;
			case "Заболяване":
				menuPanel.add(patDisMedDropBox);
				insertButton.addActionListener(e -> caller.diseaseInsertion());
				deleteButton.addActionListener(e -> caller.diseaseDeletion());
				entries = QueryExecutor.getAllDiseases();
				break;
			case "Медикамент":
				menuPanel.add(patDisMedDropBox);
				insertButton.addActionListener(e -> caller.medicationInsertion());
				deleteButton.addActionListener(e -> caller.medicationDeletion());
				entries = QueryExecutor.getAllMedications();
				break;
		}

		contentButtonPanel.add(insertButton);
		contentButtonPanel.add(deleteButton);
		contentPanel.add(contentButtonPanel);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		mainPanel.add(menuPanel);

		// ScrollPane with contentPanel - 3/4 of the width
		gbc.gridx = 1;
		gbc.weightx = 0.75;
		mainPanel.add(scrollPane, gbc);

		for (User entry : entries) {
			JPanel entryPanel = new JPanel(new GridBagLayout());
			entryPanel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY),
					BorderFactory.createEmptyBorder(10, 10, 10, 10)
			));
			entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); // consistent height
			entryPanel.setPreferredSize(new Dimension(contentPanel.getWidth(), 120));
			entryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

			GridBagConstraints entriesGbc = new GridBagConstraints();
			entriesGbc.insets = new Insets(2, 0, 2, 2);
			entriesGbc.anchor = GridBagConstraints.EAST;
			entriesGbc.gridx = 0;
			entriesGbc.gridy = 0;

			// Helper method to add label pairs
			BiConsumer<String, String> addLabelPair = (String label, String value) -> {
				entriesGbc.gridx = 0;
				entriesGbc.anchor = GridBagConstraints.EAST;
				entryPanel.add(new JLabel(label), entriesGbc);

				entriesGbc.gridx = 1;
				entriesGbc.anchor = GridBagConstraints.WEST;
				entryPanel.add(new JLabel(value), entriesGbc);

				entriesGbc.gridy++;
			};


			if (entry instanceof Doctor doc) {
				addLabelPair.accept("Код на Доктор:", String.valueOf(doc.getId()));
				addLabelPair.accept("Име на Доктор:", doc.getName());
				addLabelPair.accept("Фамилия на Доктор:", doc.getSurname());
				addLabelPair.accept("Специализация:", doc.getSpecialization());
			} else if (entry instanceof Patient pat) {
				addLabelPair.accept("Код на Пациент:", String.valueOf(pat.getId()));
				addLabelPair.accept("Име на Пациент:", pat.getName());
				addLabelPair.accept("Фамилия на Пациент:", pat.getSurname());
			} else if (entry instanceof Disease dis) {
				addLabelPair.accept("Код на Заболяване:", String.valueOf(dis.getId()));
				addLabelPair.accept("Име на Заболяване:", dis.getName());
				addLabelPair.accept("Описание на Заболяване:", dis.getDescription());
				addLabelPair.accept("Третиране на Заболяване:", dis.getTreatment());
			} else if (entry instanceof Medication med) {
				addLabelPair.accept("Код на Медикамент:", String.valueOf(med.getId()));
				addLabelPair.accept("Име на Медикамент:", med.getName());
				addLabelPair.accept("Описание на Медикамент:", med.getDescription());
				addLabelPair.accept("Дозировка на Медикамент:", med.getDosage());
			}

			contentPanel.add(Box.createVerticalStrut(10));
			contentPanel.add(entryPanel);
		}
		mainPanel.revalidate();
		mainPanel.repaint();
	}
}