package view;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage player;
    public static BufferedImage enemy;
    public static BufferedImage[] playerIdle;
    public static BufferedImage[] enemyIdle;

    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/resources/images/LightBandit_Spritesheet.png"));
        SpriteSheet sheet2 = new SpriteSheet(ImageLoader.loadImage("/resources/images/HeavyBandit_Spritesheet.png"));
        player = sheet.crop(0, 0, 48, 48);
        player = flipImage(player);
        enemy = sheet2.crop(0, 0, 48, 48);

        playerIdle = new BufferedImage[4];
        enemyIdle = new BufferedImage[4];
        int size = 48;
        for (int i = 0; i < playerIdle.length; i++) {
            playerIdle[i] = flipImage(sheet.crop(size*(i+4), 0, 48, 48));
            enemyIdle[i] = sheet2.crop(size*(i+4), 0, 48, 48);
        }

    }

    private static BufferedImage flipImage(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }
}
