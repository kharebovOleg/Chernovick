package org.example.hexlet.Algoritms.Trees.KDTree;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Helper {

    /*
    Данный код представляет собой реализацию метода buildTree класса KDTreeNode, который строит KD-дерево
    на основе переданных точек.

    Аргументы метода:

    points - список точек, представленных в виде словарей (Map<String, Integer>), где ключ - это имя измерения,
    а значение - значение точки по данному измерению.
    depth - текущая глубина построения дерева.
    dimensions - список имен измерений.
    parent - родительский узел.
     */
    public static KDTreeNode buildTree(
            List<Map<String, Integer>> points, int depth, List<String> dimensions, KDTreeNode parent) {

        // 1) В начале метода определяется текущая ось разбиения пространства, используя остаток от деления глубины
        // на количество измерений. Например, если глубина равна 0, а количество измерений равно 3, то текущая ось
        // будет иметь индекс 0.
        var dim = dimensions.get(depth % dimensions.size());

        // 2) Затем проверяется базовый случай, когда список точек пуст. В этом случае возвращается null, так как нет
        // точек для построения узла дерева.
        if (points.size() == 0) {
            return null;
        }

        // 3) Далее проверяется другой базовый случай, когда в списке точек есть только одна точка. В этом случае
        // создается новый узел дерева с этой точкой, текущей осью разбиения и родительским узлом.
        if (points.size() == 1) {
            return new KDTreeNode(points.get(0), dim, parent);
        }

        // 4) Затем точки в списке сортируются по значению текущей оси разбиения. Для этого используется
        // лямбда-выражение, которое сравнивает значения точек по текущей оси.
        points.sort(Comparator.comparingInt(a -> a.get(dim)));

        // 5) Вычисляется медиана списка точек и создается новый узел дерева с этой медианной точкой,
        // текущей осью разбиения и родительским узлом
        int median = points.size() / 2;
        KDTreeNode node = new KDTreeNode(points.get(median), dim, parent);

        // 6) Затем рекурсивно вызывается метод buildTree для левого и правого подпространств, передавая
        // соответствующие подсписки точек, увеличенную глубину и текущую ось разбиения. Родительским узлом
        // для левого и правого поддеревьев становится только что созданный узел.
        node.left = buildTree(points.subList(0, median), depth + 1, dimensions, node);
        node.right = buildTree(points.subList(median + 1, points.size()), depth + 1, dimensions, node);

        // 7) В конце метода возвращается созданный узел.
        return node;
    }

    public static List<Map<String, Integer>> run(
            List<Map<String, Integer>> points, Map<String, Integer> center, int radius) {

        List<String> dimensions = List.of("x", "y");
        KDTreeNode tree = buildTree(points, 0, dimensions, null);
        return Main.findPointsInRadius(tree, center, radius);
    }
}
