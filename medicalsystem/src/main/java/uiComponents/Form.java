package uiComponents;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import database.CrudExecutor;

public class Form extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -698594458484167312L;

	CrudExecutor executor;
	
	public Form() {
	    this.setTitle("Insert Docotor Data");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    ImageIcon icon = new ImageIcon(getClass().getResource("/images/MASicon.png"));
	    this.setIconImage(icon.getImage());

	    this.setResizable(false);
	    this.setVisible(false);  // Delay visibility until fully built
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	//Forms used to insert data
	public void doctorInsertion() {
		
		this.setVisible(true);
	    
	    JPanel formPanel = new JPanel();
	    formPanel.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 10, 5, 10);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    // Fields
	    JLabel codeLabel = new JLabel("Код:");
	    JTextField codeField = new JTextField(20);

	    JLabel nameLabel = new JLabel("Име:");
	    JTextField nameField = new JTextField(20);

	    JLabel surnameLabel = new JLabel("Фамилия:");
	    JTextField surnameField = new JTextField(20);

	    JLabel passwordLabel = new JLabel("Парола:");
	    JPasswordField passwordField = new JPasswordField(20);

	    // Layout fields
	    gbc.gridx = 0; gbc.gridy = 0;
	    formPanel.add(codeLabel, gbc);
	    gbc.gridx = 1;
	    formPanel.add(codeField, gbc);

	    gbc.gridx = 0; gbc.gridy++;
	    formPanel.add(nameLabel, gbc);
	    gbc.gridx = 1;
	    formPanel.add(nameField, gbc);

	    gbc.gridx = 0; gbc.gridy++;
	    formPanel.add(surnameLabel, gbc);
	    gbc.gridx = 1;
	    formPanel.add(surnameField, gbc);

	    gbc.gridx = 0; gbc.gridy++;
	    formPanel.add(passwordLabel, gbc);
	    gbc.gridx = 1;
	    formPanel.add(passwordField, gbc);

	    // Buttons
	    JButton confirmButton = new JButton("Потвърди");
	    JButton cancelButton = new JButton("Отказ");

	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
	    buttonPanel.add(confirmButton);
	    buttonPanel.add(cancelButton);

	    // Add action listeners if needed
	    confirmButton.addActionListener(e -> {
	        // Your save logic here
	        System.out.println("Submitted: " + nameField.getText());
	    });

	    cancelButton.addActionListener(e -> {
	    	codeField.setText("");
	    	nameField.setText("");
	    	surnameField.setText("");
	    	passwordField.setText("");
	        this.dispose(); // close window
	    });

	    // Add to frame
	    this.setLayout(new BorderLayout());
	    this.add(formPanel, BorderLayout.CENTER);
	    this.add(buttonPanel, BorderLayout.SOUTH);
	    this.revalidate();
	    this.repaint();
	    this.pack();               // Sizes frame to fit its contents
	    this.setLocationRelativeTo(null); // Re-center after packing
	    this.setVisible(true); 
	};
	
	public void specializationInsertion(int code, String name, String description) {
		
		
		this.setVisible(true);
	};
	
	public void patientInsertion(int code, String name, String surName, String password) {
		
		
		this.setVisible(true);
	};
	
	public void illnessInsertion(int code, String name, String description) {
		
		
		this.setVisible(true);
	};
	
	public void medicationInsertion(int code, String name, String description, String dosage) {
		
		
		this.setVisible(true);
	};
	
	public void prescriptionInsertion(int code, int doctorCode, int patientCode, int illnessCode, int medicationCode) {
		
		
		this.setVisible(true);
	};
	
	//Forms used to delete data ------------------------------------------------------------------------------------------
	public void doctorDeletion(int code) {
		
		
		this.setVisible(true);
	};
	
	public void specializationDeletion(int code) {
		
		
		this.setVisible(true);
	};
	
	public void patientDeletion(int code) {
		
		
		this.setVisible(true);
	};
	
	public void illnessDeletion(int code) {
		
		
		this.setVisible(true);
	};
	
	public void prescriptionDeletion(int code) {
		
		
		this.setVisible(true);
	};
	
	//Forms used to correct data
	public void doctorCorrection() {
		
		
		this.setVisible(true);
	};
	
	public void patientCorrection() {
		
		
		this.setVisible(true);
	};
	
	public void illnessCorrection() {
		
		
		this.setVisible(true);
	};
	
	public void prescriptionCorrection() {
		
		
		this.setVisible(true);
	};
}
