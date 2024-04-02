package org.example.hexlet.algoritms.trees.kdtree;

import java.util.Map;

// K-dimensional tree - дерево к-измерений
public class KDTreeNode {
    public Map<String, Integer> point; // список точек, представленных в виде словарей (Map<String, Integer>),
    // где ключ - это имя измерения, а значение - значение точки по данному измерению
    public String dimension; // измерение
    public KDTreeNode right;
    public KDTreeNode left;
    public KDTreeNode parent;

    public KDTreeNode(Map<String, Integer> point, String dimension, KDTreeNode parent) {
        this.point = point;
        this.dimension = dimension;
        this.right = null;
        this.left = null;
        this.parent = parent;
    }
}
