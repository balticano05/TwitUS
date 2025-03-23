package org.example.utils.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

public abstract class AbstractLoader<T> {

    protected abstract T processContent(String content);

    public T load(String filePath) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String content = reader.lines().collect(Collectors.joining("\n"));

            return processContent(content);
        } catch (Exception e) {
            throw new RuntimeException("Error loading file: " + filePath, e);
        }

    }

}