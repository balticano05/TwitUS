package org.example.utils.loader;

import org.example.utils.parser.PhraseWeightParser;

import java.util.HashMap;

public class PhraseWeightLoader extends AbstractLoader<HashMap<String, Double>> {

    @Override
    protected HashMap<String, Double> processContent(String content) {
        return PhraseWeightParser.parse(content);
    }

}