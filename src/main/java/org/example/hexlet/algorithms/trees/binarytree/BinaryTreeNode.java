package org.example.hexlet.algorithms.trees.binarytree;

import java.util.ArrayDeque;
import java.util.Deque;

/*
Бинарное дерево или двоичное дерево — это дерево, в котором у каждого из его узлов не более двух дочерних узлов.
При этом каждый дочерний узел тоже представляет собой бинарное дерево.
 */
class BinaryTreeNode { // узел бинарного дерева
    public BinaryTreeNode left = null; // ссылка на левого потомка узла
    public BinaryTreeNode right = null;// ссылка на правого потомка узла
    public BinaryTreeNode parent; // ссылка на родительский узел
    public int value; // значение, хранящееся в узле

    /*
        Конструктор класса BinaryTreeNode принимает два параметра:
        value - значение, которое будет храниться в узле
        parent - ссылка на родительский узел
     */

    BinaryTreeNode(int value, BinaryTreeNode parent) {
        this.parent = parent;
        this.value = value;
    }

    /* Поиск узла
    1) Создается переменная node и инициализируется текущим узлом (this).
    2) В цикле while происходит поиск узла с заданным значением:
        Если значение узла равно заданному значению, возвращается найденный узел.
        Если значение узла больше заданного значения, переходим к левому потомку узла.
        Если значение узла меньше заданного значения, переходим к правому потомку узла.
        Если узел равен null, значит узел с заданным значением не найден, возвращается null.
    3) Возвращается null, если узел с заданным значением не найден.
    */
    BinaryTreeNode findNode(int value) {
        BinaryTreeNode node = this;
        while (node != null) {
            if (value == node.value) {
                return node;
            }
            if (value < node.value) {
                node = node.left;
            }
            if (value > node.value) {
                node = node.right;
            }
        }

        return null;
    }

    /* Вставка узла
        Метод insertNode(int value) используется для вставки нового узла со значением value в бинарное дерево.
        Он вызывает приватный метод insertNode(int value, BinaryTreeNode parentNode),
        который выполняет рекурсивную вставку узла.
     */

    public void insertNode(int value) {
        insertNode(value, this);
    }

    /* Вставка узла
        В методе insertNode(int value, BinaryTreeNode parentNode) сначала проверяется, меньше ли
        значение value значения узла parentNode. Если да, то проверяется, есть ли у parentNode
        левый потомок. Если нет, то создается новый узел с значением value и ссылкой на parentNode
        в качестве родителя, и этот узел становится левым потомком parentNode. Если левый потомок
        уже существует, то рекурсивно вызывается insertNode для левого потомка.

        Затем проверяется, больше ли значение value значения узла parentNode. Если да, то проверяется,
        есть ли у parentNode правый потомок. Если нет, то создается новый узел с значением value и
        ссылкой на parentNode в качестве родителя, и этот узел становится правым потомком parentNode.
        Если правый потомок уже существует, то рекурсивно вызывается insertNode для правого потомка.

        Таким образом, метод insertNode позволяет вставлять новые узлы в бинарное дерево
        в соответствии с их значениями.
     */
    private void insertNode(int value, BinaryTreeNode parentNode) {
        if (value < parentNode.value) {
            if (parentNode.left == null) {
                parentNode.left = new BinaryTreeNode(value, parentNode);
            } else {
                insertNode(value, parentNode.left);
            }
        }
        if (value > parentNode.value) {
            if (parentNode.right == null) {
                parentNode.right = new BinaryTreeNode(value, parentNode);
            } else {
                insertNode(value, parentNode.right);
            }
        }
    }

    /* Удаление узла
    Чтобы удалить элемент в связном списке, нужно найти его и ссылку на следующий элемент перенести в поле
    ссылки на предыдущем элементе.
    Если необходимо удалить корневой узел или промежуточные вершины и сохранить структуру бинарного дерева поиска,
    выбирают один из следующих двух способов:
        Находим и удаляем максимальный элемент левого поддерева и используем его значение в качестве корневого
        или промежуточного узла
        Находим и удаляем минимальный элемент правого поддерева и используем его значение в качестве корневого
        или промежуточного узла */

