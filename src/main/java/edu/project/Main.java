package edu.project;

import javax.swing.SwingUtilities;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import edu.project.components.App;
import edu.project.services.WeatherService;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			FlatMacLightLaf.setup();

			Context context = new Context();
			new WeatherService(context);

			App app = new App(context);
			app.setVisible(true);
		});
	}
}
