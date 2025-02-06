package uiComponents;

import javax.swing.*;
import java.awt.*;

public class RoundedJPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 210266837063189294L;
	
	private int cornerRadius;

    public RoundedJPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Make the panel transparent
    }
    
    public RoundedJPanel(int cornerRadius, int width, int height) {
        this.cornerRadius = cornerRadius;
        this.setPreferredSize(new Dimension(width, height));
        setOpaque(false); // Make the panel transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draws the rounded panel with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }
}