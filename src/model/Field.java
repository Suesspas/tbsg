package model;

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
                field[6][col] = new FieldTile(player.getFighter(6));
                field[0][col] = new FieldTile(player.getFighter(5));
            case 5:
                field[4][col] = new FieldTile(player.getFighter(4));
                field[2][col] = new FieldTile(player.getFighter(3));
            case 3:
                field[5][col] = new FieldTile(player.getFighter(2));
                field[1][col] = new FieldTile(player.getFighter(1));
            case 1:
                field[3][col] = new FieldTile(player.getFighter(0));
                break;
            case 6:
                field[5][col] = new FieldTile(player.getFighter(5));
                field[1][col] = new FieldTile(player.getFighter(4));
            case 4:
                field[6][col] = new FieldTile(player.getFighter(3));
                field[0][col] = new FieldTile(player.getFighter(2));
            case 2:
                field[4][col] = new FieldTile(player.getFighter(1));
                field[2][col] = new FieldTile(player.getFighter(0));
                break;
        }
    }

    public void setFighterToPosition(Fighter fighter, int x, int y){
        field[x][y].setFighter(fighter);
    }

    public void deleteFighterFromPosition(int x, int y){
       field[x][y].setFighter(null);
    }

    public Fighter getFighterFromPosition(int x, int y){
        return field[x][y] == null ? null : field[x][y].getFighter();
    }

}