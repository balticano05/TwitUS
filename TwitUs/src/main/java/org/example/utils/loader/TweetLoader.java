package org.example.utils.loader;

import org.example.entity.Tweet;
import org.example.utils.parser.TweetParser;

import java.util.List;

public class TweetLoader extends AbstractLoader<List<Tweet>> {

    @Override
    protected List<Tweet> processContent(String content) {
        return TweetParser.parseTweets(content);
    }

}