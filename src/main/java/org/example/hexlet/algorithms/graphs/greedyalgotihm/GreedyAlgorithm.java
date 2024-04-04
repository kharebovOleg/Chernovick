package org.example.hexlet.algorithms.graphs.greedyalgotihm;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class GreedyAlgorithm {

    /*
    Реализация жадного алгоритма

    Задача выбрать собрать сумку предметами из списка, так чтобы ценность предметов в сумке была максимальной,
    сумка имеет предельный вес

    Чтобы жадный алгоритм выбирал самые ценные предметы, нужно сравнивать не абсолютную, а удельную стоимость — то есть
    стоимость одного килограмма. Как и метод перебора, жадный алгоритм рекурсивно двигается по неявному графу, но на
    каждом шаге он выбирает одну ветвь — с наибольшей удельной стоимостью.
     */

    // метод backpack принимает в себя список вещей, и максимальный вес сумки.

    // 1) сортируем список по стоимости, т.е. по цене / вес, от большей стоимости к меньшей;
    // 2) проходимся по отсортированному списку и добавляем вещи пока рюкзак не переполнится;
    // 3) создаем hashmap с ключами: "наименование", "суммарная стоимость", "суммарный вес", и соответствующими значениями.
    public static Map<String, Object> backpack(List<Worth> worths, int maxWeight) {
        var worthsCopy = new ArrayList<>(worths); // создаем копию переданного списка

        worthsCopy.sort((item1, item2) -> { // сортируем по стоимости (цена / вес)
            var unitPrice1 = item1.getPrice() / item1.getWeight();
            var unitPrice2 = item2.getPrice() / item2.getWeight();
            return Double.compare(unitPrice2, unitPrice1);
        });

        var sumPrice = 0.0; // итоговая цена
        var sumWeight = 0.0; // итоговый вес
        var names = new ArrayList<String>(); // список названий вещей

        for (Worth item : worthsCopy) { // проходимся по отсортированному списку
            var itemWeight = item.getWeight();
            var itemPrice = item.getPrice();

            if (sumWeight + itemWeight > maxWeight) {
                break; // если при добавлении вещи в сумку превышен максимальный вес, то выходим из цикла
            }

            sumPrice += itemPrice; // добавляем стоимость вещий в общий зачет
            sumWeight += itemWeight; // и вес тоже
            names.add(item.getName()); // добавляем название в соответствующий список
        }

        return Map.of( // возвращаем hashmap, с соответствующими ключами и значениями
                "names", names,
                "sumPrice", sumPrice,
                "sumWeight", sumWeight
        );
    }

    public static Map<String, Object> run(List<Map<String, Object>> coll, int maxWeight) {
        return Helper.run(coll, maxWeight);
    }
}
