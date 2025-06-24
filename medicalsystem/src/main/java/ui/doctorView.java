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
import customColors.*;
import com.example.model.*;
import database.QueryExecutor;
import uiComponents.Form;
import uiComponents.StyledComponents;

public class doctorView extends JFrame implements ActionListener {
	@Serial
	private static final long serialVersionUID = 2L;

	Doctor doc;

	JPanel mainPanel, headerPanel, menuPanel, contentPanel, contentButtonPanel;
	StyledComponents sc;
	Color headerColor = ColorSchemes.MENU_GREEN, hoverBackgroundColor = Color.DARK_GRAY, hoverTextColor = Color.WHITE,
		  contentColor = ColorSchemes.BACKGROUND_BEIGE;
	Form caller;

	static ArrayList<com.example.model.User> entries;
	final private String[] doctorOptions = { "Код", "Име", "Специализация" };

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

		sc = new StyledComponents();
		caller = new Form();

		headerPanel = new JPanel();
		headerPanel.setLayout(new GridLayout(1, 5));
		headerPanel.setBackground(new Color(82, 194, 34));

		JButton patientButton = sc.createStyledButton("Пациенти", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton prescriptionButton = sc.createStyledButton("Рецепти", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton referralButton = sc.createStyledButton("Направления", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton appointmentButton = sc.createStyledButton("Часове", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton diseaseButton = sc.createStyledButton("Болести", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton medicationButton = sc.createStyledButton("Медикаменти", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton settingsButton = sc.createStyledButton("Настройки", headerColor, hoverBackgroundColor, hoverTextColor);

		patientButton.addActionListener(e -> setDbExploitationPage("Пациент"));
		prescriptionButton.addActionListener(e -> setDbExploitationPage("Рецепта"));
		referralButton.addActionListener(e -> setDbExploitationPage("Направление"));
		medicationButton.addActionListener(e -> setDbExploitationPage("Медикаменти"));
		diseaseButton.addActionListener(e -> setDbExploitationPage("Заболяване"));
		medicationButton.addActionListener(e -> setDbExploitationPage("Медикаменти"));
		appointmentButton.addActionListener(e -> setCalendarPanel("Доктор"));
		settingsButton.addActionListener(e -> setSettingsPage());

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

		contentPanel = new JPanel();
		contentPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // vertical stack

		contentButtonPanel = new JPanel();
		contentButtonPanel.setLayout(new GridLayout(1, 2)); // 1 row, 2 columns
		contentButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
	}

	private void setDbExploitationPage(String instance) {
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
		JButton insertButton = sc.createStyledButton("Записване на: " + instance, headerColor, hoverBackgroundColor, hoverTextColor);
		JButton deleteButton = sc.createStyledButton("Изтриване на: " + instance, headerColor, hoverBackgroundColor, hoverTextColor);

		JComboBox<String> sortBox = new JComboBox<>(doctorOptions);
		sortBox.setMaximumSize(new Dimension(150, 30));
		sortBox.setPreferredSize(new Dimension(150, 30));
		menuPanel.add(new JLabel("Соритране по:"));
		menuPanel.add(sortBox);

		switch (instance) {
			case "Пациент":
				entries = QueryExecutor.getAllPatients();
				break;
			case "Рецепта":
				entries = QueryExecutor.getAllPrescriptions();
				contentButtonPanel.add(insertButton);
				contentPanel.add(contentButtonPanel);
				break;
			case "Направление":
				entries = QueryExecutor.getAllReferrals();
				contentButtonPanel.add(insertButton);
				contentPanel.add(contentButtonPanel);
				break;
			case "Заболяване":
				entries = QueryExecutor.getAllDiseases();
				contentPanel.add(contentButtonPanel);
				break;
			case "Медикаменти":
				entries = QueryExecutor.getAllMedications();
				break;
		}

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		// MenuPanel - 1/4 of the width
		gbc.gridx = 0;
		gbc.weightx = 0.25;
		mainPanel.add(menuPanel, gbc);

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
			entryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

			GridBagConstraints entriesGbc = new GridBagConstraints();
			entriesGbc.insets = new Insets(5, 5, 5, 5);  // More vertical spacing
			entriesGbc.anchor = GridBagConstraints.LINE_START;
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

		// Setup doctor info panel (menuPanel)
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		menuPanel.setPreferredSize(new Dimension(200, 0));
		menuPanel.setBackground(ColorSchemes.MENU_GREEN);

		JLabel doctorImage = new JLabel("Снимка на личния лекар", SwingConstants.CENTER);
		doctorImage.setPreferredSize(new Dimension(180, 180));
		doctorImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		doctorImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		menuPanel.add(Box.createVerticalStrut(10));
		menuPanel.add(doctorImage);

		menuPanel.add(Box.createVerticalStrut(10));
		menuPanel.add(new JLabel("Име на личния лекар"));
		menuPanel.add(new JLabel("Специализация"));
		menuPanel.add(Box.createVerticalStrut(20));
		menuPanel.add(new JLabel("Допълнителни данни"));

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
				dayButton.addActionListener(e -> caller.setAppointment(date, instance));
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

		// Menu panel (left)
		gbc.gridx = 0;
		gbc.weightx = 0.25;
		mainPanel.add(menuPanel, gbc);

		// Content panel (calendar + chat button)
		gbc.gridx = 1;
		gbc.weightx = 0.75;
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