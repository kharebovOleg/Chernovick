package org.example.hexlet.algorithms.trees.trie;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/tries/numbers.txt"));

        TrieNode root = new TrieNode(' ');
        for (String line : lines) {
            root.insert(line);
        }

        // System.out.println(root.containsString("18АР2970"));

        writeTrieToFile("src/main/resources/tries/out.txt", root);

        TrieNode fromFile = readFromFile("src/main/resources/tries/out.txt");

        List<String> extractedFromTrie = new ArrayList<>();
        fromFile.getAllNumbers("", extractedFromTrie);
    }

    public static void writeTrieToFile(String path, TrieNode root) {
        try {
            PrintWriter out = new PrintWriter(path);
            root.writeToFile(out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static TrieNode readFromFile(String path) {
        TrieNode root = new TrieNode(' '); // создаем корневой узел
        try {
            FileReader reader = new FileReader(path); // создаем fileReader
            reader.read(); // пропускаем первый символ из файла
            root.readFromFile(reader);
            reader.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
        return root;
    }
}
