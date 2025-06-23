package uiComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StyledComponents {
    public JButton createStyledButton(String text, Color backgroundColor, Color hoverBackgroundColor,
                                       Color hoverTextColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        // .,/
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
}
