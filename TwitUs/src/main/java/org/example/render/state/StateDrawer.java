package org.example.render.state;

import org.example.entity.State;
import org.example.entity.StatePolygon;
import org.example.render.logic.ColorHandler;
import org.example.render.logic.PolygonCentroid;
import org.example.utils.Converter;

import java.awt.*;
import java.util.List;

public class StateDrawer {

    private final StateNameDrawer stateNameDrawer = new StateNameDrawer();
    private final PolygonDrawer polygonDrawer = new PolygonDrawer();
    private final PolygonCentroid polygonCentroidCalculator = new PolygonCentroid();

    public void draw(Graphics2D g2d, State state, double scaledMinX, double scaledMaxX,
                     double scaledMinY, double scaledMaxY, int width, int height,
                     double centerLon, double centerLat) {

        drawAllPolygons(g2d, state, scaledMinX, scaledMaxX, scaledMinY, scaledMaxY,
                width, height, centerLon, centerLat);

        drawStateName(g2d, state, scaledMinX, scaledMaxX, scaledMinY, scaledMaxY,
                width, height, centerLon, centerLat);
    }

    private void drawAllPolygons(Graphics2D g2d, State state, double scaledMinX, double scaledMaxX,
                                 double scaledMinY, double scaledMaxY, int width, int height,
                                 double centerLon, double centerLat) {


        Color stateColor = ColorHandler.getColor(state.getWeightOfMood());

        for (StatePolygon polygon : state.getStatePolygons()) {
            polygonDrawer.draw(
                    g2d, polygon,
                    scaledMinX, scaledMaxX, scaledMinY, scaledMaxY,
                    width, height, centerLon, centerLat,
                    stateColor
            );
        }

    }

    private void drawStateName(Graphics2D g2d, State state, double scaledMinX, double scaledMaxX,
                               double scaledMinY, double scaledMaxY, int width, int height,
                               double centerLon, double centerLat) {

        double[] centroid = calculateStateCentroid(state.getStatePolygons());
        if (centroid == null) return;

        int[] screenCoordinates = Converter.convertToScreenCoordinates(
                centroid[0], centroid[1],
                scaledMinX, scaledMaxX, scaledMinY, scaledMaxY,
                width, height, centerLon, centerLat
        );

        stateNameDrawer.draw(g2d, state.getName(), screenCoordinates[0], screenCoordinates[1]);
    }

    private double[] calculateStateCentroid(List<StatePolygon> polygons) {

        double totalArea = 0;
        double weightedCentroidX = 0;
        double weightedCentroidY = 0;

        for (StatePolygon polygon : polygons) {

            double[] centroidData = polygonCentroidCalculator.calculate(polygon);
            double area = centroidData[0];
            double centroidX = centroidData[1];
            double centroidY = centroidData[2];

            totalArea += area;
            weightedCentroidX += centroidX * area;
            weightedCentroidY += centroidY * area;
        }

        if (totalArea == 0) return null;

        return new double[]{
                weightedCentroidX / totalArea,
                weightedCentroidY / totalArea
        };
    }

}