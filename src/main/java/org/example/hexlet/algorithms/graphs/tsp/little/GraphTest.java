package org.example.hexlet.algorithms.graphs.tsp.little;

public class GraphTest {
    private static final String PATH = "src/main/resources/tsp/matrix";

    public static void main(String[] args) {
        //new Graph().start(PATH);
        new GraphDetailed(PATH).bfs();
    }
}