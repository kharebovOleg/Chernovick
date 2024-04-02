package org.example.hexlet.lyambdas;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Letter {

    public static String text = "Hello, i'm learning labda";
    public static List<String> textList = List.of(text.split(" "));
    public static String addHeader(String text) {
        return "Header " + text;
    }

    public static String addFooter(String text) {
        return text + " footer";
    }

    public static String checkSpelling(String text) {
        return text.replaceAll("labda", "lambda");
    }

    public static Function<String, String> addHeader = Letter::addHeader;
    public static Function<String,String> transformationPipeline = addHeader
            .andThen(Letter::checkSpelling)
            .andThen(Letter::addFooter);
    public static List<String> readAndCheckAndTransformText(List<String> textList, Function<String, String> transformer){
        return textList.stream()
                .map(transformer)
                .collect(Collectors.toList());
    }




}
