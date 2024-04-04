package org.example.hexlet.algorithms.graphs.greedyalgotihm;

public final class Worth { // worth - ценность
    private String name; // название
    private double weight; // вес
    private double price; // цена

    public Worth(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }
}
