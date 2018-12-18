package model;

/**
 * Created by Pascal on 13.12.2018.
 */
public class Bot extends Player {
    public Bot(Fighter[] fighters) {
        super(fighters);
    }

    public void setFighters(Fighter[] fighters) {
        this.fighters = fighters;
    }

    public void setFighter(Fighter fighter, int i) {
        fighters[i] = fighter;
    }
}