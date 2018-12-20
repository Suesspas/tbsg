package view;

public class AnimatedFighter extends  AnimatedObject {

    private Animation moveAnimation;
    private Animation attackAnimation;
    private Animation defeatedAnimation;
    private Animation defendAnimation;

    public AnimatedFighter(Animation standardAnimation) {
        super(standardAnimation);
    }

    public AnimatedFighter(Animation standardAnimation, Animation moveAnimation, Animation attackAnimation, Animation defeatedAnimation, Animation defendAnimation) {
        super(standardAnimation);
        this.moveAnimation = moveAnimation;
        this.attackAnimation = attackAnimation;
        this.defeatedAnimation = defeatedAnimation;
        this.defendAnimation = defendAnimation;
    }
}
