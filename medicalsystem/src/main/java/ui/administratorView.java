package ui;

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.border.*;

import database.*;
import customColors.*;
import uiComponents.*;

public class administratorView extends JFrame implements ActionListener{
	
	JFrame frame;
	JPanel mainPanel;
	JPanel headerPanel;
	RoundedJPanel menuPanel;
	RoundedJPanel contentPanel;
	
	static String[] butNames = {"Пациенти", "Лекари", "Болести", "Настройки"};
	static String[] testEntries = {"entry0", "entry1", "entry2", "entry3"};
	
	Color cHeader = new Color(35, 64, 153);
	
	private static final long serialVersionUID = 1L;
	String instance;
	
	public administratorView() {
		this.setTitle("Hello Administrator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(false);
		this.setVisible(true);
		
		ImageIcon icon = new ImageIcon("Medical_emblem.png");
		this.setIconImage(icon.getImage());
		
		//-------------------------------------------------------------------------------------------------------------------------------------------------------
		
		 headerPanel = new JPanel();
	        headerPanel.setLayout(new GridLayout(1, 4));
	        headerPanel.setBackground(new Color(82,194,34));

	        Color headerColor = ColorSchemes.MENU_GREEN;
	        Color hoverBackgroundColor = Color.DARK_GRAY;
	        Color hoverTextColor = Color.WHITE;
	        
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
	        mainPanel.setLayout(new BoxLayout(mainPanel, 2));
	        mainPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);
	        add(mainPanel, FlowLayout.CENTER);

	        // Set action listeners for the buttons
	        // patientButton.addActionListener(e -> updateMainPanel("Patient"));
	        patientButton.addActionListener(e -> setPatientPage());
	        //doctorButton.addActionListener(e -> updateMainPanel("Doctor"));
	        doctorButton.addActionListener(e -> setDoctorPage());
	        diseaseButton.addActionListener(e -> updateMainPanel("Disease"));
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
    	JLabel label = new JLabel("Height: " + mainPanel.getHeight() + " Width: " + mainPanel.getWidth());
    	label.setHorizontalAlignment(SwingConstants.CENTER);
    	mainPanel.add(label, BorderLayout.CENTER);
    	mainPanel.revalidate();
        mainPanel.repaint();
    	
    	for(String entry : testEntries)
    	{
    		JPanel entryPanel = new JPanel();
    		JLabel name = new JLabel(entry);
    		entryPanel.add(name);
    		contentPanel.add(entryPanel);
    	}
    }
    
    private void setDoctorPage() {
    	mainPanel.removeAll();
    	
    	menuPanel = new RoundedJPanel(20, 417 , 661);
    		menuPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    		menuPanel.setBackground(new Color(55, 144, 52));
    		
    	contentPanel = new RoundedJPanel(20, 844, 661);
    		contentPanel.setBackground(Color.WHITE);
    	
        for(String entry : testEntries) {
        		JPanel entryPanel = new JPanel();
        		JLabel name = new JLabel(entry);
        		entryPanel.add(name);
        		contentPanel.add(entryPanel);
        	}
    		
    	mainPanel.add(menuPanel); mainPanel.add(contentPanel);
    	mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void setDiseasePage() {}
    
    private void setSettingsPage() {}
    
}
