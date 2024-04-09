package org.example;

public class Mage {
    private String name;
    private int level;

    public Mage(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Mage " + name + " level: " + level;
    }
}