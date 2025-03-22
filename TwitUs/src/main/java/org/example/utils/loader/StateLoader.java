package org.example.utils.loader;

import org.example.entity.State;
import org.example.utils.parser.StateParser;

import java.util.List;

public class StateLoader extends AbstractLoader<List<State>> {

    @Override
    protected List<State> processContent(String content) {
        return StateParser.parseStates(content);
    }

}