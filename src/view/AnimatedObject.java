package view;

public abstract class AnimatedObject {
    private Animation standardAnimation;

    public AnimatedObject(Animation standardAnimation) {
        this.standardAnimation = standardAnimation;
    }

    public Animation getStandardAnimation() {
        return standardAnimation;
    }

    public void setStandardAnimation(Animation standardAnimation) {
        this.standardAnimation = standardAnimation;
    }
}
