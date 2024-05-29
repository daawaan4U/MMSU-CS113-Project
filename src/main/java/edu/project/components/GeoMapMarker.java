package edu.project.components;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.WaypointRenderer;

public class GeoMapMarker implements WaypointRenderer<DefaultWaypoint> {

	private static BufferedImage markerIconImage;
	private static BufferedImage markerShadowImage;

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
		Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());

		graphics.drawImage(markerShadowImage, (int) point.getX() - 12,
				(int) point.getY() - markerShadowImage.getHeight(), null);
		graphics.drawImage(markerIconImage, (int) point.getX() - markerIconImage.getWidth() / 2,
				(int) point.getY() - markerIconImage.getHeight(), null);

		graphics.dispose();
	}
}
