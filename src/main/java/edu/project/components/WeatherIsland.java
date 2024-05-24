package edu.project.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import edu.project.Context;

public class WeatherIsland extends JPanel {
    public WeatherIsland(Context context) {
        putClientProperty(FlatClientProperties.STYLE,
                "background: tint(@background,50%); border: 16,16,16,16,shade(@background,10%),,16");
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2d.setColor(new Color(220, 220, 220, 180)); // Example color, adjust as needed

        int arcWidth = 24;
        int arcHeight = 24;

        // Draw the top large rectangle (3-Hour Forecast)
        graphics2d.fillRoundRect(20, 20, getWidth() - 40, 100, arcWidth, arcHeight);

        // Draw the bottom large rectangle (5-Day Forecast)
        graphics2d.fillRoundRect(20, 140, getWidth() - 40, 100, arcWidth, arcHeight);

        // Draw the four squares (info boxes)
        int squareSize = 60;
        int spacing = 20;

        graphics2d.fillRoundRect(20, 260, squareSize, squareSize, arcWidth, arcHeight);
        graphics2d.fillRoundRect(100, 260, squareSize, squareSize, arcWidth, arcHeight);
        graphics2d.fillRoundRect(180, 260, squareSize, squareSize, arcWidth, arcHeight);
        graphics2d.fillRoundRect(260, 260, squareSize, squareSize, arcWidth, arcHeight);
    }
}
