package edu.project.components.WeatherCards;

import javax.swing.*;
import java.awt.*;

public abstract class WeatherCard extends JPanel {
	protected JLabel titleLabel;
	protected JLabel valueLabel;
	protected JLabel unitLabel;

	public WeatherCard(String title, String value, String unit) {
		setLayout(new GridBagLayout());
		setBackground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(100, 150));

		titleLabel = new JLabel(title, SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		valueLabel = new JLabel(value, SwingConstants.CENTER);
		valueLabel.setFont(new Font("Arial", Font.BOLD, 32));

		unitLabel = new JLabel(unit, SwingConstants.CENTER);
		unitLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		add(titleLabel, gbc);

		gbc.gridy++;
		add(valueLabel, gbc);

		gbc.gridy++;
		add(unitLabel, gbc);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
	}
}
