package uiComponents;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import database.QueryExecutor;

public class Form extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -698594458484167312L;

	QueryExecutor executor;
	
	public Form() {
	    this.setTitle("Insert Docotor Data");
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
		this.getContentPane().removeAll();
		this.setVisible(true);
	    
	    JPanel formPanel = new JPanel();
	    formPanel.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 10, 5, 10);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    JLabel nameLabel = new JLabel("Име:");
	    JTextField nameField = new JTextField(20);

	    JLabel surnameLabel = new JLabel("Фамилия:");
	    JTextField surnameField = new JTextField(20);

	    JLabel passwordLabel = new JLabel("Парола:");
	    JPasswordField passwordField = new JPasswordField(20);

	    // Layout fields

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
	        QueryExecutor.newDoctor(nameField.getText(), surnameField.getText(), passwordField.getPassword().toString());
	    });

	    cancelButton.addActionListener(e -> {
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
	
	public void specializationInsertion() {
		
		
		this.setVisible(true);
	};
	
	public void patientInsertion() {
		
		
		this.setVisible(true);
	};
	
	public void diseaseInsertion() {
		
		
		this.setVisible(true);
	};
	
	public void medicationInsertion() {
		
		
		this.setVisible(true);
	};
	
	public void prescriptionInsertion() {
		
		
		this.setVisible(true);
	};
	
	//Forms used to delete data ------------------------------------------------------------------------------------------
	
	public void doctorDeletion() {
		this.getContentPane().removeAll();
		this.setVisible(true);
	    
	    JPanel formPanel = new JPanel();
	    formPanel.removeAll();
	    formPanel.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 10, 5, 10);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    JLabel codeLabel = new JLabel("Код:");
	    JTextField codeField = new JTextField(20);

	    // Layout fields

	    gbc.gridx = 0; gbc.gridy++;
	    formPanel.add(codeLabel, gbc);
	    gbc.gridx = 1;
	    formPanel.add(codeField, gbc);

	    // Buttons
	    JButton confirmButton = new JButton("Потвърди");
	    JButton cancelButton = new JButton("Отказ");

	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
	    buttonPanel.add(confirmButton);
	    buttonPanel.add(cancelButton);

	    // Add action listeners if needed
	    confirmButton.addActionListener(e -> {
	        // Your save logic here
	        System.out.println("Submitted: " + codeField.getText());
	    });

	    cancelButton.addActionListener(e -> {
	    	codeField.setText("");
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
	
	public void specializationDeletion() {
		
		
		this.setVisible(true);
	};
	
	public void patientDeletion() {
		
		
		this.setVisible(true);
	};
	
	public void diseaseDeletion() {
		
		
		this.setVisible(true);
	};
	
	public void prescriptionDeletion() {
		
		
		this.setVisible(true);
	};
	
	//Forms used to correct data
	public void doctorCorrection() {
		
		
		this.setVisible(true);
	};
	
	public void patientCorrection() {
		
		
		this.setVisible(true);
	};
	
	public void diseaseCorrection() {
		
		
		this.setVisible(true);
	};
	
	public void medicationDeletion() {
		
		
		this.setVisible(true);
	};
	
	public void prescriptionCorrection() {
		
		
		this.setVisible(true);
	};
}
