package model;

import java.util.LinkedList;

/**
 * Created by Pascal on 13.12.2018.
 */
public class Human extends Player{
    private int gold;
    private LinkedList<Item> inventory;

    public Human(Fighter[] fighters, int gold) {
        super(fighters);
        this.gold = gold;
        this.inventory = new LinkedList<>();
        Item standardWeapon = new Item("stdWeapon", "standard weapon doing standard dmg");
        Item standardArmor = new Item("stdArmor", "standard armor blocking standard dmg");
        inventory.add(standardWeapon);
        inventory.add(standardArmor);
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public LinkedList<Item> getInventory() {
        return inventory;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }




}
