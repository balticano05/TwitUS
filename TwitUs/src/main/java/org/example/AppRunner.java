package org.example;

import org.example.entity.State;
import org.example.entity.Tweet;
import org.example.utils.loader.PhraseWeightLoader;
import org.example.utils.loader.StateLoader;
import org.example.utils.loader.TweetLoader;

import java.util.HashMap;
import java.util.List;

public class AppRunner {

    public static void run(){

        TweetLoader tweetLoader = new TweetLoader();
        StateLoader stateLoader = new StateLoader();
        PhraseWeightLoader phraseWeightLoader = new PhraseWeightLoader();

        List<Tweet> tweets = tweetLoader.load("src/main/resources/tweets.txt");
        List<State> states = stateLoader.load("src/main/resources/states.json");
        HashMap<String, Double> phraseWeightList = phraseWeightLoader.load("src/main/resources/phrases.csv");
    }

}