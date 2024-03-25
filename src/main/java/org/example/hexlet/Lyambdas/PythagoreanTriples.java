package org.example.hexlet.Lyambdas;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PythagoreanTriples {
    public Stream<int[]> pythagoreanTriples =
            IntStream.rangeClosed(1, 100).boxed()
                    .flatMap(a ->
                            IntStream.rangeClosed(a, 100)
                                    .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                    .mapToObj(b ->
                                            new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                    );

    public void fibonachiNumbers() {
        Stream.iterate(
                        new int[]{0, 1},  // первый параметр iterate - начальное значение

                        t -> t[1] < 100, /* второй параметр (не обязательный) - остановить выполнение как только t[1]
                         будет >= 100 */

                        t -> new int[]{t[1], t[0] + t[1]} /* третий параметр iterate - шаг: массив где первый элемент
                         - это второй элемент предыдущего массива, а второй эелемент - это сумма двух элементов
                          предыдущего массива */
                )
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));
    }
}


