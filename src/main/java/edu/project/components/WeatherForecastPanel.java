package edu.project.components;

import edu.project.Context;
import edu.project.api.WeatherForecast5Data;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class WeatherForecastPanel extends JPanel {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE").withLocale(Locale.ENGLISH); // Abbreviate the day names
    private static final Color PANEL_COLOR = new Color(211, 211, 211); // Light gray color
    private static final int ARC_WIDTH = 20; // Adjust the arc width for rounded corners
    private static final int ARC_HEIGHT = 20; // Adjust the arc height for rounded corners
    private static final int SPACING = 0; // Vertical spacing between each temperature label

    public WeatherForecastPanel(Context context) {
        setOpaque(false);
        context.store.addWeatherForecast5DataListener(this::updateForecast);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, -30)); // Add padding around the panel

        setLayout(new BorderLayout()); // Use BorderLayout for the main panel

        // Create and add the title panel with left alignment
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("5-Day Forecast");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
    }

    private void updateForecast(WeatherForecast5Data forecastData) {
        // Create a panel to hold the forecast content
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, SPACING)); // Set FlowLayout with center alignment and vertical spacing

        List<WeatherForecast5Data.WeatherList> weatherList = forecastData.list;

        // Ensure we process only up to the next 5 days
        int dayCount = Math.min(5, weatherList.size() / 8);
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < dayCount; i++) {
            LocalDate forecastDate = currentDate.plusDays(i);

            JPanel dayPanel = new JPanel(new GridLayout(3, 1)); // Create a panel to hold day, separator, and temperature
            dayPanel.setOpaque(false); // Make it transparent

            JLabel dayLabel = new JLabel(forecastDate.format(dateFormatter));
            dayLabel.setFont(new Font(dayLabel.getFont().getName(), Font.BOLD, 14)); // Adjust font size
            dayLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align day label
            dayPanel.add(dayLabel);

            // Add horizontal spacing between day and separator
            dayPanel.add(Box.createHorizontalStrut(2));

            // Add the separator panel with an image
            JPanel separatorPanel = createSeparatorPanel();
            dayPanel.add(separatorPanel);

            // Add horizontal spacing between separator and temperature
            dayPanel.add(Box.createHorizontalStrut(2));

            // Calculate the average temperature for the day, converting from Kelvin to Celsius and dividing by 8
            float sumTemp = 0;
            for (int j = i * 8; j < (i + 1) * 8 && j < weatherList.size(); j++) {
                sumTemp += weatherList.get(j).main.temp;
            }
            float averageTemp = (sumTemp / 8) - 273.15f;  // Convert from Kelvin to Celsius and divide by 8

            JLabel tempLabel = new JLabel(String.format("%.1f°C", averageTemp));
            tempLabel.setFont(new Font(tempLabel.getFont().getName(), Font.BOLD, 13)); // Adjust font size
            tempLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align temperature label
            dayPanel.add(tempLabel);

            contentPanel.add(dayPanel); // Add the day panel to the content panel
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
