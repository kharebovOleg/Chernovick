package org.example.hexlet.algorithms.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CycleSearch {

    // поиск всех циклов в графе
    // Метод принимает в качестве параметров два значения:
    //
    //Список вершин графа, List<String>
    //Матрицу смежности, List<List<Boolean>>
    //
    //Метод должен найти и вернуть все циклы в графе в виде множества Set<List<String>>
    public static Set<List<String>> run(List<String> vertices, List<List<Boolean>> edges) {
        var cycles = new HashSet<List<String>>();
        var visited = new boolean[vertices.size()]; // буленовый массив размером переданного количества вершин

        for (int node = 0; node < vertices.size(); node++) { // итерация по вершинам
            var path = new ArrayList<Integer>(); // создаем новый путь
            hasCycleDFS(node, node, vertices, edges, visited, path, cycles); //
        }

        return cycles;
    }

    private static void hasCycleDFS(
            int node, // текущий узел
            int startNode, // стартовый узел
            List<String> vertices, // список вершин
            List<List<Boolean>> edges, // матрица смежности
            boolean[] visited, // массив посещенных узлов
            List<Integer> path, // путь
            Set<List<String>> cycles) {

        visited[node] = true; // отмечаем, что посетили текущий узел
        path.add(node); // добавили его в путь

        for (int i = 0; i < vertices.size(); i++) { // проходимся по матрице смежности
            if (edges.get(node).get(i)) {
                if (!visited[i]) { // если текущий элемент не посещен
                    hasCycleDFS(i, startNode, vertices, edges, visited, path, cycles); // запускаем рекурсию для текущего узла
                } else if (i == startNode && path.size() > 2) { // если мы попали в стартовый узел и пройденный путь больше 2 узлов
                    var cycle = new ArrayList<String>(); // значит нашли цикл
                    for (int vertex : path) { // проходимся по пройденному пути
                        cycle.add(vertices.get(vertex)); // добавляем в цикл наши вершины
                    }
                    cycle.sort(String::compareTo); // сортируем
                    cycles.add(cycle); // добавляем цикл в сет
                }
            }
        }

        visited[node] = false;
        path.remove(path.size() - 1);
    }
}
