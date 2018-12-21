package view;

import model.Fighter;

public class AnimatedFighter extends  AnimatedObject {

    private Fighter fighter;
    private Animation moveAnimation;
    private Animation attackAnimation;
    private Animation defeatedAnimation;
    private Animation defendAnimation;

    //TODO konstruktor nur für testzwecke
    public AnimatedFighter(Fighter fighter, Animation standardAnimation) {
        super(standardAnimation);
        this.fighter = fighter;
    }

    public AnimatedFighter(Fighter fighter, Animation standardAnimation, Animation moveAnimation, Animation attackAnimation, Animation defeatedAnimation, Animation defendAnimation) {
        super(standardAnimation);
        this.fighter = fighter;
        this.moveAnimation = moveAnimation;
        this.attackAnimation = attackAnimation;
        this.defeatedAnimation = defeatedAnimation;
        this.defendAnimation = defendAnimation;
    }

    public Fighter getFighter() {
        return fighter;
    }

    public void setFighter(Fighter fighter) {
        this.fighter = fighter;
    }

    public Animation getMoveAnimation() {
        return moveAnimation;
    }

    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    public Animation getDefeatedAnimation() {
        return defeatedAnimation;
    }

    public Animation getDefendAnimation() {
        return defendAnimation;
    }
}
