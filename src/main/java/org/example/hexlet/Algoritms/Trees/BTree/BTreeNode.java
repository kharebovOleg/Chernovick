package org.example.hexlet.Algoritms.Trees.BTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
Основная идея сильноветвящихся деревьев заключается в том, чтобы увеличить количество веток в узлах и хранить
несколько ключей полезной нагрузки. В каждом узле дерева хранится не одно значение, а отсортированный список
значениий (страница, блок).
 */

public class BTreeNode {
    public boolean leaf; // Флаг показывает, что текущий узел является листовым
    public List<Integer> keys; // Массив ключей (полезной нагрузки) узла (блок, страница)
    public List<BTreeNode> children; // Массив дочерних узлов

    public BTreeNode(List<Integer> keys) {
        this.leaf = false;
        this.keys = keys;
        this.children = new ArrayList<>();
    }

    // поиск значения в би-дереве
    public BTreeNode findNode(Integer value) {
        BTreeNode node = this;
        while (node != null) { // Проходимся по всем узлам
            for (var i = 0; i < node.keys.size(); i++) { // Проверяем ключи в узле
                if (Objects.equals(value, node.keys.get(i))) {
                    return node; // Если нашли совпадение, возвращаем текущий узел
                }
                if (value < node.keys.get(i)) { // Если ключ больше искомого, переходим по соответствующей ветке дерева
                    if (!node.leaf) {
                        node = node.children.get(i);
                        break;
                    } else { // Если мы уже в листовом узле, переходить некуда. Значения у нас нет
                        return null;
                    }
                } else if (node.keys.size() == i + 1 && !node.leaf) {
                    // Если искомое значение больше последнего ключа, переходим по последней ссылке
                    node = node.children.get(i + 1);
                    break;
                }
            }
        }
        return null;
    }


}
