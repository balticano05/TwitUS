package org.example.render.tweet;

import org.example.entity.Tweet;
import org.example.render.logic.ColorHandler;
import org.example.utils.Converter;

import java.awt.*;

public class TweetDrawer {

    public void drawTweet(Graphics2D g2d, Tweet tweet, double scaledMinX, double scaledMaxX,
                          double scaledMinY, double scaledMaxY, int width, int height,
                          double centerLon, double centerLat) {

        double lon = tweet.getLongitude();
        double lat = tweet.getLatitude();

        int[] screenCoordinates = Converter.convertToScreenCoordinates(
                lon, lat,
                scaledMinX, scaledMaxX, scaledMinY, scaledMaxY,
                width, height, centerLon, centerLat
        );

        Color fillColor = ColorHandler.getColor(tweet.getWeightOfMood());
        Color borderColor = fillColor;

        g2d.setColor(fillColor);

        g2d.fillOval(screenCoordinates[0] - 1, screenCoordinates[1] - 2, 5, 5);

        g2d.setColor(borderColor);

        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(screenCoordinates[0] - 1, screenCoordinates[1] - 2, 5, 5);
    }

}