package org.example.utils.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Point;
import org.example.entity.State;
import org.example.entity.StatePolygon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StateParser {

    public static List<State> parseStates(String inputJson) {

        List<State> states = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            JsonNode rootNode = objectMapper.readTree(inputJson);

            Iterator<String> stateNames = rootNode.fieldNames();

            while (stateNames.hasNext()) {
                String stateName = stateNames.next();
                State state = parseState(rootNode, stateName);

                states.add(state);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return states;
    }

    private static State parseState(JsonNode rootNode, String stateName) {

        State state = new State();
        state.setName(stateName);

        state.setStatePolygons(new ArrayList<>());

        JsonNode polygonsNode = rootNode.get(stateName);

        if (polygonsNode != null && polygonsNode.isArray()) {

            for (JsonNode polygonNode : polygonsNode) {
                StatePolygon statePolygon = parsePolygon(polygonNode);

                state.getStatePolygons().add(statePolygon);
            }
        }

        return state;
    }

    private static StatePolygon parsePolygon(JsonNode polygonNode) {

        StatePolygon statePolygon = new StatePolygon();
        statePolygon.setPoints(new ArrayList<>());

        if (polygonNode.isArray()) {

            if (!polygonNode.isEmpty() && polygonNode.get(0).isArray()) {

                if (polygonNode.get(0).size() == 2 && polygonNode.get(0).get(0).isNumber()) {
                    parsePoints(polygonNode, statePolygon);
                } else {
                    parseIslands(polygonNode, statePolygon);
                }
            }
        }

        return statePolygon;
    }

    private static void parseIslands(JsonNode polygonNode, StatePolygon statePolygon) {

        for (JsonNode islandNode : polygonNode) {

            if (islandNode.isArray()) {
                parsePoints(islandNode, statePolygon);
            }
        }
    }

    private static void parsePoints(JsonNode pointsNode, StatePolygon statePolygon) {

        for (JsonNode pointNode : pointsNode) {

            if (pointNode.isArray() && pointNode.size() == 2) {

                Point point = parsePoint(pointNode);
                statePolygon.getPoints().add(point);
            }
        }
    }

    private static Point parsePoint(JsonNode pointNode) {

        Point point = new Point();
        point.setX(pointNode.get(0).asDouble());
        point.setY(pointNode.get(1).asDouble());

        return point;
    }

}
