package model;

/**
 * Created by Pascal on 13.12.2018.
 */
public class Fighter {
    private String name;
    //TODO wahrscheinlich int besser für hp, dmg etc
    private double hp;
    private double mp;
    private double baseAtk;
    private double baseDef;
    private int level;
    private Weapon weapon;
    private Armor armor;
    private boolean defeated = false;

    public Fighter(String name, double hp, double mp, double baseAtk, double baseDef, int level) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.level = level;
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
}
