package ui;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent; 

import javax.swing.*;

public class patientView extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	String instance;
	
	public patientView() {
		this.setTitle("Hello Patient");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(false);
		this.setVisible(true);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/MASicon.png"));
		this.setIconImage(icon.getImage());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
