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

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(3, 3, 3, 3);
		add(titleLabel, constraints);

		constraints.gridy++;
		add(valueLabel, constraints);

		constraints.gridy++;
		add(unitLabel, constraints);
	}

	public void setValue(String value) {
		valueLabel.setText(value);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setColor(Color.LIGHT_GRAY);
		graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16 * 2, 16 * 2);
		super.paintComponent(graphics);
	}
}
