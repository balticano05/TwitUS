package org.example.render.logic;

import org.example.entity.Point;
import org.example.entity.StatePolygon;

import java.util.List;

public class PolygonCentroid {

    public double[] calculate(StatePolygon statePolygon) {

        double polygonArea = 0;
        double polygonCentroidX = 0;
        double polygonCentroidY = 0;

        List<Point> points = statePolygon.getPoints();

        int n = points.size();

        for (int i = 0; i < n; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % n);

            double lon1 = p1.getX();
            double lat1 = p1.getY();
            double lon2 = p2.getX();
            double lat2 = p2.getY();

            double cross = (lon1 * lat2 - lon2 * lat1);
            polygonArea += cross;
            polygonCentroidX += (lon1 + lon2) * cross;
            polygonCentroidY += (lat1 + lat2) * cross;
        }

        polygonArea /= 2;
        polygonCentroidX /= (6 * polygonArea);
        polygonCentroidY /= (6 * polygonArea);

        return new double[]{polygonArea, polygonCentroidX, polygonCentroidY};
    }

}