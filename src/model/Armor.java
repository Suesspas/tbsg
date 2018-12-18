package model;

public class Armor extends Item {

    double defense;

    public Armor(String name, String description, double defense) {
        super(name, description);
        this.defense = defense;
    }
}
