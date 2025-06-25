package uiComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.sql.SQLException;
import java.util.Objects;

import database.*;
import ui.*;

public class EntryPanel extends JFrame implements ActionListener {
	@Serial
	private static final long serialVersionUID = 1L;

	private final String[] positions = {"Пациент", "Доктор", "Администратор"};

	private final JComboBox<String> comboBox = new JComboBox<>(positions);
	private final JTextField nameField = new JTextField(15);
	private final JTextField codeField = new JTextField(15);
	private final JPasswordField passwordField = new JPasswordField(15);
	private final JButton enterButton = new JButton("ВХОД");

	JLabel spacer = new JLabel(" ");
	private final JLabel nameLabel = new JLabel("Име:");
	private final JLabel codeLabel = new JLabel("Код:");
	private final JLabel passwordLabel = new JLabel("Парола:");

	private final DatabaseAuthenticator authenticator = new DatabaseAuthenticator();

	public EntryPanel() {
		setTitle("Вход");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(250, 200);
		setLocationRelativeTo(null);

		ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/MASicon.png")));
		setIconImage(icon.getImage());

		comboBox.setSelectedItem(null);
		comboBox.setBackground(Color.WHITE);
		comboBox.addActionListener(this);
		comboBox.setMaximumSize(new Dimension(150,30));
		comboBox.setPreferredSize(new Dimension(150, 30));

		enterButton.setFocusable(false);
		enterButton.setBackground(Color.WHITE);
		enterButton.addActionListener(this);

		// Initially hidden fields
		nameLabel.setVisible(false);
		nameField.setVisible(false);
		codeLabel.setVisible(false);
		codeField.setVisible(false);
		passwordLabel.setVisible(false);
		passwordField.setVisible(false);

		// Layout for input form
		JPanel formPanel = new JPanel();
		GroupLayout layout = new GroupLayout(formPanel);
		formPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(nameLabel)
										.addComponent(codeLabel)
										.addComponent(passwordLabel)
										.addComponent(spacer, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)) // added here
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(nameField, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addComponent(codeField, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)))
						.addComponent(enterButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
		);

		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGap(15)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(nameLabel)
								.addComponent(nameField))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(codeLabel)
								.addComponent(codeField))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(passwordLabel)
								.addComponent(passwordField))
						.addComponent(spacer, 0, 0, 0) // dummy to keep it in both groups
						.addGap(20)
						.addComponent(enterButton)
		);

		// Center everything in a BorderLayout
		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.add(formPanel, BorderLayout.CENTER);
		add(wrapper);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object selected = comboBox.getSelectedItem();

		if (e.getSource() == comboBox && selected != null) {
			String role = selected.toString();

			// Hide all first
			nameLabel.setVisible(false);
			nameField.setVisible(false);
			codeLabel.setVisible(false);
			codeField.setVisible(false);
			passwordLabel.setVisible(false);
			passwordField.setVisible(false);

			// Show relevant fields
			switch (role) {
				case "Пациент" -> {
					nameLabel.setVisible(true);
					nameField.setVisible(true);
					passwordLabel.setVisible(true);
					passwordField.setVisible(true);
				}
				case "Доктор" -> {
					codeLabel.setVisible(true);
					codeField.setVisible(true);
					passwordLabel.setVisible(true);
					passwordField.setVisible(true);
				}
				case "Администратор" -> {
					codeLabel.setVisible(true);
					codeField.setVisible(true);
				}
			}

			revalidate();
			repaint();
		}

		if (e.getSource() == enterButton &&
				authenticator.authenticateUser(comboBox, codeField, nameField, passwordField)) {

			String role = Objects.requireNonNull(comboBox.getSelectedItem()).toString();

			switch (role) {
				case "Пациент" -> SwingUtilities.invokeLater(() -> {
					try {
						new patientView(nameField.getText().trim(), passwordField.getPassword());
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				});
				case "Доктор" -> SwingUtilities.invokeLater(() -> {
					try {
						new doctorView(Integer.parseInt(codeField.getText().trim()), passwordField.getPassword());
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				});
				case "Администратор" -> SwingUtilities.invokeLater(() -> {
					try {
						new administratorView(Integer.parseInt(codeField.getText().trim()));
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				});
			}
			this.dispose();
		}
	}
}
