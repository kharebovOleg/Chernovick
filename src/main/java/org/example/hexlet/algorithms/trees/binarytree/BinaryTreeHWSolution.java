package org.example.hexlet.algorithms.trees.binarytree;

import java.util.ArrayList;
import java.util.List;

/*
 Функция для бинарного дерева поиска, которая выводит все пути от корня до листовых узлов
 Метод принимает в качестве параметра бинарное дерево BinaryTreeNode. Метод должен вернуть
 список List<List<Integer>> всех путей от корня до листовых узлов. Если на вход передано
 пустое дерево (null), метод должен вернуть пустой список
 */
public class BinaryTreeHWSolution {
    public static List<List<Integer>> collectPaths(BinaryTreeNode root, List<Integer> path, List<List<Integer>> paths) {
        if (root == null) {
            return paths;  // если нет узла возвращаем список путей, переданный в параметр метода
        }

        path.add(root.value); // добавляем в путь значение текущего узла
        if (root.left == null && root.right == null) { // если у узла нет корней, значит путь окончен
            paths.add(List.copyOf(path)); // можно передавать его в список путей
        } else {
            collectPaths(root.left, path, paths); // иначе повторяем действия для правого и левого потомка
            collectPaths(root.right, path, paths);
        }
        path.remove(path.size() - 1); //удаляем последний элемент из собранного пути todo узнать зачем эта строка

        return paths;
    }

    public static List<List<Integer>> collectPaths(BinaryTreeNode root) {
        return collectPaths(root, new ArrayList<Integer>(), new ArrayList<List<Integer>>());
    }

}
