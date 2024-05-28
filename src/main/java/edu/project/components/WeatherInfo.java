package edu.project.components;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import edu.project.Context;
import java.awt.BorderLayout;

public class WeatherInfo extends JPanel {
	public WeatherInfo(Context context) {
		setLayout(new BorderLayout());
		setOpaque(false);

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setOpaque(false);

		JLabel dateLabel = new JLabel("Saturday, May 4");
		dateLabel.setFont(new Font(dateLabel.getFont().getName(), Font.BOLD, 18));

		JLabel skyLabel = new JLabel("Clear Sky");
		JLabel feelsLikeLabel = new JLabel("<html>Feels like <b>31°C</b></html>");
		JLabel needLabel = new JLabel("Umbrella required");

		leftPanel.add(dateLabel);
		leftPanel.add(skyLabel);
		leftPanel.add(feelsLikeLabel);
		leftPanel.add(needLabel);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setOpaque(false);

		JLabel locationLabel = new JLabel("Quiling Sur");
		locationLabel.setFont(new Font(locationLabel.getFont().getName(), Font.BOLD, 18));

		JLabel tempLabel = new JLabel("39°C");
		tempLabel.setFont(new Font(tempLabel.getFont().getName(), Font.BOLD, 25));

		JLabel highLowTempLabel = new JLabel("H: 40°C / L: 32°C");
		highLowTempLabel.setFont(
				new Font(highLowTempLabel.getFont().getName(), Font.BOLD, highLowTempLabel.getFont().getSize()));

		rightPanel.add(locationLabel);
		rightPanel.add(tempLabel);
		rightPanel.add(highLowTempLabel);

		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.EAST);
	}
}
