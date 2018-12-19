package model;

import java.util.LinkedList;

/**
 * Created by Pascal on 13.12.2018.
 */
public class Game {
    private Field field;
    private Human human; //human
    private Bot bot; //bot - evtl beide als player wenn allgemein
    private int level;
    private PlayerType current; //player with current move TODO evtl in Field
    private final int maxGroupSize = 3;

    public Game(Human human, Bot bot, int level, PlayerType current) {
        this.human = human;
        this.bot = bot;
        this.level = level;
        this.current = current;
    }

    public Game(){
        Fighter stdFighter = new Fighter("Std", 10, 5, 4, 2, 1, PlayerType.Human);
        Fighter stdFighter2 = new Fighter("Std2", 10, 5, 4, 2, 1, PlayerType.Bot);
        Fighter[] stdFighters = new Fighter[maxGroupSize];
        Fighter[] stdFightersB = new Fighter[maxGroupSize];
        stdFighters[0] = stdFighter;
        stdFightersB[0] = stdFighter2;
        human = new Human(stdFighters, 50);
        bot = new Bot(stdFightersB);
        level = 0;
        current = PlayerType.Human;
        field = new Field(7, human, bot);
        //field = new Field(); TODO field entspricht im endeffekt einer view, kann also evtl weg
    }

    @Override
    public String toString() {
        String[] hFighters = new String[maxGroupSize];
        String[] bFighters = new String[maxGroupSize];
        double[] hHP = new double[maxGroupSize];
        double[] bHP = new double[maxGroupSize];
        for (int i = 0; i < maxGroupSize; i++) {
            hFighters[i] = human.getFighter(i) == null ? "" : human.getFighter(i).getName();
            bFighters[i] = bot.getFighter(i) == null ? "" : bot.getFighter(i).getName();
            hHP[i] = human.fighters[i] != null ? human.fighters[i].getHp() : 0;
            bHP[i] = bot.fighters[i] != null ? bot.fighters[i].getHp() : 0;

        }
        return  "Level: " + level + " | Turn: "+ current.getClass().getSimpleName()+" | Gold: "+ human.getGold() +"  \n"+
                "________________________________________\n"+
                "      "+hFighters[0]+"                       "+bFighters[0]+"           \n"+
                "   "+hFighters[1]+"                             "+bFighters[1]+"        \n"+
                "        "+hFighters[2]+"                        "+bFighters[2]+"        \n"+
                "________________________________________\n"+
                "Human Fighter HP   |   Bot Fighter HP   \n" +
                hHP[0] + " , " + hHP[1] + " , " + hHP[2] + "   |   " +
                bHP[0] + " , " + bHP[1] + " , " + bHP[2] +
                "                                        \n";
    }


    public void addFighterToPlayer(Fighter fighter, PlayerType playerType) {
        boolean full = true;
        Player player = playerType == PlayerType.Human ? human : bot;
        for (int i = 0; i < player.getFighters().length; i++) {
            if (player.getFighter(i) == null) {
                player.addFighter(fighter);
                if (playerType == PlayerType.Human) System.out.println("Fighter successfully added");
                full = false;
                break;
            }
        }
        if (full) System.out.println("Fighter could not be added. Group full.");
    }

    public void humanMove(){
        if (current == PlayerType.Human){

        } else {
            System.out.println("It is not your turn");
        }
    }

    public void botMove(){

    }

    public void endTurn(){

    }
    //TODO zuerst mal human attack dann rest
    //TODO abrprüfen der ints obs die arrays auch groß genug sind
    public void humanAttack(Fighter atk, Fighter def){
        if (atk == null || def == null) {
            throw new IllegalArgumentException("Positions not defined");
        }
        double damage = calculateDamage(atk, def);
        dealDamageToFighter(damage, def);
    }

    //evtl hp < 0 zulassen
    public void dealDamageToFighter(double damage, Fighter fighter){
        if (damage >= fighter.getHp()){
            damage = fighter.getHp();
            fighter.setDefeated(true);
            System.out.println(fighter.getName() + " has been defeated.");
        }
        fighter.setHp(fighter.getHp() - damage);
        System.out.println("The attack did " + damage + " damage.");
    }

    public void block(){

    }
    //TODO Klasse Attack für verschiedene Arten von Angriffen, z.B. DoT, AoE
    public double calculateDamage(Fighter attacker, Fighter defender){
        double damage = attacker.getBaseAtk() - defender.getBaseDef();
        return damage < 0 ? 0 : damage;
    }

    public PlayerType getPlayerFromPosition(int x, int y) {
        Fighter fighter = field.getFighterFromPosition(x, y);
        return fighter == null ? PlayerType.Nobody : fighter.getOwner();
    }

    public Fighter getFighterFromPosition(int x, int y) {
        return field.getFighterFromPosition(x, y);
    }

    //TODO abprüfung auf maximale Laufreichweite
    public void move(Fighter fighter, int x, int y) {
        field.deleteFighterFromPosition(fighter.getxPos(), fighter.getyPos());
        field.setFighterToPosition(fighter, x, y);
    }

    public Game machineMove() {
        return this;
    }

    //Getter und Setter
    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Player getP1() {
        return human;
    }

    public void setP1(Human human) {
        this.human = human;
    }

    public Player getP2() {
        return bot;
    }

    public void setP2(Bot bot) {
        this.bot = bot;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public PlayerType getCurrent() {
        return current;
    }

    public void setCurrent(PlayerType current) {
        this.current = current;
    }



}
