package org.example.render.logic;

import java.awt.*;

public class ColorHandler {

    public static Color getColor(Double weight) {

        double clampedWeight = Math.max(-1.0, Math.min(1.0, weight));

        ColorStop[] colorStops = {
                new ColorStop(-1.0, new Color(0, 0, 139)),
                new ColorStop(-0.5, new Color(0, 0, 255)),
                new ColorStop(0.0, new Color(128, 128, 128)),
                new ColorStop(0.5, new Color(255, 165, 0)),
                new ColorStop(1.0, new Color(255, 255, 0))
        };

        for (int i = 0; i < colorStops.length - 1; i++) {

            if (clampedWeight >= colorStops[i].position && clampedWeight <= colorStops[i + 1].position) {

                return interpolate(
                        colorStops[i].color,
                        colorStops[i + 1].color,
                        (clampedWeight - colorStops[i].position) / (colorStops[i + 1].position - colorStops[i].position)
                );
            }
        }

        return colorStops[colorStops.length - 1].color;
    }

    private static Color interpolate(Color start, Color end, double fraction) {

        int r = (int) (start.getRed() + (end.getRed() - start.getRed()) * fraction);
        int g = (int) (start.getGreen() + (end.getGreen() - start.getGreen()) * fraction);
        int b = (int) (start.getBlue() + (end.getBlue() - start.getBlue()) * fraction);

        return new Color(r, g, b);
    }

    private static class ColorStop {

        double position;
        Color color;

        ColorStop(double position, Color color) {
            this.position = position;
            this.color = color;
        }
    }

}