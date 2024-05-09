package edu.project;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import edu.project.components.App;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			FlatMacLightLaf.setup();

			Context context = new Context();
			App app = new App(context);
			app.setVisible(true);
		});
	}
}