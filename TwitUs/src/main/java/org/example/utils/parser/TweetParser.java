package org.example.utils.parser;

import org.example.entity.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetParser {

    public static List<Tweet> parseTweets(String input) {

        List<Tweet> tweets = new ArrayList<>();

        Pattern coordPattern = Pattern.compile("\\[(-?\\d+\\.\\d+),\\s*(-?\\d+\\.\\d+)]");
        String[] lines = input.split("\n");

        Double currentLat = null;
        Double currentLon = null;
        Date currentTime = null;

        StringBuilder currentText = new StringBuilder();

        for (String line : lines) {

            line = line.trim();
            Matcher m = coordPattern.matcher(line);

            if (m.find()) {

                if (currentLat != null && currentLon != null && currentTime != null) {

                    addTweet(tweets, currentLat, currentLon, currentTime, currentText.toString());
                    currentText.setLength(0);
                }

                currentLat = parseDouble(m.group(1));
                currentLon = parseDouble(m.group(2));

                String[] parts = line.split("\t");

                if (parts.length >= 3) {
                    currentTime = parseTimestamp(parts[2].trim());
                }

                if (parts.length >= 4) {
                    currentText.append(parts[3].trim()).append(" ");
                }

            } else if (currentLat != null) {
                currentText.append(line).append(" ");
            }

        }

        if (currentLat != null && currentLon != null && currentTime != null) {
            addTweet(tweets, currentLat, currentLon, currentTime, currentText.toString());
        }

        return tweets;
    }

    private static void addTweet(List<Tweet> tweets, double lat, double lon, Date time, String text) {

        String cleanedText = text.trim().replaceAll("\\s+", " ");

        if (!cleanedText.isEmpty()) {
            tweets.add(Tweet.builder()
                    .latitude(lat)
                    .longitude(lon)
                    .timestamp(time)
                    .text(cleanedText)
                    .build());
        }
    }

    private static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid coordinate format: " + value);
        }
    }

    private static Date parseTimestamp(String timestamp) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid timestamp format: " + timestamp);
        }
    }

}