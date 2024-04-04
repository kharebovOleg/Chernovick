package org.example.hexlet.algorithms.graphs.greedyalgotihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Helper {
    public static List<Worth> getWorths(List<Map<String, Object>> collection) {

        var worths = new ArrayList<Worth>();

        for (Map<String, Object> item : collection) {
            var name = item.get("name").toString();
            var price = Double.parseDouble(item.get("price").toString());
            var weight = Double.parseDouble(item.get("weight").toString());
            worths.add(new Worth(name, weight, price));
        }

        return worths;
    }

    public static Map<String, Object> run(List<Map<String, Object>> collection, int maxWeight) {
        var worths = getWorths(collection);
        return GreedyAlgorithm.backpack(worths, maxWeight);
    }
}
