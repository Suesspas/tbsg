package view;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage player;

    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/resources/images/LightBandit_Spritesheet.png"));

        player = sheet.crop(0, 0, 48, 48);
        player = flipImage(player);

    }

    private static BufferedImage flipImage(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }
}