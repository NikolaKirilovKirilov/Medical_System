package uiComponents;

import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

import database.*;
import ui.*;

public class EntryPanel extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private String[] positions = { "Patient", "Doctor", "Administrator" };

	DatabaseAuthenticator authenticator = new DatabaseAuthenticator();

	JComboBox<String> comboBox = new JComboBox<String>(positions);;
	JTextField name;
	JTextField code;
	JPasswordField password;
	JButton enter;

	public EntryPanel() {
		this.setTitle("Вход");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(300, 290);

		ImageIcon icon = new ImageIcon("Medical_emblem.png");
		this.setIconImage(icon.getImage());

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		comboBox = new JComboBox<String>(positions);
		name = new JTextField();
		code = new JTextField();
		password = new JPasswordField();
		enter = new JButton();

		comboBox.setBounds(80, 20, 120, 30);
		comboBox.addActionListener(this);
		comboBox.setSelectedItem(null);
		comboBox.setBackground(Color.WHITE);

		name.setBounds(40, 70, 200, 30);
		name.setVisible(false);

		code.setBounds(40, 100, 200, 30);
		code.setVisible(false);

		password.setBounds(40, 150, 200, 30);
		password.setVisible(false);

		enter.setBounds(90, 200, 100, 30);
		enter.addActionListener(this);
		enter.setText("ENTER");
		enter.setFocusable(false);
		enter.setBackground(Color.WHITE);

		getContentPane().setLayout(null);
		getContentPane().add(comboBox);
		getContentPane().add(name);
		getContentPane().add(code);
		getContentPane().add(password);
		getContentPane().add(enter);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == comboBox)
		{
			if(comboBox.getSelectedItem() == "Patient")
			{
				name.setVisible(true);
				code.setVisible(false);
				password.setVisible(true);
			}
			else if(comboBox.getSelectedItem() == "Doctor")
			{
				name.setVisible(false);
				code.setVisible(true);
				password.setVisible(true);

			}
			else if(comboBox.getSelectedItem() == "Administrator")
			{
				name.setVisible(false);
				code.setVisible(true);
				password.setVisible(false);
			}
		}
		
		if(e.getSource() == enter && authenticator.authenticateUser(comboBox, code, name, password)) {
			if(comboBox.getSelectedItem() == "Patient" && e.getSource() == enter)
			{
				SwingUtilities.invokeLater(() -> new patientView());
				this.setVisible(false);
				this.dispose();
			}
		
			else if(comboBox.getSelectedItem() == "Doctor" && e.getSource() == enter)
			{
				SwingUtilities.invokeLater(() -> new doctorView());
				this.setVisible(false);
				this.dispose();
			}
		
			else if(comboBox.getSelectedItem() == "Administrator" && e.getSource() == enter)
			{
				SwingUtilities.invokeLater(() -> new administratorView());
				this.setVisible(false);
				this.dispose();
			}
		}
	}
}
