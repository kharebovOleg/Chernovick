package org.example.hexlet.algoritms.trees.rbtree;

public class CountBlackNodesSolution {
    public static int countBlackNodes(RBTreeNode rbTreeNode) {
        int length = 0;
        while (rbTreeNode != null) {
            if (!rbTreeNode.isRed) {
                length++;
            }
            rbTreeNode = rbTreeNode.left;
        }
        return length - 1;
    }
}
