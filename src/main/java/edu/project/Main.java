package edu.project;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatLightLaf;

import edu.project.components.App;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			FlatLightLaf.setup();

			App app = new App();
			app.setVisible(true);
		});
	}
}