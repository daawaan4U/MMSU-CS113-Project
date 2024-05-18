package edu.project.components;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

import javax.swing.border.EmptyBorder;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

import edu.project.Config;
import edu.project.Context;
import edu.project.tilesets.HybridTileFactory;

public class GeoMap extends JXMapKit 
{
	public GeoMap(Context context) 
    {
		setDefaultProvider(DefaultProviders.Custom);
		setTileFactory(new HybridTileFactory());

		JXMapViewer mainMap = getMainMap();
		mainMap.setBorder(new EmptyBorder(10, 10, 10, 10)); 
		mainMap.setOverlayPainter(null); 
		for (MouseListener listener : mainMap.getMouseListeners()) 
        {
			
			if (listener instanceof CenterMapListener)
				mainMap.removeMouseListener(listener);
		}

		JXMapViewer miniMap = getMiniMap();
		miniMap.setMinimumSize(new Dimension(150, 150)); 
		miniMap.setPreferredSize(new Dimension(150, 150));

		// Configure overlay marker
		WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<DefaultWaypoint>();
		HashSet<DefaultWaypoint> waypoints = new HashSet<DefaultWaypoint>();
		DefaultWaypoint waypoint = new DefaultWaypoint();
		waypoints.add(waypoint);
		waypointPainter.setWaypoints(waypoints);
		try 
        {
			waypointPainter.setRenderer(new GeoMapMarker());
		} catch (Exception exception) 
        {
			exception.printStackTrace(System.err);
		}

		
		mainMap.setOverlayPainter(waypointPainter);

		
		mainMap.addMouseListener(new MouseAdapter() 
        {
			@Override
			public void mouseClicked(MouseEvent event) 
            {
				Point point = event.getPoint();
				GeoPosition position = mainMap.convertPointToGeoPosition(point);
				context.store.setLocation(position);
			}
		});

		
		context.store.addLocationListener(position -> 
        {
			waypoint.setPosition(position);
			getMainMap().repaint();
		});

		setCenterPosition(context.store.getLocation());
		setZoom(Config.MAP_INIT_ZOOM);
	}
}
