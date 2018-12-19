package model;

public class FieldTile {

    private Fighter fighter;
    //sonstige dinge wie umwelt effekte oder mana wie in duelyst einfügbar


    public FieldTile() {

    }

    public FieldTile(Fighter fighter) {
        this.fighter = fighter;
    }

    public Fighter getFighter() {
        return fighter;
    }
}
