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
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(1200, 390);
		this.setVisible(false);
		
		ImageIcon icon = new ImageIcon("Medical_emblem.png");
		this.setIconImage(icon.getImage());

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	//Forms used to insert data
	public void doctorInsertion() {
		
		
		this.setVisible(true);
	};
	
	public void patientInsertion() {
		
		
		this.setVisible(true);
	};
	
	public void illnessInsertion() {
		
		
		this.setVisible(true);
	};
	
	public void prescriptionInsertion() {
		
		
		this.setVisible(true);
	};
	
	//Forms used to delete data
	public void doctorDeletion() {
		
		
		this.setVisible(true);
	};
	
	public void patientDeletion() {
		
		
		this.setVisible(true);
	};
	
	public void illnessDeletion() {
		
		
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
	
	public void illnessCorrection() {
		
		
		this.setVisible(true);
	};
	
	public void prescriptionCorrection() {
		
		
		this.setVisible(true);
	};
}
