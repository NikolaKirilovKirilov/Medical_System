package engine;
import javax.swing.*;
import uiComponents.EntryPanel;

public class program {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(EntryPanel::new);
	}
}
