package model;

/**
 * Created by Pascal on 13.12.2018.
 */
public abstract class Player {
        Fighter[] fighters;
        int currentGroupSize = 0;
        PlayerType playerType;

        public Fighter[] getFighters() {
                return fighters;
        }

        public Player(Fighter[] fighters, PlayerType playerType) {
                if (playerType == PlayerType.Human || playerType == PlayerType.Bot) {
                        this.playerType = playerType;
                } else {
                        throw new IllegalArgumentException("Player has to be human or bot");
                }
                for (Fighter f : fighters) {
                        if (f != null) {
                                if (f.getOwner() != playerType) {
                                        throw new IllegalArgumentException();
                                }
                                currentGroupSize++;
                        }
                }
                this.fighters = fighters;
        }

        //TODO abprüfen ob auch bei leerem array funzt
        public Fighter getFighter(int i) {
                if (fighters.length < i){
                        return null;
                }
                else {
                        return fighters[i];
                }
        }

        public void addFighter(Fighter fighter) {
                boolean added = false;
                for (int i = 0; i < fighters.length; i++) {
                        if (fighters[i] == null){
                                fighters[i] = fighter;
                                currentGroupSize++;
                                added = true;
                                break;
                        }
                }
                if (!added){
                        throw new RuntimeException("Fighter could not be added");
                }
        }

        public int getCurrentGroupSize() {
                return currentGroupSize;
        }

        public PlayerType getPlayerType() {
                return playerType;
        }
}
