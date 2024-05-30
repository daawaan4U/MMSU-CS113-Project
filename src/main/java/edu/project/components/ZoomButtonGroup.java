package edu.project.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.project.Context;

/**
 * Zoom-In & Zoom-Out button group component
 */
public class ZoomButtonGroup extends JPanel {
	public ZoomButtonGroup(Context context) {
		setOpaque(false);
		setLayout(new GridLayout(2, 1));

		// Initialize zoom-in button
		JButton zoomInButton = new JButton("+");
		zoomInButton.setFont(new Font("Arial", Font.PLAIN, 32));
		zoomInButton.setForeground(Color.GRAY);
		add(zoomInButton);
		zoomInButton.addActionListener(e -> {
			context.store.zoomIn();
		});

		// Initialize zoom-out button
		JButton zoomOutButton = new JButton("-");
		zoomOutButton.setForeground(Color.GRAY);
		zoomOutButton.setFont(new Font("Arial", Font.PLAIN, 32));
		add(zoomOutButton);
		zoomOutButton.addActionListener(e -> {
			context.store.zoomOut();
		});
	}
}
