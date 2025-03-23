package org.example.render.state;

import org.example.entity.StatePolygon;
import org.example.utils.Converter;

import java.awt.*;

public class PolygonDrawer {

    public void draw(Graphics2D g2d, StatePolygon statePolygon, double minX, double maxX,
                     double minY, double maxY, int width, int height, double centerLon,
                     double centerLat, Color fillColor) {
        int[] xPoints = new int[statePolygon.getPoints().size()];
        int[] yPoints = new int[statePolygon.getPoints().size()];

        for (int i = 0; i < statePolygon.getPoints().size(); i++) {

            org.example.entity.Point point = statePolygon.getPoints().get(i);

            double lon = point.getX();
            double lat = point.getY();

            int[] screenCoords = Converter.convertToScreenCoordinates(lon, lat, minX, maxX, minY, maxY, width, height, centerLon, centerLat);
            xPoints[i] = screenCoords[0];
            yPoints[i] = screenCoords[1];
        }

        g2d.setColor(fillColor);
        g2d.fillPolygon(xPoints, yPoints, statePolygon.getPoints().size());

        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, statePolygon.getPoints().size());
    }

}