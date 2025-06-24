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

public class patientView extends JFrame implements ActionListener {
	@Serial
	private static final long serialVersionUID = 2L;

	Patient patient;
	JPanel mainPanel, headerPanel, menuPanel, contentPanel;
	StyledComponents sc;
	Color headerColor = ColorSchemes.MENU_GREEN, hoverBackgroundColor = Color.DARK_GRAY, hoverTextColor = Color.WHITE,
			contentColor = ColorSchemes.BACKGROUND_BEIGE;
	Form caller;

	static ArrayList<com.example.model.User> entries;
	final private String[] doctorOptions = { "Код", "Име", "Специализация" };

	public patientView(String name, char[] password) throws SQLException {
		String strPassword = String.valueOf(password);
		patient = QueryExecutor.getPatientByNamePassword(name, strPassword);

		this.setTitle("Здравейте " + patient.getName() + " " + patient.getSurname());
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

		JButton doctorButton = sc.createStyledButton("Доктори", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton appointmentButton = sc.createStyledButton("Часове", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton prescriptionsButton = sc.createStyledButton("Вашите Рецепти", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton referralButton = sc.createStyledButton("Вашите Направления", headerColor, hoverBackgroundColor, hoverTextColor);
		JButton settingsButton = sc.createStyledButton("Настройки", headerColor, hoverBackgroundColor, hoverTextColor);

		headerPanel.add(doctorButton);
		headerPanel.add(appointmentButton);
		headerPanel.add(prescriptionsButton);
		headerPanel.add(referralButton);
		headerPanel.add(settingsButton);

		add(headerPanel, BorderLayout.NORTH);
		add(mainPanel, FlowLayout.CENTER);

		doctorButton.addActionListener(e -> setDbExploitationPage("Доктори"));
		appointmentButton.addActionListener(e -> setCalendarPanel("Пациент"));
		prescriptionsButton.addActionListener(e -> setDbExploitationPage("Рецепти"));
		referralButton.addActionListener(e -> setDbExploitationPage("Направления"));
		settingsButton.addActionListener(e -> setSettingsPage());
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

		contentPanel = new JPanel();
		contentPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // vertical stack
	}

	private void setDbExploitationPage(String instance) {
		mainPanel.removeAll();
		menuPanel.removeAll();
		contentPanel.removeAll();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // vertical stack

		// Scrollable contentPanel wrapper
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);  // smoother scrolling

		JComboBox<String> sortBox = new JComboBox<>(doctorOptions);
		sortBox.setMaximumSize(new Dimension(150, 30));
		sortBox.setPreferredSize(new Dimension(150, 30));
		menuPanel.add(new JLabel("Соритране по:"));
		menuPanel.add(sortBox);

		switch (instance) {
			case "Доктори":
				entries = QueryExecutor.getAllDoctors();
				break;
			case "Рецепти":
				entries = QueryExecutor.getAllPrescriptions();
				break;
			case "Направления":
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

			if (entry instanceof Doctor doc) {
				addLabelPair.accept("Код на Доктор:", String.valueOf(doc.getId()));
				addLabelPair.accept("Име на Доктор:", doc.getName());
				addLabelPair.accept("Фамилия на Доктор:", doc.getSurname());
				addLabelPair.accept("Специализация:", doc.getSpecialization());
			} else if (entry instanceof Prescription prs) {
				addLabelPair.accept("Код на Рецептата:", String.valueOf(prs.getCode()));
				addLabelPair.accept("Код на Доктор:", String.valueOf(prs.getDocCode()));
				addLabelPair.accept("Код на Пациент:", String.valueOf(prs.getPatCode()));
				addLabelPair.accept("Код на заболяване:", String.valueOf(prs.getIllCode()));
				addLabelPair.accept("Код на Медикамент:", String.valueOf(prs.getMedCode()));
			} else if (entry instanceof Referral ref) {
				addLabelPair.accept("Код на Направлението:", String.valueOf(ref.getCode()));
				addLabelPair.accept("Код на Пациента:", String.valueOf(ref.getPatCode()));
				addLabelPair.accept("Описание на медикамент:", ref.getDescription());
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