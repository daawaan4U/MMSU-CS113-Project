package edu.project;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import edu.project.components.App;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			FlatMacLightLaf.setup();

			Store state = new Store();
			App app = new App(state);
			app.setVisible(true);
		});
	}
}