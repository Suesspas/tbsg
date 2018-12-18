package model;


/**
 * Field, where the Fighters are having their battle
 *
 */
//TODO Klasse überhaupt notwendig? evtl direkt in Game
    //TODO Klasse nicht mehr notwendig, da fighter in players abgespeichert werden und field mit view angezeigt wird
    //TODO evtl nur notwendig wenn bei verschiedenen Ebenen neue Attribute abgespeichert werden müssen
public class Field {

    private int maxGroupSize;
    private Fighter[] p1Group = new Fighter[maxGroupSize];
    private Fighter[] p2Group = new Fighter[maxGroupSize];
    //private int level;

    public Field(int maxGroupSize, Fighter[] p1Group, Fighter[] p2Group, int level) {
        this.maxGroupSize = maxGroupSize;
        this.p1Group = p1Group;
        this.p2Group = p2Group;
        //this.level = level;
    }

}