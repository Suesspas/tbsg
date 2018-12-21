package view;

import java.awt.image.BufferedImage;

public abstract class AnimatedObject {
    private Animation standardAnimation;
    private BufferedImage currentSprite;

    public AnimatedObject(Animation standardAnimation) {
        this.standardAnimation = standardAnimation;
        currentSprite = standardAnimation.getCurrentFrame();
    }

    public Animation getStandardAnimation() {
        return standardAnimation;
    }

    public void setStandardAnimation(Animation standardAnimation) {
        this.standardAnimation = standardAnimation;
    }

    public BufferedImage getCurrentSprite() {
        return currentSprite;
    }
}
