package game;

import objects.Earth;
import objects.GameObject;
import utilities.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static game.Constants.*;

/*
    The view of the Game
 */
public class View extends JComponent {

    private Game game;
    Image im0 = Sprite.SPACE, im1 = Sprite.EARTH;
    AffineTransform bgTransf;

    public View(Game game) {
        this.game = game;
        double imWidth = im0.getWidth(null);
        double imHeight = im0.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 : Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 : Constants.FRAME_HEIGHT/imHeight);
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
        this.setDoubleBuffered(true);
    }

    public void paintComponent(Graphics g0) {
        int earthRadius = Earth.getRADIUS();
        Graphics2D g = (Graphics2D) g0;

        g.drawImage(im0, bgTransf, null);
        if (Game.EARTH) {
            g.drawImage(im1, FRAME_WIDTH/2 - earthRadius, FRAME_HEIGHT/2 - earthRadius,
                    2 * earthRadius, 2 * earthRadius, null);
        }

        synchronized (Game.class) {
            for (GameObject object : game.objects) {
                object.draw(g);
            }
        }

        //Paints the HUD
        g.setColor(Color.black);
        g.fillRect(0,FRAME_HEIGHT-40,FRAME_WIDTH, 40);
        g.setColor(Color.white);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        g.drawString("Score: " + Game.SCORE, 10, FRAME_HEIGHT-15);
        g.drawString("Shields: " + Game.SHIELDS, 160, FRAME_HEIGHT-15);
        g.drawString("Earth HP: " + Game.EARTH_HP, 285, FRAME_HEIGHT-15);
        g.drawString("Lives: " + Game.LIVES, 430, FRAME_HEIGHT-15);
        g.drawString("Status: " + Game.STATUS, FRAME_WIDTH-350, FRAME_HEIGHT-15);
        g.drawString("Level: " + Game.LEVEL, FRAME_WIDTH-100, FRAME_HEIGHT-15);

        //Paints Game Over message
        if (Game.GAME_OVER) {
            g.setFont(new Font("Comic Sans MS",Font.PLAIN,24));
            g.drawString("Press Enter to Play Again", FRAME_WIDTH/2-140, FRAME_HEIGHT/2-80);
        }
    }

    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }

}
