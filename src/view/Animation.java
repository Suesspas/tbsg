package view;

import java.awt.image.BufferedImage;

public class Animation {
    private int timeTillNextFrame;
    private int index;
    private BufferedImage[] frames;
    private long lastTime, timer;

    public Animation(int timeTillNextFrame, BufferedImage[] frames) {
        this.timeTillNextFrame = timeTillNextFrame;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void tick(){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (timer > timeTillNextFrame){
            index++;
            timer = 0;
            if (index >= frames.length){
                index = 0;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
}
