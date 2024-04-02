package org.example.hexlet.algoritms.trees.btree;

import java.util.ArrayList;
import java.util.List;

public class FindValuesInRange {
    public static List<Integer> findValuesInRange(BTreeNode node, int min, int max) {
        List<Integer> values = new ArrayList<>();
        List<Integer> keys = node.keys;
        List<BTreeNode> children = node.children;

        for (int i = 0; i < keys.size(); i++) { // смотрим есть ли в текущем узле подходящие значения
            if (keys.get(i) >= min && keys.get(i) <= max) {
                values.add(keys.get(i)); // как только находим добавляем в ответ
            }
        }

        if (!node.leaf) { // проверяем, что узел не листовой
            for (int i = 0; i < children.size(); i++) {
                if (i == 0 && keys.get(0) >= min) { // если первый элемент текущего узла больше параметра min
                    values.addAll(findValuesInRange(children.get(i), min, max)); // выполняем теже действия для потомка
                } else if (i == children.size() - 1 && keys.get(keys.size() - 1) <= max) {
                    values.addAll(findValuesInRange(children.get(i), min, max));
                } else if (i < children.size() - 1 && (keys.get(i) >= min && keys.get(i) <= max)) {
                    values.addAll(findValuesInRange(children.get(i), min, max));
                }
            }
        }

        return values;
    }
}
