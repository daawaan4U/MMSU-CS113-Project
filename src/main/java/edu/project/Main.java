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
			FlatMacLightLaf.setup();

			if (!isInternetAvailable()) {
				JOptionPane.showMessageDialog(null,
						"<html><left>The application requires internet connection. <br/>Please check your network settings and try again.</left></html>",
						"Weather Forecast", JOptionPane.ERROR_MESSAGE);
				return; // Exit the application if there's no internet connection
			}

			Context context = new Context();
			new WeatherService(context);

			App app = new App(context);
			app.setVisible(true);
		});
	}

	private static boolean isInternetAvailable() {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
			return true;
		} catch (Exception e) {
			return true;
		}
	}
}
