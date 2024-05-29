package edu.project.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import edu.project.Context;

public class WeatherIsland extends JPanel {
	public WeatherIsland(Context context) {
		putClientProperty(FlatClientProperties.STYLE,
				"background: tint(@background,50%); border: 8,8,8,8,shade(@background,10%),,16");
		setOpaque(false);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		WeatherInfo weatherInfo = new WeatherInfo(context);

		// Maximize Width and Minimize Height
		weatherInfo.setMaximumSize(
				new Dimension(
						weatherInfo.getMaximumSize().width,
						weatherInfo.getPreferredSize().height));

		add(weatherInfo);

		add(Box.createVerticalStrut(8));

		WeatherCardGroup weatherGroup = new WeatherCardGroup(context);

		// Maximize Width and Minimize Height
		weatherGroup.setMaximumSize(
				new Dimension(
						weatherGroup.getMaximumSize().width,
						weatherGroup.getPreferredSize().height));

		add(weatherGroup);

	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setColor(getBackground());
		graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16 * 2, 16 * 2);
		super.paintComponent(graphics2d);
	}
}
