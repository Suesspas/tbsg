package view;

import model.Fighter;
import model.PlayerType;

import javax.swing.*;
import java.awt.*;

public class FieldPartView extends JPanel{
    private static final long serialVersionUID = 1L;
    private PlayerType owner;
    private Fighter fighter;
    AnimatedObject animatedObject;

    public FieldPartView(Fighter fighter) {
        super(new BorderLayout());
        //animatedObject = new AnimatedFighter(null);
        //animatedObject.getCurrentSprite();
        if (fighter == null) {
            owner = PlayerType.Nobody;
        } else {
            this.owner = fighter.getOwner();
        }
        this.fighter = fighter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.LIGHT_GRAY);
        double scale = 0.6; //scale size of cycle between 0 and 1
        int start =  (this.getWidth() / 2) - (int)(this. getWidth() / 2 * scale);
        int len = this.getWidth() - (2 * start);
        if (owner == PlayerType.Human) {
            //setForeground(Color.BLUE);
            //g.setColor(Color.BLUE);
            //g.fillOval(start, start, len, len);
            g.setColor(Color.RED);
            drawHPbar(g, start, len);
            g.drawImage(fighter.getCurrentSprite(), len/6, -len/5, this.getWidth()-len/5, this.getHeight()-len/10,null);
        } else if (owner == PlayerType.Bot){
            //setForeground(Color.RED);
            //g.fillOval(start, start, len, len);
            g.setColor(Color.RED);
            drawHPbar(g, start, len);
            g.drawImage(fighter.getCurrentSprite(), 0, -len/5, this.getWidth()-len/5, this.getHeight()-len/10,null);
        }
    }

    private void drawHPbar(Graphics g, int start, int len) {
        double hpPercent = fighter.getHp()/fighter.getMaxHp();
        g.fillRect(start, start + len + len/10, (int)(len*hpPercent), len*23/100);
        g.setColor(Color.BLACK);
        g.drawRect(start, start + len + len/10, len, len*23/100);
        g.setFont(new Font("TimesRoman", Font.BOLD, this.getWidth()/8));
        g.drawString("HP: " + fighter.getHp(), start,start+len+(3* len/10));
    }

    public PlayerType getOwner() {
        return owner;
    }

    public void setOwner(PlayerType owner) {
        this.owner = owner;
    }
}
