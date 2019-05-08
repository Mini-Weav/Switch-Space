package objects;

import controllers.*;
import game.Game;
import utilities.*;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static game.Constants.*;

/*
    Ship that the Player controls
 */
public class PlayerShip extends Ship implements ActionListener {

    static final int RADIUS = 16, HP = 1;
    static Color COLOUR;
    static final Clip DEAD_SOUND = SoundManager.asteroidExplode;
    static Image IM_0, IM_1;

    int i; //int i used to determine the frame of animation
    boolean thrusting;
    Color prevColour = null;
    Timer timer;

    public PlayerShip(Controller ctrl) {
        super(new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2), RADIUS, HP, DEAD_SOUND);

        i = 0;

        this.ctrl = ctrl;
        timer = new Timer(100, this);
        timer.start();

        SoundManager.spawn();

        if (COLOUR == null) {
            COLOUR = RED;
            IM_0 = Sprite.RED_SHIP;
            IM_1 = Sprite.RED_THRUSTS[i];
        }
    }

    public static Color getCOLOUR() { return COLOUR; }
    public static void setCOLOUR(Color COLOUR) { PlayerShip.COLOUR = COLOUR; }

    public void update() {
        controllers.Action action = ctrl.action();
        thrusting = action.thrust == 1;

        //Stores previous colour
        if (COLOUR != GOLD) { prevColour = COLOUR; }
        //Effects of being a GoldShip
        if (PowerUpEffect.GOLD) {
            COLOUR = GOLD;
            maxSpeed = 300;
            magAcc = 200;
            IM_0 = Sprite.GOLD_SHIP;
        }
        //Changes the PlayerShip back after the PowerUp is done
        else {
            COLOUR = prevColour;
            maxSpeed = 150;
            magAcc = 100;
            if (COLOUR == RED) {
                IM_0 = Sprite.RED_SHIP;
            }
            if (COLOUR == BLUE) {
                IM_0 = Sprite.BLUE_SHIP;
            }
        }

        if (action.shift) {
            if ( System.currentTimeMillis() - lastShot > 1000/2) {
                shift();
                action.shift = false;
                lastShot = System.currentTimeMillis();
            }
        }
        if (action.shield && !Shield.ON && Game.getSHIELDS() > 0) {
            new Shield();
            Game.decSHIELDS();
        }
        super.update();
    }

    public void hit() {
        if (!Shield.ON) {
            Game.decLIVES();
            PowerUpEffect.stopPowerUp();
            super.hit();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (Game.isACTIVE()) {
            i++;
            if (i > 1) { i = 0; }

            if (COLOUR == RED) { IM_1 = Sprite.RED_THRUSTS[i]; }
            if (COLOUR == BLUE) { IM_1 = Sprite.BLUE_THRUSTS[i]; }
            if (COLOUR == GOLD) { IM_1 = Sprite.GOLD_THRUSTS[i]; }
        }
    }

    //Changes the PlayerShip's colour
    public void shift() {
        if (!PowerUpEffect.GOLD) {
            SoundManager.shift();
            if (COLOUR == RED) {
                COLOUR = BLUE;
                IM_0 = Sprite.BLUE_SHIP;
            }
            else {
                COLOUR = RED;
                IM_0 = Sprite.RED_SHIP;
            }
        }
    }

    public boolean canHit(GameObject other) {
        return !(other instanceof Earth);
    }

    public void draw(Graphics2D g) {
        if (thrusting) {
            Sprite sprite = new Sprite(IM_1, position, direction, 40, 32);
            sprite.draw(g);
        }
        else {
            Sprite sprite = new Sprite(IM_0, position, direction, 32, 32);
            sprite.draw(g);
        }
        if (Shield.ON) {
            g.setColor(new Color(255,255,255,64));
            g.fillOval((int) (position.x - 2.25 * radius), (int) (position.y - 2.25 * radius),
                    (int) (4.5 * radius), (int) (4.5 * radius));
        }
    }
}
