package org.example.hexlet.Algoritms.Trees.RBTree;

public class RBTreeNode {
    public RBTreeNode left = null; // ссылка на левый дочерний узел
    public RBTreeNode right = null; // ссылка на правый дочерний
    public boolean isRed = false; // Цвет узла
    // Если узел не красный, то считаем что он черный
    public RBTreeNode parent; // ссылка на родителя
    public Object value; // полезная нагрузка

    RBTreeNode(Object value, RBTreeNode parent) {
        this.parent = parent;
        this.value = value;
    }
}
