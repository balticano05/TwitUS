package org.example.utils.parser;

import java.util.Arrays;
import java.util.HashMap;

public class PhraseWeightParser {

    public static HashMap<String, Double> parse(String input) {

        HashMap<String, Double> result = new HashMap<>();

        Arrays.stream(input.split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .forEach(line -> {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        try {
                            String word = parts[0].trim();

                            double value = Double.parseDouble(parts[1].trim());
                            result.put(word, value);
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("Invalid number format in line: " + line);
                        }
                    }
                });

        return result;
    }

}