    public void removeNode(int value) {
        this.removeNode(value, this);
    }

    private BinaryTreeNode removeNode(int value, BinaryTreeNode node) {
        if (node == null) {
            return null; // если переданный узел значит null , то вернем null , так как нет узла для удаления
        }

        if (value < node.value) { // елси значение меньше текущего узла, то рекурсивно вызывается метод removeNode для левого поддерева
            node.left = removeNode(value, node.left);
        } else if (value > node.value) { // Если значение больше значения текущего узла, то рекурсивно вызывается метод removeNode для правого поддерева.
            node.right = removeNode(value, node.right);
        } else {
            if (node.left == null) { // Если левое поддерево отсутствует, то возвращается правое поддерево.
                return node.right;
            }
            if (node.right == null) { // Если правое поддерево отсутствует, то возвращается левое поддерево.
                return node.left;
            }
        }
        /*
        Если оба поддерева присутствуют, то находится наименьший узел в правом поддереве (узел с наименьшим значением,
        большим значения текущего узла). Затем правое поддерево этого узла удаляется рекурсивным вызовом removeNode,
        а левое поддерево остается без изменений.
         */
        BinaryTreeNode original = node;
        node = node.right;
        while (node.left != null) {
            node = node.left;
        }
        node.right = removeNode(value, original.right);
        node.left = original.left;
        return node.right; //В конце метода возвращается правое поддерево.
    }

    /*
        Метод traverseRecursive осуществляет рекурсивный обход дерева. Он вызывает приватный вспомогательный метод
        traverseRecursive, который принимает узел в качестве аргумента. Если узел не равен null, то выводится значение
        узла, а затем рекурсивно вызывается метод traverseRecursive для левого и правого поддерева.
     */
    public void traverseRecursive() {
        traverseRecursive(this);
    }

    private void traverseRecursive(BinaryTreeNode node) {
        if (node != null) {
            System.out.println("node = " + node.value);
            traverseRecursive(node.left);
            traverseRecursive(node.right);
        }
    }

    /*
        Метод traverseWithStack осуществляет обход дерева с использованием стека. Вначале создается пустой стек и в
        него помещается корневой узел. Затем выполняется цикл, пока стек не станет пустым. На каждой итерации
        извлекается узел из вершины стека, выводится его значение, а затем, если у узла есть правый и/или левый
        потомок, они добавляются в стек.
     */

    public void traverseWithStack() {
        Deque<BinaryTreeNode> stack = new ArrayDeque<>();
        stack.push(this);
        while (stack.size() > 0) {
            BinaryTreeNode currentNode = stack.pop();

            System.out.println("node = " + currentNode.value);

            if (currentNode.right != null) {
                stack.push(currentNode.right);
            }
            if (currentNode.left != null) {
                stack.push(currentNode.left);
            }
        }
    }
    /*
        Метод traverseWithQueue осуществляет обход дерева с использованием очереди. Вначале создается пустая очередь
        и в нее помещается корневой узел. Затем выполняется цикл, пока очередь не станет пустой. На каждой итерации
        извлекается узел из начала очереди, выводится его значение, а затем, если у узла есть левый и/или правый
        потомок, они добавляются в конец очереди.
     */

    public void traverseWithQueue() {
        Deque<BinaryTreeNode> queue = new ArrayDeque<>();
        queue.push(this);
        while (queue.size() > 0) {
            BinaryTreeNode currentNode = queue.removeFirst();
            System.out.println("node" + currentNode.value);
            if (currentNode.left != null ){
                queue.push(currentNode.left);
            }
            if (currentNode.right != null) {
                queue.push(currentNode.right);
            }
        }
    }
}
