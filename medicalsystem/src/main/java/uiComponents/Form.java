package uiComponents;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Form extends JFrame implements ActionListener{

	public Form() {
		this.setTitle("Вход");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/MASicon.png"));
		this.setIconImage(icon.getImage());
		
		this.setResizable(false);
		this.setSize(1200, 390);
		this.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	//Forms used to insert data
	public void doctorInsertion(int code, String name, String surName, String password) {
		
		
		
		
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
