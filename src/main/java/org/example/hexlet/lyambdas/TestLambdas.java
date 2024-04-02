package org.example.hexlet.lyambdas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TestLambdas {

    public void testLambda(Runnable r) {
        r.run();
    }

    public String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br =
                new BufferedReader(new FileReader("src/main/resources/data.txt"))){
            return p.process(br);
        }
    }

    // Predicate
    public <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T t:list) {
            if (p.test(t)) {
                results.add(t);
            }
        }
        return results;
    }

    //Consumer
    public <T> void forEach(List<T> list, Consumer<T> c) {
        for (T t:list) {
            c.accept(t);
        }
    }

    //Function
    public <T,R> List<R> map(List<T> list, Function<T,R> f) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }
}
