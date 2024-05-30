package edu.project.components;

import com.formdev.flatlaf.FlatClientProperties;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WeatherCard extends JPanel {

	private JLabel valueLabel;

	public WeatherCard(String title, String unit) {
		//// Set a custom property for the component's border style using FlatLaf
		//// properties
		putClientProperty(FlatClientProperties.STYLE,
				"border: 6,4,6,4,shade(@background,10%),,16");

		setOpaque(false);

		setLayout(new GridBagLayout());

		JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		valueLabel = new JLabel(" ", SwingConstants.CENTER);
		valueLabel.setFont(new Font("Arial", Font.BOLD, 32));

		JLabel unitLabel = new JLabel(unit, SwingConstants.CENTER);
		unitLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		// Define layout constraints for the GridBagLayout
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(3, 3, 3, 3);// Padding around components
		add(titleLabel, constraints);

		// Move to the next row and add the value label
		constraints.gridy++;
		add(valueLabel, constraints);

		// Move to the next row and add the unit label
		constraints.gridy++;
		add(unitLabel, constraints);
	}

	public void setValue(String value) {
		valueLabel.setText(value);
	}

	// Override the paintComponent method to customize the panel's appearance
	@Override
	protected void paintComponent(Graphics graphics) {

		// Cast the Graphics object to Graphics2D for better control over rendering
		Graphics2D graphics2d = (Graphics2D) graphics;

		// Enable anti-aliasing for smoother rendering
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setColor(new Color(224, 224, 224));
		graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

		// Call the superclass method to ensure proper rendering of other components
		super.paintComponent(graphics);
	}
}
