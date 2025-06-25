package uiComponents;

import customColors.ColorSchemes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class MenuSetter {
    JPanel menuPanel, contentPanel;

    public MenuSetter() {}

    public List<JPanel> setSettingsPage(JPanel menuPanel, JPanel contentPanel) {
        menuPanel.removeAll();
        contentPanel.removeAll();

        ArrayList<JPanel> panels = new ArrayList<>();

        menuPanel.setBackground(ColorSchemes.MENU_GREEN);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(300, 0));  // adjust width if needed

        // Centering container for the image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(ColorSchemes.MENU_GREEN);
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        ImageIcon profileIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/DefaultProfilePic.png")));
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(profileIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH)));
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

                    // TODO: Save 'imagePath' to your database for the current user
                    System.out.println("Избран файл: " + imagePath);
                }
            }
        });

        // Label "Настройки на профила" with hover effect
        JLabel profileSettings = new JLabel("Настройки на профила");
        profileSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileSettings.setFont(new Font("SansSerif", Font.PLAIN, 16));
        profileSettings.setForeground(Color.WHITE);
        profileSettings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profileSettings.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // top padding

        profileSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                profileSettings.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                profileSettings.setForeground(Color.WHITE);
            }
        });

        // Label "Настройки на приложението"
        JLabel appSettings = new JLabel("Настройки на приложението");
        appSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
        appSettings.setFont(new Font("SansSerif", Font.PLAIN, 16));
        appSettings.setForeground(Color.WHITE);
        appSettings.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // top padding

        // Add everything to menuPanel vertically
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(imagePanel);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(profileSettings);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(appSettings);
        menuPanel.add(Box.createVerticalGlue()); // push everything up

        // Content panel empty or custom setup
        contentPanel.setBackground(ColorSchemes.BACKGROUND_BEIGE);
        contentPanel.setLayout(new BorderLayout());

        // Layout constraints for mainPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.weightx = 0.25;
        panels.add(menuPanel);

        gbc.gridx = 1;
        gbc.weightx = 0.75;
        panels.add(contentPanel);

        return panels;
    }
}
