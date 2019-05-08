package objects;

import game.Constants;
import game.Game;
import utilities.SoundManager;
import utilities.Sprite;
import utilities.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    GameObject used to determine which PowerUpEffect is used
 */
public class PowerUp extends GameObject implements ActionListener {

    static final int RADIUS = 16, SHIELD = 0, LIFE = 1, BIG_BULLET = 2, SPEED_BULLET = 3, GOLD = 4;

    int type;
    Timer timer;
    Image im0;
    Vector2D direction;

    public PowerUp(Vector2D position, int type) {
        super(position, new Vector2D(),RADIUS, 1, SoundManager.spawn);
        SoundManager.spawn();
        this.type = type;
        direction = new Vector2D(0, -1);
        timer = new Timer(30000, this);
        timer.start();
    }

    //Determines which PowerUp is dropped when a UFO is destroyed
    public static PowerUp makePowerUp(UFO ufo) {
        int type;

        if (ufo.colour == Constants.RED) { type = (int) (Math.random() * 3); }
        else if (ufo.colour == Constants.BLUE) {
            type = (int) (Math.random() * 3);
            if (type == 2) { type++; }
        }
        else if (ufo.colour == Constants.BLACK) { type = (int) (Math.random() * 2) + 2; }
        else { type = 4; }

        return new PowerUp(ufo.position, type);
    }

    //Determines which PowerUp is dropped when a Satellite is destroyed
    public static PowerUp makePowerUp(Satellite satellite) {
        int type;

        if (satellite.colour == Constants.RED) { type = (int) (Math.random() * 3); }
        else {
            type = (int) (Math.random() * 3);
            if (type == 2) { type++; }
        }

        return new PowerUp(satellite.position, type);
    }

    public void update() {
        direction = direction.rotate(Math.PI / 64);
    }

    public void actionPerformed(ActionEvent e) {
        SoundManager.spawn();
        timer.stop();
        this.dead = true;
    }

    //Determines which PowerUpEffect is used when the PowerUp is hit by the PlayerShip
    public void collisionHandling(GameObject other) {
        if (other instanceof PlayerShip) {
            switch (type) {
                case SHIELD:
                    Game.incSHIELDS();
                    break;
                case LIFE:
                    Game.incLIVES();
                    break;
                case BIG_BULLET:
                    new PowerUpEffect(BIG_BULLET);
                    break;
                case SPEED_BULLET:
                    new PowerUpEffect(SPEED_BULLET);
                    break;
                case GOLD:
                    new PowerUpEffect(GOLD);
                    break;
            }
            this.hit();
        }
    }

    public void hit() {
        timer.stop();
        SoundManager.levelUp();
        super.hit();
    }

    public boolean canHit(GameObject other) { return other instanceof PlayerShip; }

    public void draw(Graphics2D g) {
        switch (type) {
            case SHIELD:
                im0 = Sprite.SHIELD;
                break;
            case LIFE:
                im0 = Sprite.LIFE;
                break;
            case BIG_BULLET:
                im0 = Sprite.BIG_BULLET;
                break;
            case SPEED_BULLET:
                im0 = Sprite.SPEED_BULLET;
                break;
            case  GOLD:
                im0 = Sprite.GOLD;
                break;
        }
        Sprite sprite = new Sprite(im0, position, direction, radius*2, radius*2);
        sprite.draw(g);
    }
}
