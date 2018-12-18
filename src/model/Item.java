package model;

/**
 * Created by Pascal on 13.12.2018.
 */
public class Item {
    private String name;
    private String description;
    //TODO Aufspalten in armor, weapon, consumable etc.

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
