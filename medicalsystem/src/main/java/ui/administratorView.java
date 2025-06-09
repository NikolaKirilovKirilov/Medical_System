package ui;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import customColors.*;
import com.example.model.*;
import database.QueryExecutor;
import uiComponents.Form;

public class administratorView extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	JFrame frame;
	JPanel  mainPanel, headerPanel, menuPanel, contentPanel, contentButtonPanel;
    Color headerColor = ColorSchemes.MENU_GREEN, hoverBackgroundColor = Color.DARK_GRAY, hoverTextColor = Color.WHITE;
    
    Form caller;
    
    static ArrayList<User> entries;
	static String[] testEntries = {"entry0", "entry1", "entry2", "entry3", "entry4", "entry5", "entry6", "entry7", "entry8", "entry9", "entry10", "entry11"};
	private String[] doctorFilters = {"By ID", "By Name", "By Specialization"};
	
	
	public administratorView() {
		this.setTitle("Hello Administrator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(false);
		this.setVisible(true);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/MASicon.png"));
		this.setIconImage(icon.getImage());
		
		initializePanels();
		
		caller = new Form();
		//-------------------------------------------------------------------------------------------------------------------------------------------------------

		
		 headerPanel = new JPanel();
	        headerPanel.setLayout(new GridLayout(1, 4));
	        headerPanel.setBackground(new Color(82,194,34));
	        
	        JButton patientButton = createStyledButton("Patient", headerColor, hoverBackgroundColor, hoverTextColor);
	        JButton doctorButton = createStyledButton("Doctor", headerColor, hoverBackgroundColor, hoverTextColor);
	        JButton diseaseButton = createStyledButton("Disease", headerColor, hoverBackgroundColor, hoverTextColor);
	        JButton medicationButton = createStyledButton("Medication", headerColor, hoverBackgroundColor, hoverTextColor);
	        JButton settingsButton = createStyledButton("Settings", headerColor, hoverBackgroundColor, hoverTextColor);

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

	        // Set action listeners for the buttons
	        // patientButton.addActionListener(e -> updateMainPanel("Patient"));
	        
	        /*
	        patientButton.addActionListener(e -> setPatientPage());
	        doctorButton.addActionListener(e -> setDoctorPage());
	        diseaseButton.addActionListener(e -> setDiseasePage());
	        medicationButton.addActionListener(e -> setMedicationPage());
	        settingsButton.addActionListener(e -> updateMainPanel("Settings"));
	        */
	        patientButton.addActionListener(e -> setDbExplotationPage("Patient"));
	        doctorButton.addActionListener(e -> setDbExplotationPage("Doctor"));
	        diseaseButton.addActionListener(e -> setDbExplotationPage("Disease"));
	        medicationButton.addActionListener(e -> setDbExplotationPage("Medicine"));
	        settingsButton.addActionListener(e ->  setSettingsPage());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	 private JButton createStyledButton(String text, Color backgroundColor, Color hoverBackgroundColor, Color hoverTextColor) {
	        JButton button = new JButton(text);
	        button.setBackground(backgroundColor);
	        button.setForeground(Color.BLACK);
	        button.setBorderPainted(false);
	        button.setFocusPainted(false);
	        button.setOpaque(true);
	        //                 .,/
	        button.setFont(new Font("Arial", Font.BOLD, 14));

	        // Add hover effects
	        button.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                button.setBackground(hoverBackgroundColor);
	                button.setForeground(hoverTextColor);
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                button.setBackground(backgroundColor);
	                button.setForeground(Color.BLACK);
	            }
	        });

	        return button;
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
	 
    private void setDbExplotationPage(String instance) {
    	mainPanel.removeAll();
        menuPanel.removeAll();
        contentPanel.removeAll();
        contentButtonPanel.removeAll();

        // Scrollable contentPanel wrapper
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);  // smoother scrolling

        // Example control in menuPanel
        JComboBox<String> filters = new JComboBox<>(doctorFilters);
        filters.setPreferredSize(new Dimension(150, 30));
        menuPanel.add(new JLabel("Sort by:"));
        menuPanel.add(filters);
        
        // === TOP BUTTONS ===
        JButton insertButton = createStyledButton("Insert " + instance, headerColor, hoverBackgroundColor, hoverTextColor);
        JButton deleteButton = createStyledButton("Delete " + instance, headerColor, hoverBackgroundColor, hoverTextColor);
        
        switch(instance) {
        	case"Patient":
        		insertButton.addActionListener(e -> caller.patientInsertion());
        		deleteButton.addActionListener(e -> caller.patientDeletion());
        	case"Doctor":
        		insertButton.addActionListener(e -> caller.doctorInsertion());
        		deleteButton.addActionListener(e -> caller.doctorDeletion());
        		entries = QueryExecutor.getAllDoctors();
        		System.out.println(entries.get(1).getName());
        	case"Disease":
        		insertButton.addActionListener(e -> caller.diseaseInsertion());
        		deleteButton.addActionListener(e -> caller.diseaseDeletion());
        	case"Medication":
        		insertButton.addActionListener(e -> caller.medicationInsertion());
        		deleteButton.addActionListener(e -> caller.medicationDeletion());
        }
        
        contentButtonPanel.add(insertButton);
        contentButtonPanel.add(deleteButton);
        contentPanel.add(contentButtonPanel);

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
    	
        // Add equal-height entries
        	for (User entry : entries) {
            	JPanel entryPanel = new JPanel();
            	entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300)); // fixed height
            	entryPanel.setPreferredSize(new Dimension(400, 300));
            	entryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            	entryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            	entryPanel.add(new JLabel("Doctor's ID: " + entry.getId()));
            	entryPanel.add(new JLabel("Doctor's Name: " + entry.getName()));
            	entryPanel.add(new JLabel("Doctor's Surname: " + entry.getSurname()));
            	entryPanel.add(new JLabel("Doctor's Sepcialization: " + entry.getspecialization()));
            	contentPanel.add(entryPanel);
        	}

        mainPanel.revalidate();
        mainPanel.repaint();    
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