package model;

public class Weapon extends Item {

    double damage;

    public Weapon(String name, String description, double damage) {
        super(name, description);
        this.damage = damage;
    }
}
