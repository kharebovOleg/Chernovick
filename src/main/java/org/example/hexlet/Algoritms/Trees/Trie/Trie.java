package org.example.hexlet.Algoritms.Trees.Trie;

import java.util.HashMap;
import java.util.Map;

/*
Этот код представляет собой реализацию структуры данных Trie на языке Java. Trie (или префиксное
дерево) - это древовидная структура данных, которая используется для хранения и поиска строк.

Класс Trie имеет следующие поля:
 */

//todo поработать над эти материалом в hexlet непонятно написано
public class Trie {
    public String key;
    public Map<String, Trie> children;
    public boolean end;

    public Trie(String key) {
        this.key = key;
        this.children = new HashMap<>();
        this.end = false;
    }


}
