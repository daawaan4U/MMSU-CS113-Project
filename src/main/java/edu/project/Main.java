package edu.project;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import edu.project.components.App;
import edu.project.services.WeatherService;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			// Initialize FlatLaf MacOS Light Theme Look & Feel
			FlatMacLightLaf.setup();

			// Check for internet availability before running app
			if (!isInternetAvailable()) {
				JOptionPane.showMessageDialog(null,
						"<html><left>The application requires internet connection. <br/>Please check your network settings and try again.</left></html>",
						"Weather Forecast", JOptionPane.ERROR_MESSAGE);
				return; // Exit the application if there's no internet connection
			}

			// Initialize context & services
			Context context = new Context();
			new WeatherService(context);

			// Start application
			App app = new App(context);
			app.setVisible(true);
		});
	}

	private static boolean isInternetAvailable() {
		// Check internet availability by opening a connection to Google DNS
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}
}
