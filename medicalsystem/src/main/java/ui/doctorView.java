package ui;

import java.io.Serial;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.function.BiConsumer;
import javax.swing.*;

import backendFunctionalities.ContentOrganizer;
import com.mysql.cj.QueryResult;
import customColors.*;
import com.example.model.*;
import database.QueryExecutor;
import uiComponents.Form;
import uiComponents.SettingsSetter;
import uiComponents.StyledComponents;
import uiComponents.UserPage;

public class doctorView extends JFrame implements ActionListener {
	@Serial
	private static final long serialVersionUID = 2L;

	Doctor doc;

	JPanel mainPanel, headerPanel, menuPanel, contentPanel, contentButtonPanel;
	StyledComponents sc;
	SettingsSetter setSetter;
	UserPage up;
	Color headerColor = ColorSchemes.MENU_GREEN, hoverBackgroundColor = Color.DARK_GRAY, hoverTextColor = Color.WHITE,
		  contentColor = ColorSchemes.BACKGROUND_BEIGE;
	Form caller;
	ContentOrganizer organizer;

	static ArrayList<com.example.model.User> entries;
	final private String[] doctorOptions = { "Код", "Име", "Специализация" };
	final private String[] patDisMedOptions = {"Код", "Име"};

	public doctorView(int code, char[] password) throws SQLException {
		String strPassword = String.valueOf(password);
		doc = QueryExecutor.getDoctorByCodePassword(code, strPassword);

		this.setTitle("Здравейте Доктор: " + doc.getName() + " " + doc.getSurname());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(false);
		this.setVisible(true);

		ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/MASicon.png")));
		this.setIconImage(icon.getImage());

		initializePanels();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;

		int menuPanelWidth = (int) (screenWidth * 0.3);
		Dimension fixedSize = new Dimension(menuPanelWidth, Integer.MAX_VALUE);

		menuPanel.setPreferredSize(fixedSize);
		menuPanel.setMinimumSize(fixedSize);

		sc = new StyledComponents();
		setSetter = new SettingsSetter();
		up = new UserPage();
		caller = new Form();
		organizer = new ContentOrganizer();

		headerPanel = new JPanel();
		headerPanel.setLayout(new GridLayout(1, 0));

		JButton patientButton = sc.createStyledButton("Пациенти", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton prescriptionButton = sc.createStyledButton("Рецепти", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton referralButton = sc.createStyledButton("Направления", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton appointmentButton = sc.createStyledButton("Часове", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton diseaseButton = sc.createStyledButton("Болести", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton medicationButton = sc.createStyledButton("Медикаменти", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton settingsButton = sc.createStyledButton("Настройки", headerColor, hoverBackgroundColor, hoverTextColor);

		patientButton.addActionListener(e -> {
			try {
				setDbExploitationPage("Пациенти");
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		});
		prescriptionButton.addActionListener(e -> {
            try {
                setDbExploitationPage("Рецепта");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
		referralButton.addActionListener(e -> {
            try {
                setDbExploitationPage("Направление");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
		medicationButton.addActionListener(e -> {
            try {
                setDbExploitationPage("Медикаменти");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
		diseaseButton.addActionListener(e -> {
            try {
                setDbExploitationPage("Заболяване");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
		medicationButton.addActionListener(e -> {
            try {
                setDbExploitationPage("Медикаменти");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

		appointmentButton.addActionListener(e -> setCalendarPanel("Доктор"));
		settingsButton.addActionListener(e -> {
			mainPanel = setSetter.setSettingsPage(mainPanel, menuPanel, contentPanel);
			mainPanel.revalidate();
		});

		headerPanel.add(patientButton);
		headerPanel.add(prescriptionButton);
		headerPanel.add(referralButton);
		headerPanel.add(appointmentButton);
		headerPanel.add(diseaseButton);
		headerPanel.add(medicationButton);
		headerPanel.add(settingsButton);

		add(headerPanel, BorderLayout.NORTH);
		add(mainPanel, FlowLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	private void initializePanels() {
		mainPanel = new JPanel();
		mainPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);
		mainPanel.setLayout(new GridBagLayout());

		menuPanel = new JPanel();
		menuPanel.setBackground(ColorSchemes.MENU_GREEN);
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		menuPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

		contentPanel = new JPanel();
		contentPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // vertical stack

		contentButtonPanel = new JPanel();
		contentButtonPanel.setLayout(new GridLayout(1, 2)); // 1 row, 2 columns
		contentButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
	}

	private void setDbExploitationPage(String instance) throws SQLException {
		mainPanel.removeAll();
		menuPanel.removeAll();
		contentPanel.removeAll();
		contentButtonPanel.removeAll();

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // vertical stack

		// Scrollable contentPanel wrapper
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);  // smoother scrolling

		// === TOP BUTTONS ===
		JButton insertButton = sc.createStyledButton("Издаване на: " + instance, headerColor, hoverBackgroundColor, hoverTextColor);

		JComboBox<String> doctorDropBox = new JComboBox<>(doctorOptions);
		doctorDropBox.setPreferredSize(new Dimension(150, 30));
		doctorDropBox.setMaximumSize(doctorDropBox.getPreferredSize());
		doctorDropBox.addActionListener(e -> {
			if (doctorDropBox.getSelectedItem() != null) {
				contentPanel = organizer.sortEntries(doctorDropBox, entries, contentPanel, instance);
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
				contentPanel = organizer.sortEntries(patDisMedDropBox, entries, contentPanel, instance);
				contentPanel.revalidate();
			}
		});

		patDisMedDropBox.setSelectedItem(null);
		patDisMedDropBox.setBackground(Color.WHITE);
		menuPanel.add(new JLabel("Соритране по:"));

		switch (instance) {
			case "Пациенти":
				mainPanel.removeAll();
				menuPanel.add(patDisMedDropBox);
				entries = QueryExecutor.getPatientsByDoctorCode(doc.getCode());
				break;
			case "Рецепта":
				mainPanel.removeAll();
				entries = QueryExecutor.getAllPrescriptions();
				insertButton.addActionListener(e -> caller.prescriptionForm(doc));
				contentButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				contentButtonPanel.add(insertButton);
				contentPanel.add(contentButtonPanel);
				break;
			case "Направление":
				mainPanel.removeAll();
				entries = QueryExecutor.getAllReferrals();
				insertButton.addActionListener(e -> caller.referralForm(doc));
				contentButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				contentButtonPanel.add(insertButton);
				contentPanel.add(contentButtonPanel);
				break;
			case "Заболяване":
				menuPanel.add(patDisMedDropBox);
				mainPanel.removeAll();
				entries = QueryExecutor.getAllDiseases();
				contentPanel.add(contentButtonPanel);
				break;
			case "Медикаменти":
				menuPanel.add(patDisMedDropBox);
				mainPanel.removeAll();
				entries = QueryExecutor.getAllMedications();
				break;
		}


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
					BorderFactory.createLineBorder(Color.LIGHT_GRAY),  // Dark border to match wireframe
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

			BiConsumer<String, String> addLabelPair = (String label, String value) -> {
				entriesGbc.gridx = 0;
				entriesGbc.anchor = GridBagConstraints.LINE_END; // Label aligned right
				entryPanel.add(new JLabel(label), entriesGbc);

				entriesGbc.gridx = 1;
				entriesGbc.anchor = GridBagConstraints.LINE_START; // Value aligned left
				entryPanel.add(new JLabel(value), entriesGbc);

				entriesGbc.gridy++;
			};

			if (entry instanceof Patient pat) {
				addLabelPair.accept("Код на пациента:", String.valueOf(pat.getId()));
				addLabelPair.accept("Име на пациента:", pat.getName());
				addLabelPair.accept("Фамилия на пациента:", pat.getSurname());
			} else if (entry instanceof Disease dis) {
				addLabelPair.accept("Код на заболяване:", String.valueOf(dis.getId()));
				addLabelPair.accept("Име на заболяване:", dis.getName());
				addLabelPair.accept("Описание на заболяване:", dis.getDescription());
				addLabelPair.accept("Метод за третирана на заболяване:", dis.getTreatment());
			} else if (entry instanceof Medication med) {
				addLabelPair.accept("Код на медикамент:", String.valueOf(med.getId()));
				addLabelPair.accept("Име на медикамент:", med.getName());
				addLabelPair.accept("Описание на медикамент:", med.getDescription());
				addLabelPair.accept("Дозировка на медикамент:", med.getDosage());
			} else if (entry instanceof Prescription pres) {
				addLabelPair.accept("Код на рецепта: ", String.valueOf(pres.getCode()));
				addLabelPair.accept("Код на доктор: ", String.valueOf(pres.getDocCode()));
				addLabelPair.accept("Код на пациент: ", String.valueOf(pres.getPatCode()));
				addLabelPair.accept("Код на медикамент: ", String.valueOf(pres.getMedCode()));
			} else if (entry instanceof Referral ref) {
				addLabelPair.accept("Код на направление: ", String.valueOf(ref.getCode()));
				addLabelPair.accept("Код на доктор: ", String.valueOf(ref.getDocCode()));
				addLabelPair.accept("Код на пациент: ", String.valueOf(ref.getPatCode()));
				addLabelPair.accept("Код на описание: ", String.valueOf(ref.getDescription()));
			}

			contentPanel.add(Box.createVerticalStrut(10)); // space between panels
			contentPanel.add(entryPanel);
		}

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setCalendarPanel(String instance) {
		// Clear previous content
		mainPanel.removeAll();
		menuPanel.removeAll();
		contentPanel.removeAll();
		ArrayList<Appointment> appointments = QueryExecutor.getAppointmentsByDoctorCode(doc.getCode());
		// Setup doctor info panel (menuPanel)
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		menuPanel.setPreferredSize(new Dimension(200, 0));
		menuPanel.setBackground(ColorSchemes.MENU_GREEN);

		menuPanel.add(Box.createVerticalStrut(10));
		menuPanel.add(new JLabel("Доктор: " + doc.getName() + " " + doc.getSurname()));
		menuPanel.add(new JLabel(doc.getSpecialization()));
		menuPanel.add(Box.createVerticalStrut(20));

		// Setup calendar grid in contentPanel
		JPanel calendarGrid = new JPanel(new GridLayout(0, 7, 5, 5)); // 7 columns
		calendarGrid.setBackground(ColorSchemes.BACKGROUND_BEIGE);
		YearMonth currentMonth = YearMonth.now();
		int daysInMonth = currentMonth.lengthOfMonth();
		LocalDate today = LocalDate.now();

		for (int day = 1; day <= daysInMonth; day++) {
			LocalDate date = LocalDate.of(today.getYear(), today.getMonth(), day);
			JButton dayButton = new JButton(String.valueOf(day));
			dayButton.setFocusPainted(false);

			if (date.isBefore(today)) {
				// Past date: disable and gray out
				dayButton.setEnabled(false);
				dayButton.setBackground(Color.LIGHT_GRAY);
			} else {
				// Future or today: clickable
				dayButton.setBackground(Color.WHITE);
				dayButton.addActionListener(e -> caller.setAppointment(date, doc, appointments));
			}
			calendarGrid.add(dayButton);
		}

		calendarGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(calendarGrid, BorderLayout.CENTER);

		// Add panels to mainPanel using GridBagLayout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		// Menu panel (left) - fixed width (already set in constructor)
		gbc.gridx = 0;
		gbc.weightx = 0; // No extra space, we're using fixed width
		mainPanel.add(menuPanel, gbc);

		// Content panel (calendar + chat button) - takes remaining space
		gbc.gridx = 1;
		gbc.weightx = 1.0; // Takes all remaining space
		mainPanel.add(contentPanel, gbc);

		// Refresh UI
		mainPanel.revalidate();
		mainPanel.repaint();
	}


	private void openDayFrame(LocalDate date) {
		JFrame dayFrame = new JFrame("Ден: " + date);
		dayFrame.setSize(300, 200);
		dayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dayFrame.add(new JLabel("Детайли за: " + date, SwingConstants.CENTER));
		dayFrame.setVisible(true);
	}

	private void setSettingsPage() {
		menuPanel.setBackground(ColorSchemes.MENU_GREEN);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // vertical stack
		contentPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		// MenuPanel - 1/3 of the width
		gbc.gridx = 0;
		gbc.weightx = 0.25;
		mainPanel.add(menuPanel, gbc);

		// ScrollPane with contentPanel - 2/3 of the width
		gbc.gridx = 1;
		gbc.weightx = 0.75;
		mainPanel.add(contentPanel, gbc);


		mainPanel.revalidate();
		mainPanel.repaint();
	}
}