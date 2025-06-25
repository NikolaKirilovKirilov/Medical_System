package uiComponents;

import com.example.model.Doctor;
import com.example.model.Patient;
import com.example.model.User;
import customColors.ColorSchemes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;
import database.QueryExecutor;

public class UserPage {
    Color headerColor = ColorSchemes.MENU_GREEN, hoverBackgroundColor = Color.DARK_GRAY, hoverTextColor = Color.WHITE,
            contentColor = ColorSchemes.BACKGROUND_BEIGE;

    public UserPage() {}

    public JPanel setUserPage(JPanel mainPanel, JPanel menuPanel, JPanel contentPanel, User user) {
        mainPanel.removeAll();
        menuPanel.removeAll();
        contentPanel.removeAll();

        menuPanel.setBackground(headerColor);
        menuPanel.setPreferredSize(new Dimension(300, 0));

        loadProfileImage(menuPanel);

        if (user instanceof Doctor doctor) {
            loadDoctorMenu(menuPanel, doctor);
        } else if (user instanceof Patient patient) {
            loadDoctorInfoForPatient(menuPanel, patient);
        }

        mainPanel.add(menuPanel);
        contentPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);
        contentPanel.setLayout(new BorderLayout());

        return mainPanel;
    }

    public JPanel loadDoctorInfoForPatient(JPanel menuPanel, Patient patient) {
        menuPanel.removeAll();

        menuPanel.setBackground(ColorSchemes.MENU_GREEN);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(300, 0));

        Doctor doctor = QueryExecutor.getDoctorByPatientCode(patient.getCode());

        if (doctor != null) {
            JLabel docName = new JLabel("Д-р " + doctor.getName() + " " + doctor.getSurname());
            docName.setFont(new Font("SansSerif", Font.BOLD, 16));
            docName.setForeground(Color.WHITE);
            docName.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel specialization = new JLabel("Специализация: " + doctor.getSpecialization());
            specialization.setFont(new Font("SansSerif", Font.PLAIN, 14));
            specialization.setForeground(Color.YELLOW);
            specialization.setAlignmentX(Component.CENTER_ALIGNMENT);

            menuPanel.add(Box.createVerticalStrut(30));
            menuPanel.add(docName);
            menuPanel.add(Box.createVerticalStrut(10));
            menuPanel.add(specialization);
        } else {
            JLabel notFound = new JLabel("Няма информация за лекар.");
            notFound.setForeground(Color.RED);
            notFound.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuPanel.add(notFound);
        }
        return menuPanel;
    }


    private void loadDoctorMenu(JPanel menuPanel, Doctor doctor) {
        JLabel nameLabel = new JLabel(doctor.getName() + " " + doctor.getSurname());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(nameLabel);

        JLabel specLabel = new JLabel("Специализация: " + doctor.getSpecialization());
        specLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        specLabel.setForeground(Color.WHITE);
        specLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(specLabel);
    }

    private void loadProfileImage(JPanel menuPanel) {
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(ColorSchemes.MENU_GREEN);

        ImageIcon profileIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/DefaultProfilePic.png")));
        JLabel imageLabel = new JLabel(new ImageIcon(profileIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH)));
        imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        imagePanel.add(imageLabel);

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Изберете нова снимка на профила");
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG и JPG", "png", "jpg", "jpeg"));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                    ImageIcon newIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH));
                    imageLabel.setIcon(newIcon);

                    // TODO: Save 'imagePath' to DB
                    System.out.println("Избран файл: " + imagePath);
                }
            }
        });

        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(imagePanel);
        menuPanel.add(Box.createVerticalStrut(20));
    }

}
