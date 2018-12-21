package data;

import model.Fighter;
import model.PlayerType;
import view.AnimatedFighter;
import view.Animation;
import view.Assets;

import java.util.HashMap;
import java.util.Map;

//TODO Langfristig durch Datenbank ersetzen
public final class FighterData {
    private static HashMap<String, AnimatedFighter> animatedFighters;
    private static final int timeTillNextFrame = 400;

    private FighterData() {
    }

    //Init all fighters for the game
    public static void init(){
        Fighter stdFighter = new Fighter("Std", 10, 5, 4, 2, 1, PlayerType.Human);
        Fighter stdFighter2 = new Fighter("Std2", 10, 5, 4, 2, 1, PlayerType.Bot);

        Animation stdFighterIdle = new Animation(timeTillNextFrame, Assets.playerIdle);
        Animation stdFighter2Idle = new Animation(timeTillNextFrame, Assets.enemyIdle);

        AnimatedFighter stdAnFighter = new AnimatedFighter(stdFighter, stdFighterIdle);
        AnimatedFighter stdAnFighter2 = new AnimatedFighter(stdFighter2, stdFighter2Idle);

        animatedFighters.put(stdFighter.getUniqueID(), stdAnFighter);
        animatedFighters.put(stdFighter2.getUniqueID(), stdAnFighter2);
    }

    public static Fighter getFighter(String id) {
        AnimatedFighter aF = animatedFighters.get(id);
        return aF == null ? null : aF.getFighter();
    }

    public static AnimatedFighter getAnimatedFighter(String id) {
        return animatedFighters.get(id);
    }

    /*public static void insertAnimatedFighter(AnimatedFighter animatedFighter) {
        fighters.put(animatedFighter.getFighter().getUniqueID(), animatedFighter);
    }

    public static void deleteFighter(HashMap<String, AnimatedFighter> fighters) {
        FighterData.fighters = fighters;
    }*/

    public static HashMap<String, AnimatedFighter> getAnimatedFighters() {
        return animatedFighters;
    }

    public static HashMap<String, Fighter> getFighters() {
        HashMap<String, Fighter> fighters = new HashMap<>();
        for(Map.Entry<String, AnimatedFighter> entry : animatedFighters.entrySet()) {
            String key = entry.getKey();
            Fighter value = entry.getValue().getFighter();
            fighters.put(key, value);
        }
        return fighters;
    }

    //TODO thread safety prüfen
    public static void updateAnimatedFighter(AnimatedFighter animatedFighter){
        if (animatedFighters.containsKey(animatedFighter.getFighter().getUniqueID())){
            animatedFighters.replace(animatedFighter.getFighter().getUniqueID(), animatedFighter);
        } else {
            System.out.println("fighter not in database");
        }
    }
    //TODO thread safety prüfen
    public static void updateFighter(Fighter fighter){
        if (animatedFighters.containsKey(fighter.getUniqueID())){
            AnimatedFighter animatedFighter = animatedFighters.get(fighter.getUniqueID());
            animatedFighter.setFighter(fighter);
            animatedFighters.replace(fighter.getUniqueID(), animatedFighter);
        } else {
            System.out.println("fighter not in database");
        }
    }
}
