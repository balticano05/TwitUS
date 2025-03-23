package org.example.render;

import lombok.AllArgsConstructor;
import org.example.entity.State;
import org.example.entity.Tweet;
import org.example.render.state.StateDrawer;
import org.example.render.tweet.TweetDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@AllArgsConstructor
public class MapPanel extends JPanel {

    private final List<State> states;
    private final List<Tweet> tweets;

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        StateDrawer stateDrawer = new StateDrawer();
        TweetDrawer tweetDrawer = new TweetDrawer();

        double zoomLevel = 2.2;
        double centerLon = -98.0;
        double centerLat = 39.0;

        double minX = -180, maxX = 180, minY = -90, maxY = 90;

        double[] scaledBounds = calculateScaledBounds(minX, maxX, minY, maxY, centerLon, centerLat, zoomLevel);
        double scaledMinX = scaledBounds[0];
        double scaledMaxX = scaledBounds[1];
        double scaledMinY = scaledBounds[2];
        double scaledMaxY = scaledBounds[3];

        int width = getWidth();
        int height = getHeight();

        for (State state : states) {
            stateDrawer.draw(g2d, state, scaledMinX, scaledMaxX, scaledMinY, scaledMaxY, width, height, centerLon, centerLat);
        }

        for (Tweet tweet : tweets) {
            tweetDrawer.drawTweet(g2d, tweet, scaledMinX, scaledMaxX, scaledMinY, scaledMaxY, width, height, centerLon, centerLat);
        }

    }

    private double[] calculateScaledBounds(double minX, double maxX, double minY,
                                           double maxY, double centerLon, double centerLat,
                                           double zoomLevel) {
        double zoomFactor = 1.0 / zoomLevel;
        double scaledMinX = centerLon - (centerLon - minX) * zoomFactor;
        double scaledMaxX = centerLon + (maxX - centerLon) * zoomFactor;
        double scaledMinY = centerLat - (centerLat - minY) * zoomFactor;
        double scaledMaxY = centerLat + (maxY - centerLat) * zoomFactor;

        return new double[]{scaledMinX, scaledMaxX, scaledMinY, scaledMaxY};
    }

}