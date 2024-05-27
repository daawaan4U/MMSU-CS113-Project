package edu.project.components;

import java.awt.*;
import javax.swing.*;

import com.formdev.flatlaf.FlatClientProperties;

import edu.project.Context;

public class WeatherIsland extends JPanel {
	public WeatherIsland(Context context) {
		putClientProperty(FlatClientProperties.STYLE,
				"background: tint(@background,50%); border: 16,16,16,16,shade(@background,10%),,16");
		setOpaque(false);

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // Use BoxLayout for horizontal alignment

		add(createWeatherBox("Wind", "16", "mph", Color.LIGHT_GRAY));
		add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing between boxes
		add(createWeatherBox("Humidity", "50", "%", Color.LIGHT_GRAY));
		add(Box.createRigidArea(new Dimension(10, 0)));
		add(createWeatherBox("Rain Rate", "0", "in/h", Color.LIGHT_GRAY));
		add(Box.createRigidArea(new Dimension(10, 0)));
		add(createWeatherBox("UV Index", "5", "Moderate", Color.LIGHT_GRAY));
	}

	private JPanel createWeatherBox(String title, String value, String unit, Color boxColor) {
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics graphics) {
				Graphics2D graphics2d = (Graphics2D) graphics;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Draw shadow
				graphics2d.setColor(new Color(0, 0, 0, 50));
				graphics2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);

				// Draw box
				graphics2d.setColor(boxColor);
				graphics2d.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 20, 20);

				super.paintComponent(graphics2d);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(80, 90);
			}

			@Override
			public Dimension getMaximumSize() {
				return new Dimension(80, 90);
			}

			@Override
			public Dimension getMinimumSize() {
				return new Dimension(80, 90);
			}
		};

		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel titleLabel = new JLabel(title);
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		JLabel valueLabel = new JLabel(value);
		valueLabel.setAlignmentX(CENTER_ALIGNMENT);
		valueLabel.setFont(new Font("Arial", Font.BOLD, 24));

		JLabel unitLabel = new JLabel(unit);
		unitLabel.setAlignmentX(CENTER_ALIGNMENT);
		unitLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		panel.add(Box.createVerticalGlue());
		panel.add(titleLabel);
		panel.add(Box.createVerticalStrut(1));
		panel.add(valueLabel);
		panel.add(Box.createVerticalStrut(1));
		panel.add(unitLabel);
		panel.add(Box.createVerticalGlue());

		return panel;
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setColor(getBackground());
		graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16 + 12, 16 + 12);
		super.paintComponent(graphics2d);
	}

	// Main method for testing
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Weather Island");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(600, 200); // Adjust the frame size as needed

			JPanel mainPanel = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.insets = new Insets(10, 10, 10, 10); // Adjust insets for padding

			WeatherIsland weatherIsland = new WeatherIsland(new Context());
			mainPanel.add(weatherIsland, gbc);

			frame.add(mainPanel);
			frame.setVisible(true);
		});
	}
}
