package org.example.render;

import org.example.entity.State;
import org.example.entity.Tweet;

import javax.swing.*;
import java.util.List;

public class MapFrame {

    private final JFrame frame;
    private final MapPanel mapPanel;

    public MapFrame(List<State> states, List<Tweet> tweets) {

        frame = new JFrame("US Map");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 1200);

        mapPanel = new MapPanel(states, tweets);
        frame.add(mapPanel);
    }

    public void display() {
        frame.setVisible(true);
    }

}