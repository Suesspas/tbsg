package model;

import javafx.util.Pair;

import java.util.LinkedList;

public class Range {
    private LinkedList<Pair<Integer,Integer>> inRange;

    public Range(int standardRange) {
        inRange = new LinkedList<>();

        for (int i = -standardRange; i <= standardRange; i++) {
            for (int j = -standardRange; j <= standardRange; j++) {
                inRange.add(new Pair<>(i, j));
            }
        }
    }

    public LinkedList<Pair<Integer, Integer>> getInRange() {
        return inRange;
    }

    public boolean isInRange(int x, int y){
        return inRange.contains(new Pair<>(x, y));
    }

    public void addToRange(Pair<Integer, Integer> range) {
        this.inRange.add(range);
    }

    public void removeFromRange(LinkedList<Pair<Integer, Integer>> inRange) {
        this.inRange.remove(inRange);
    }
}
