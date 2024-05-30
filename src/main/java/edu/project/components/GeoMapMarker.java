package edu.project.components;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.WaypointRenderer;

/**
 * Renderer for the marker in the map
 */
public class GeoMapMarker implements WaypointRenderer<DefaultWaypoint> {

	// Cached Marker Image
	private static BufferedImage markerIconImage;

	// Cached Marker Shadow Image
	private static BufferedImage markerShadowImage;

	// Load Resource Images
	static {
		try {
			markerIconImage = ImageIO.read(GeoMapMarker.class.getResource("/images/marker-icon.png"));
			markerShadowImage = ImageIO.read(GeoMapMarker.class.getResource("/images/marker-shadow.png"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void paintWaypoint(Graphics2D graphics, JXMapViewer map, DefaultWaypoint waypoint) {
		graphics = (Graphics2D) graphics.create();

		// Convert pixel position to geolocation coordinates
		Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());

		// Offset marker image to match marker tip with the target pixel position
		graphics.drawImage(markerShadowImage, (int) point.getX() - 12,
				(int) point.getY() - markerShadowImage.getHeight(), null);

		// Offset marker shadow image to match marker tip with the target pixel position
		graphics.drawImage(markerIconImage, (int) point.getX() - markerIconImage.getWidth() / 2,
				(int) point.getY() - markerIconImage.getHeight(), null);

		graphics.dispose();
	}
}
