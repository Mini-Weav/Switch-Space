package objects;

import game.Game;
import utilities.SoundManager;
import utilities.Vector2D;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static game.Constants.BLACK;
import static game.Constants.GOLD;

/*
    GameObjects that are projected from Ships to hit/destory other GameObjects
    The colour is determined by the colour of the Ship that fired it
 */
public class Bullet extends GameObject implements ActionListener {

    static final int HP = 1;
    static final Clip DEAD_SOUND = SoundManager.noSound;

    static int RADIUS;
    static double SPEED;

    int type;
    Timer timer;
    Color colour;

    public Bullet(Vector2D v, Vector2D velocity, Ship shipType) {
        super(v, velocity, RADIUS, HP, DEAD_SOUND);

        if (PowerUpEffect.BIG_BULLET && shipType instanceof PlayerShip) { RADIUS = 15; }
        else { RADIUS = 5; }

        if (PowerUpEffect.SPEED_BULLET && shipType instanceof PlayerShip) { SPEED = 750; }
        else { SPEED = 300; }

        v.x += RADIUS/2;
        timer = new Timer(4000, this);
        timer.start();

        if (shipType instanceof PlayerShip ) {
            colour = PlayerShip.COLOUR;
            type = 0;
        }
        else if (shipType instanceof UFO ) {
            colour = ((UFO) shipType).colour;
            type = 1;
        }
        else if (shipType instanceof Satellite ) {
            colour = ((Satellite) shipType).colour;
            type = 1;
        }

    }

    public void hit() {
        timer.stop();
        super.hit();
    }

    public void collisionHandling(GameObject other) {
        if (other instanceof BlackHole && this.overlap(other)) {
            other.collisionHandling(this);
        }

        else if (other instanceof Asteroid || other instanceof UFO || other instanceof Satellite) {
            if (other instanceof Asteroid && this.overlap(other)) {
                //The Bullet's colour determines if it can destroy an Asteroid
                if (this.colour == ((Asteroid) other).colour || this.colour == GOLD || this.colour == Color.BLACK) {
                    this.hit();
                    other.hit();
                    //If the Bullet was created by a PlayerShip, the Game's score is incremented
                    if (this.type == 0) {
                        switch ((int)other.radius) {
                            case 60: Game.incScore(20);
                                break;
                            case 30: Game.incScore(50);
                                break;
                            case 15: Game.incScore(100);
                                break;
                        }
                    }
                } else {
                    this.hit();
                }
            }
            if (other instanceof UFO && this.overlap(other)) {
                //The Bullet's colour determines if it can destroy a UFO
                if (this.colour == ((UFO) other).colour || ((UFO) other).gold || ((UFO) other).black
                        || this.colour == GOLD || this.colour == BLACK) {
                    this.hit();
                    other.hit();
                    //If the Bullet was created by a PlayerShip, the Game's score is incremented
                    if (this.type == 0) {
                        if (((UFO) other).gold) { Game.incScore(500); }
                        else if (((UFO) other).black) { if (other.hp == 0) { Game.incScore(300); } }
                        else { Game.incScore(200); }
                        //When a UFO is destroyed, a PowerUp is dropped
                        if (other.hp == 0) { ((UFO) other).powerUp = PowerUp.makePowerUp((UFO) other); }
                    }
                } else {
                    this.hit();
                }
            }
            if (other instanceof Satellite && this.overlap(other)) {
                //The Bullet's colour determines if it can destroy a Satellite
                if (this.colour == ((Satellite) other).colour || this.colour == GOLD || this.colour == BLACK) {
                    this.hit();
                    other.hit();
                    //If the Bullet was created by a PlayerShip, the Game's score is incremented
                    //When a Satellite is destroyed, a PowerUp is dropped
                    if (this.type == 0 && other.hp == 0) {
                        Game.incScore(500);
                        ((Satellite) other).powerUp = PowerUp.makePowerUp((Satellite) other);
                    }
                } else {
                    this.hit();
                }
            }
        }
        else if (this.canHit(other) && this.overlap(other)) {
            this.hit();
            other.hit();
        }
    }

    public boolean canHit(GameObject other) { return !(other instanceof Earth || other instanceof PowerUp); }

    public void actionPerformed(ActionEvent e) {
        this.dead = true;
    }

    public void draw(Graphics2D g) {
        g.setColor(colour);
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }
}
