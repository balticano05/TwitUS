package org.example.render.state;

import java.awt.*;

public class StateNameDrawer {

    public void draw(Graphics2D g2d, String stateName, int centerX, int centerY) {

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));

        FontMetrics metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(stateName);
        int textHeight = metrics.getHeight();

        int textX = centerX - textWidth / 2;
        int textY = centerY + textHeight / 4;

        g2d.drawString(stateName, textX, textY);
    }

}