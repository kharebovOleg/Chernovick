package org.example.hexlet.algoritms.trees.kdtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
Метод принимает три параметра:

KD дерево с точками, объект класса KDTreeNode
Точку – центр окружности. Точка – это словарь Map<String, Integer> со свойствами x и y
Радиус окружности, целое число
Функция должна вернуть список List<Map<String, Integer>> всех точек из дерева, которые находятся внутри окружности
 */
public class Main { // todo разобраться с работой метода
    public static List<Map<String, Integer>> findPointsInRadius(KDTreeNode node, Map<String, Integer> centerPoint, int radius) {
        if (node == null) {
            return new ArrayList<>();
        }
        // в данном методе координаты x и y являются измерениями - dimension
        // определяем лежит ли точка в пределах окружности по формуле
        // (x - center_x)^2 + (y - center_y)^2 <= radius^2

        double dx = centerPoint.get("x") - node.point.get("x"); // (x - center_x)
        double dy = centerPoint.get("y") - node.point.get("y"); // (y - center_y)

        double distanceSquared = dx * dx + dy * dy; // (x - center_x)^2 + (y - center_y)^2

        if (distanceSquared <= radius * radius) { // если точка принадлежит окружности
            List<Map<String, Integer>> result = new ArrayList<>(); // создаем итоговый список
            result.add(node.point); // добавляем узел в итоговый список
            result.addAll(findPointsInRadius(node.left, centerPoint, radius)); // повторяем все действия для дочерних узлов
            result.addAll(findPointsInRadius(node.right, centerPoint, radius));
            return result;
        }
        // Теперь, если точка находится вне радиуса.
        // В зависимости от значения dimension, мы делаем выбор, в каком поддереве искать дальше - либо в левом,
        // либо в правом. Таким образом, мы оставляем за пределами нашего интереса только часть дерева, в которой могут
        // находиться точки внутри заданного радиуса.

        if (node.dimension.equals("x") && centerPoint.get("x") - radius <= node.point.get("x")) {
            List<Map<String, Integer>> result = new ArrayList<>();
            result.addAll(findPointsInRadius(node.left, centerPoint, radius));
            result.addAll(findPointsInRadius(node.right, centerPoint, radius));
            return result;

        }

        if (node.dimension.equals("y") && centerPoint.get("y") - radius <= node.point.get("y")) {
            List<Map<String, Integer>> result = new ArrayList<>();
            result.addAll(findPointsInRadius(node.left, centerPoint, radius));
            result.addAll(findPointsInRadius(node.right, centerPoint, radius));
            return result;

        }

        return findPointsInRadius(node.right, centerPoint, radius);
    }

    public static List<Map<String, Integer>> run(
            List<Map<String, Integer>> points, Map<String, Integer> center, int radius) {
        return Helper.run(points, center, radius);
    }
}
