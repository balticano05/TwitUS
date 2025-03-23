package org.example.utils;

public class Converter {

    public static int[] convertToScreenCoordinates(double lon, double lat, double minX,
                                                   double maxX, double minY, double maxY,
                                                   int width, int height, double centerLon,
                                                   double centerLat) {
        int x = (int) ((lon - minX) / (maxX - minX) * width);
        int y = (int) ((maxY - lat) / (maxY - minY) * height);

        x -= (int) ((centerLon - minX) / (maxX - minX) * width - width / 2);
        y -= (int) ((maxY - centerLat) / (maxY - minY) * height - height / 2);

        return new int[]{x, y};
    }

}