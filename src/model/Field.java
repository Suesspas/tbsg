package model;

import java.awt.*;

/**
 * Field, where the Fighters are having their battle
 *
 */
public class Field {

    private FieldTile[][] field;


    public Field(int size, Player human, Player bot) {
        if (size % 2 == 0 || size != 7){
            throw new IllegalStateException("Fieldsize has to be an odd number (7)");
        }

        if (human.currentGroupSize > 7 || bot.currentGroupSize > 7){
            throw new IllegalArgumentException("Maximum 7 fighters per player at the start of battle allowed.");
        }
        field = new FieldTile[size][size];
        setFighterDistribution(human);
        setFighterDistribution(bot);
    }

    //TODO variable größe
    private void setFighterDistribution(Player player) {
        int col = player.getPlayerType() == PlayerType.Human ? 1 : field.length-2 ;
        switch (player.currentGroupSize) {
            case 7:
                setFighterToPosition(player.getFighter(6), 6, col);
                setFighterToPosition(player.getFighter(5), 0, col);
            case 5:
                setFighterToPosition(player.getFighter(4), 4, col);
                setFighterToPosition(player.getFighter(3), 2, col);
            case 3:
                setFighterToPosition(player.getFighter(2), 5, col);
                setFighterToPosition(player.getFighter(1), 1, col);
            case 1:
                setFighterToPosition(player.getFighter(0), 3, col);
                break;
            case 6:
                setFighterToPosition(player.getFighter(5), 5, col);
                setFighterToPosition(player.getFighter(4), 1, col);
            case 4:
                setFighterToPosition(player.getFighter(3), 6, col);
                setFighterToPosition(player.getFighter(2), 0, col);
            case 2:
                setFighterToPosition(player.getFighter(1), 4, col);
                setFighterToPosition(player.getFighter(0), 2, col);
                break;
        }
    }

    public void setFighterToPosition(Fighter fighter, int x, int y){
        field[x][y] = new FieldTile(fighter);
        fighter.setxPos(x);
        fighter.setyPos(y);
    }

    public void deleteFighterFromPosition(int x, int y){
        field[x][y] = null;
    }

    public Fighter getFighterFromPosition(int x, int y){
        return field[x][y] == null ? null : field[x][y].getFighter();
    }

}