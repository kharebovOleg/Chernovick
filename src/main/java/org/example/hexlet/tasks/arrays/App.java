package org.example.hexlet.tasks.arrays;

public class App {
    /*
    Реализуйте публичный статический метод getLastWordLength(), который возвращает длину последнего слова переданной
    на вход строки. Словом считается любая последовательность, не содержащая пробелов.
     */

    public static int getLastWordLength(String str){
        String[] array = str.trim().split(" ");

        return array[array.length - 1].length();
    }


}
