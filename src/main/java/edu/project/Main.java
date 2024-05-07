package edu.project;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import edu.project.components.App;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			FlatMacLightLaf.setup();

			Store store = new Store();
			App app = new App(store);
			app.setVisible(true);
		});
	}
}