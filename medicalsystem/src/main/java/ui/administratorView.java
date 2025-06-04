package ui;

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import customColors.*;
import uiComponents.Form;

public class administratorView extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	JFrame frame;
	JPanel headerPanel, mainPanel, menuPanel, contentPanel;
    Color headerColor = ColorSchemes.MENU_GREEN, hoverBackgroundColor = Color.DARK_GRAY, hoverTextColor = Color.WHITE;
    
    Form caller;
    
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
		
		caller = new Form();
		//-------------------------------------------------------------------------------------------------------------------------------------------------------

		
		 headerPanel = new JPanel();
	        headerPanel.setLayout(new GridLayout(1, 4));
	        headerPanel.setBackground(new Color(82,194,34));
	        
	        JButton patientButton = createStyledButton("Patient", headerColor, hoverBackgroundColor, hoverTextColor);
	        JButton doctorButton = createStyledButton("Doctor", headerColor, hoverBackgroundColor, hoverTextColor);
	        JButton diseaseButton = createStyledButton("Disease", headerColor, hoverBackgroundColor, hoverTextColor);
	        JButton settingsButton = createStyledButton("Settings", headerColor, hoverBackgroundColor, hoverTextColor);

	        // Add buttons to the header panel
	        headerPanel.add(patientButton);
	        headerPanel.add(doctorButton);
	        headerPanel.add(diseaseButton);
	        headerPanel.add(settingsButton);

	        // Add the header panel to the JFrame
	        add(headerPanel, BorderLayout.NORTH);

	        // Create the main work area
	        mainPanel = new JPanel();
	        mainPanel.setLayout(new GridLayout(1, 2, 10, 0));
	        mainPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);
	        add(mainPanel, FlowLayout.CENTER);

	        // Set action listeners for the buttons
	        // patientButton.addActionListener(e -> updateMainPanel("Patient"));
	        patientButton.addActionListener(e -> setPatientPage());
	        //doctorButton.addActionListener(e -> updateMainPanel("Doctor"));
	        doctorButton.addActionListener(e -> setDoctorPage());
	        diseaseButton.addActionListener(e -> setDiseasePage());
	        settingsButton.addActionListener(e -> updateMainPanel("Settings"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
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
	
    private void updateMainPanel(String section) {
        // Clear the main panel
        mainPanel.removeAll();

        // Add a label to display the selected section
        JLabel label = new JLabel("Избрахте: " + section);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 24));

        mainPanel.add(label, BorderLayout.CENTER);

        // Refresh the main panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void setHomePage() {}
    
    private void setPatientPage() {
    	
    	mainPanel.removeAll();
        mainPanel.setLayout(new GridBagLayout());

        // Panels
        menuPanel = new JPanel();
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // vertical stack

        menuPanel.setBackground(ColorSchemes.MENU_GREEN);
        contentPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);

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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0)); // 1 row, 2 columns, 10px horizontal gap
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JButton insertButton = createStyledButton("Insert Patient", headerColor, hoverBackgroundColor, hoverTextColor);
        JButton deleteButton = createStyledButton("Delete Patient", headerColor, hoverBackgroundColor, hoverTextColor);

        buttonPanel.add(insertButton);
        buttonPanel.add(deleteButton);

        contentPanel.add(Box.createVerticalStrut(10)); // top spacing
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalStrut(10)); // spacing after buttons

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
        mainPanel.add(scrollPane, gbc);

        // Add equal-height entries
        for (String entry : testEntries) {
            JPanel entryPanel = new JPanel();
            entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300)); // fixed height
            entryPanel.setPreferredSize(new Dimension(400, 300));
            entryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            entryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            entryPanel.add(new JLabel(entry));
            contentPanel.add(entryPanel);
        }

        mainPanel.revalidate();
        mainPanel.repaint();    
    }
    
    
    private void setDoctorPage() {
    	mainPanel.removeAll();
        mainPanel.setLayout(new GridBagLayout());

        // Panels
        menuPanel = new JPanel();
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // vertical stack

        menuPanel.setBackground(ColorSchemes.MENU_GREEN);
        contentPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);

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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0)); // 1 row, 2 columns, 10px horizontal gap
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JButton insertButton = createStyledButton("Insert Doctor", headerColor, hoverBackgroundColor, hoverTextColor);
        insertButton.addActionListener(e -> caller.doctorInsertion());
        
        JButton deleteButton = createStyledButton("Delete Doctor", headerColor, hoverBackgroundColor, hoverTextColor);

        buttonPanel.add(insertButton);
        buttonPanel.add(deleteButton);

        contentPanel.add(Box.createVerticalStrut(10)); // top spacing
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalStrut(10)); // spacing after buttons

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
        mainPanel.add(scrollPane, gbc);

        // Add equal-height entries
        for (String entry : testEntries) {
            JPanel entryPanel = new JPanel();
            entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300)); // fixed height
            entryPanel.setPreferredSize(new Dimension(400, 300));
            entryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            entryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            entryPanel.add(new JLabel(entry));
            contentPanel.add(entryPanel);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void setDiseasePage() {
    	
    	mainPanel.removeAll();
        mainPanel.setLayout(new GridBagLayout());

        // Panels
        menuPanel = new JPanel();
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // vertical stack

        menuPanel.setBackground(ColorSchemes.MENU_GREEN);
        contentPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);

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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0)); // 1 row, 2 columns, 10px horizontal gap
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JButton insertButton = createStyledButton("Insert Disease", headerColor, hoverBackgroundColor, hoverTextColor);
        insertButton.addActionListener(filters);
        
        JButton deleteButton = createStyledButton("Delete Disease", headerColor, hoverBackgroundColor, hoverTextColor);

        
        
        buttonPanel.add(insertButton);
        buttonPanel.add(deleteButton);

        contentPanel.add(Box.createVerticalStrut(10)); // top spacing
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalStrut(10)); // spacing after buttons

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
        mainPanel.add(scrollPane, gbc);

        // Add equal-height entries
        for (String entry : testEntries) {
            JPanel entryPanel = new JPanel();
            entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300)); // fixed height
            entryPanel.setPreferredSize(new Dimension(400, 300));
            entryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            entryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            entryPanel.add(new JLabel(entry));
            contentPanel.add(entryPanel);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void setSettingsPage() {}
    
}
