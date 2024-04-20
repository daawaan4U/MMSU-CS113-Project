package edu.project;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import edu.project.components.App;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			FlatMacLightLaf.setup();

			App app = new App();
			app.setVisible(true);
		});
	}
}