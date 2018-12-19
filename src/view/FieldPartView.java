package view;

import model.PlayerType;

import javax.swing.*;
import java.awt.*;

public class FieldPartView extends JPanel{
    private static final long serialVersionUID = 1L;
    private PlayerType owner;

    public FieldPartView(PlayerType owner) {
        super(new BorderLayout());
        this.owner = owner;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.LIGHT_GRAY);
        if (owner == PlayerType.Human) {
            setForeground(Color.BLUE);
            g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
        } else if (owner == PlayerType.Bot){
            setForeground(Color.RED);
            g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
        }
        /*int w = this.getWidth();
        int h = this.getHeight();
        g.fillOval(w/10, h/4, 10, 10);
        g.fillOval(w/10, h/2, 10, 10);
        g.fillOval(w/10, 3*h/4, 10, 10);

        g.fillOval(9*w/10, h/4, 10, 10);
        g.fillOval(9*w/10, h/2, 10, 10);
        g.fillOval(9*w/10, 3*h/4, 10, 10);*/

    }

    public PlayerType getOwner() {
        return owner;
    }

    public void setOwner(PlayerType owner) {
        this.owner = owner;
    }
}
