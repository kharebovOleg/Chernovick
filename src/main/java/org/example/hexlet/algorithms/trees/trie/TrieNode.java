package org.example.hexlet.algorithms.trees.trie;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/*
Этот код представляет собой реализацию структуры данных Trie на языке Java. Trie (или префиксное
дерево) - это древовидная структура данных, которая используется для хранения и поиска строк.

Класс Trie имеет следующие поля:
 */

public class TrieNode {

    char value; // символ узла (значение)

    // не инициализируется children, для экономии памяти, но придется добавлять проверки на null в методах
    List<TrieNode> children; // список потомков

    public TrieNode(char value) {
        this.value = value;
    }

    /*
    метод вставки в префиксное дерево
     */
    public void insert(String data) {
        if (data.length() == 0) { // если вставляется пустая строка вернем ничего не делаем
            return;
        }
        if (children == null) { // если у узла нет потомков - создаем
            children = new ArrayList<>();
        }
        char c = data.charAt(0); // берем первый символ слова
        TrieNode child = findNodeByChar(c); // изем узел дерева, содержащий такой символ
        if (child == null) { // если такого нет - создаем, если есть ничего не делаем
            child = new TrieNode(c);
            children.add(child);
        }
        child.insert(data.substring(1)); // повторяем действия для следующего символа

    }

    // поиск узла, содержащего заданный символ
    private TrieNode findNodeByChar(char c) {
        if (children != null) {
            for (TrieNode trieNode : children) {
                if (trieNode.value == c) {
                    return trieNode;
                }
            }
        }
        return null;
    }

    // есть ли слово в дереве
    public boolean containsString(String str) {
        TrieNode current = this;
        for (int i = 0; i < str.length(); i++) {
            current = current.findNodeByChar(str.charAt(i));
            if (current == null) {
                return false;
            }
        }
        return true;
    }

    public void getAllNumbers(String path, List<String> result) { // path - текущий путь, который мы прошли
        // result - список строк куда мы будем найденный строки сохранять
        if (value != ' ') { // если узел не корневой (в корневом будет ' ')
            path = path + value; // к пути прибавляем текущеее значение
        }
        if (children != null) { // повторяем все для всех потомков
            for (TrieNode node :
                    children) {
                node.getAllNumbers(path, result);
            }
        } else { // дошли до листа (крайнего узла) и добавляем в список
            result.add(path);
        }
    }

    // запись дерева в файл
    public void writeToFile(PrintWriter writer) {
        writer.write(value); // сперва записываем значение узла
        if (children != null) { // если есть дочерние узлы
            for (TrieNode node : children) { // для каждого дочернего узла проделываем то же самое
                node.writeToFile(writer);
            }
        }
        writer.write(']'); // в конце добаляем разделитель, чтобы было понятно где заканчивается путь
    }

    // чтение дерева из файла
    public void readFromFile(FileReader reader) throws IOException {
        char ch;
        while ((ch = (char) reader.read()) != ']') { // пока не дошли до конца - ']'
            TrieNode trieNode = new TrieNode(ch); // если не конец, значит какой-то дочерний узел - создаем
            trieNode.readFromFile(reader); // вызываем у дочернего узла readFromFile
            if (children == null) { // если списка потомков не было создаем
                children = new ArrayList<>();
            }
            children.add(trieNode); // добавляем считанный узел в список дочерних узлов текущего узла
        }
    }
}
