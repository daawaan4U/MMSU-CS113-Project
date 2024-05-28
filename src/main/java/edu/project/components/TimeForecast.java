package edu.project.components;

import edu.project.Context;
import edu.project.api.WeatherForecast5Data;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TimeForecast extends JPanel {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h a").withLocale(Locale.ENGLISH); // Abbreviate the hour
    private static final Color PANEL_COLOR = new Color(211, 211, 211); // Light gray color
    private static final int ARC_WIDTH = 20; // Adjust the arc width for rounded corners
    private static final int ARC_HEIGHT = 20; // Adjust the arc height for rounded corners
    private static final int SPACING = 0; // Vertical spacing between each temperature label

    public TimeForecast(Context context) {
        setOpaque(false);
        context.store.addWeatherForecast5DataListener(this::updateForecast);
        setBorder(BorderFactory.createEmptyBorder(10, 5, 10,-90)); // Add padding around the panel

        setLayout(new BorderLayout()); // Use BorderLayout for the main panel

        // Create and add the title panel with left alignment
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("8-Hour Forecast");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
    }

    private void updateForecast(WeatherForecast5Data forecastData) {
        // Create a panel to hold the forecast content
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, -20, SPACING)); // Set FlowLayout with center alignment and vertical spacing

        List<WeatherForecast5Data.WeatherList> weatherList = forecastData.list;

        // Ensure we process only up to the next 8 hours
        int hourCount = Math.min(8, weatherList.size());
        LocalDateTime currentTime = LocalDateTime.now();

        for (int i = 0; i < hourCount; i++) {
            LocalDateTime forecastTime = currentTime.plusHours(i);

            JPanel hourPanel = new JPanel(new GridLayout(3, 1)); // Create a panel to hold hour, separator, and temperature
            hourPanel.setOpaque(false); // Make it transparent

            JLabel hourLabel = new JLabel(forecastTime.format(timeFormatter));
            hourLabel.setFont(new Font(hourLabel.getFont().getName(), Font.BOLD, 14)); // Adjust font size
            hourLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align hour label
            hourPanel.add(hourLabel);

            // Add horizontal spacing between hour and separator
            hourPanel.add(Box.createHorizontalStrut(1));

            // Add the separator panel with an image
            JPanel separatorPanel = createSeparatorPanel();
            hourPanel.add(separatorPanel);

            // Add horizontal spacing between separator and temperature
            hourPanel.add(Box.createHorizontalStrut(1));

            // Get the temperature for the hour, converting from Kelvin to Celsius
            float tempKelvin = weatherList.get(i).main.temp;
            float tempCelsius = tempKelvin - 273.15f; // Convert from Kelvin to Celsius

            JLabel tempLabel = new JLabel(String.format("%.1f°C", tempCelsius));
            tempLabel.setFont(new Font(tempLabel.getFont().getName(), Font.BOLD, 13)); // Adjust font size
            tempLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align temperature label
            hourPanel.add(tempLabel);

            contentPanel.add(hourPanel); // Add the hour panel to the content panel
        }

        // Remove the previous content panel and add the new one
        if (getComponentCount() > 1) {
            remove(1);
        }
        add(contentPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private JPanel createSeparatorPanel() {
        // Load the image
        ImageIcon icon = new ImageIcon("path/to/your/image.png");

        JPanel separatorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw the image at the center of the panel
                int x = (getWidth() - icon.getIconWidth()) / 2;
                int y = (getHeight() - icon.getIconHeight()) / 2;
                icon.paintIcon(this, g2d, x, y);
            }
        };
        separatorPanel.setLayout(new BorderLayout());
        separatorPanel.setPreferredSize(new Dimension(20, 20)); // Set preferred size for the separator panel
        separatorPanel.setMaximumSize(new Dimension(20, 20)); // Set maximum size to ensure it doesn't expand
        return separatorPanel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(PANEL_COLOR);
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT));
        g2d.dispose();
    }
}
