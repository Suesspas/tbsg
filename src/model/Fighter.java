package model;

import javafx.util.Pair;

import java.awt.*;

/**
 * Created by Pascal on 13.12.2018.
 */
public class Fighter {
    private String name;
    //TODO wahrscheinlich int besser für hp, dmg etc
    private final double maxHp;
    private double hp;
    private final double maxMp;
    private double mp;
    private double baseAtk;
    private double baseDef;
    private int level;
    private Weapon weapon;
    private Armor armor;
    private boolean defeated = false;
    private PlayerType owner;
    private int xPos;
    private int yPos;

    public Fighter(String name, double hp, double mp, double baseAtk, double baseDef, int level, PlayerType owner) {
        this.name = name;
        this.maxHp = hp;
        this.hp = hp;
        this.maxMp = mp;
        this.mp = mp;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.level = level;
        this.owner = owner;
        xPos = -1;
        yPos = -1;
    }

    public String getName() {
        return name;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        if (hp < 0) {
            this.hp = 0;
            throw new IllegalArgumentException("hp cant be negative");
        }
        this.hp = hp;
    }

    public double getMp() {
        return mp;
    }

    public void setMp(double mp) {
        this.mp = mp;
    }

    public double getBaseAtk() {
        return baseAtk;
    }

    public void setBaseAtk(double baseAtk) {
        this.baseAtk = baseAtk;
    }

    public double getBaseDef() {
        return baseDef;
    }

    public void setBaseDef(double baseDef) {
        this.baseDef = baseDef;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public PlayerType getOwner() {
        return owner;
    }

    public void setOwner(PlayerType owner) {
        this.owner = owner;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public double getMaxMp() {
        return maxMp;
    }
}
