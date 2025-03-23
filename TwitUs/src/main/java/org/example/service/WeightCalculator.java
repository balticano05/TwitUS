package org.example.service;

import org.example.entity.Point;
import org.example.entity.State;
import org.example.entity.StatePolygon;
import org.example.entity.Tweet;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WeightCalculator {

    private HashMap<String, Double> phraseWeightList;
    private HashMap<State, Point2D.Double> centroidsCache;

    public WeightCalculator(HashMap<String, Double> phraseWeightList) {
        this.phraseWeightList = phraseWeightList;
        this.centroidsCache = new HashMap<>();
    }

    public Double calculateForTweet(String input) {

        Double weight = 0.0;

        Pattern pattern = Pattern.compile("[^&#/.@|!:;*]\\w+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String token = matcher.group();
            weight += phraseWeightList.getOrDefault(token.trim(), 0.0);
        }

        return matcher.groupCount() > 0 ? weight / matcher.groupCount() : weight;
    }

    public void calculateForStates(List<Tweet> tweets, List<State> states) {

        calculateCentroids(states);

        HashMap<State, StateStatistics> stats = new HashMap<>();

        for (Tweet tweet : tweets) {

            State state = locateTweet(tweet, states);
            if (state != null) {
                stats.computeIfAbsent(state, k -> new StateStatistics())
                        .add(tweet.getWeightOfMood());
            }
        }

        for (State state : states) {

            StateStatistics stateStats = stats.get(state);

            if (stateStats != null) {
                state.setWeightOfMood(stateStats.getAverage());
            } else {
                state.setWeightOfMood(0.0);
            }
        }
    }

    private State locateTweet(Tweet tweet, List<State> states) {

        if (tweet.getLatitude() == null || tweet.getLongitude() == null) {
            return null;
        }

        Point2D.Double point = new Point2D.Double(
                tweet.getLongitude(),
                tweet.getLatitude()
        );

        for (State state : states) {

            if (isInState(point, state)) {
                return state;
            }
        }

        return findNearestState(point, states);
    }

    private boolean isInState(Point2D.Double point, State state) {

        for (StatePolygon statePolygon : state.getStatePolygons()) {

            if (rayCastingAlgorithm(point, statePolygon.getPoints())) {
                return true;
            }
        }

        return false;
    }

    private boolean rayCastingAlgorithm(Point2D.Double point, List<Point> polygon) {

        boolean inside = false;
        int n = polygon.size();

        for (int i = 0, j = n - 1; i < n; j = i++) {

            Point2D.Double p1 = convertPoint(polygon.get(i));
            Point2D.Double p2 = convertPoint(polygon.get(j));

            if ((p1.y > point.y) != (p2.y > point.y) &&
                    point.x < (p2.x - p1.x) * (point.y - p1.y) / (p2.y - p1.y) + p1.x) {
                inside = !inside;
            }
        }

        return inside;
    }

    private void calculateCentroids(List<State> states) {

        for (State state : states) {

            List<Point2D.Double> allPoints = state.getStatePolygons().stream()
                    .flatMap(p -> p.getPoints().stream())
                    .map(this::convertPoint)
                    .collect(Collectors.toList());

            double avgX = allPoints.stream().mapToDouble(p -> p.x).average().orElse(0);
            double avgY = allPoints.stream().mapToDouble(p -> p.y).average().orElse(0);

            centroidsCache.put(state, new Point2D.Double(avgX, avgY));
        }
    }

    private State findNearestState(Point2D.Double target, List<State> states) {
        return states.stream()
                .min(Comparator.comparingDouble(s ->
                        target.distance(centroidsCache.get(s))))
                .orElse(null);
    }

    private Point2D.Double convertPoint(Point p) {
        return new Point2D.Double(p.getX(), p.getY());
    }

    private static class StateStatistics {

        double totalMood = 0;
        int tweetCount = 0;

        void add(double mood) {
            totalMood += mood;
            tweetCount++;
        }

        double getAverage() {
            return tweetCount > 0 ? totalMood / tweetCount : 0.0;
        }

    }

}