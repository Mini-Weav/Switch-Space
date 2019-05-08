package objects;

import controllers.FleeNShoot;
import controllers.SeekNShoot;
import controllers.WanderNShoot;
import game.Game;
import utilities.SoundManager;
import utilities.Sprite;
import utilities.Vector2D;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static game.Constants.*;

/*
    Mobile AI Ship
 */
public class UFO extends Ship implements ActionListener {

    static final int RADIUS = 24, HP = 1;
    static final Clip DEAD_SOUND = SoundManager.asteroidExplode;

    static Game GAME;

    int i, j;
    boolean gold, black;
    Color colour;
    Image im0;
    Timer timer;
    Mine mine;
    PowerUp powerUp;

    public UFO(int type, Vector2D v) {
        super(v, RADIUS, HP, DEAD_SOUND);

        i = 0;
        j = 0;

        SoundManager.spawn();

        switch (type) {
            case 1:
                colour = RED;
                im0 = Sprite.RED_UFOS[i];
                gold = false;
                black = false;
                timer = new Timer(100, this);
                this.ctrl = new WanderNShoot(GAME, this);
                break;
            case 2:
                colour = BLUE;
                im0 = Sprite.BLUE_UFOS[i];
                gold = false;
                black = false;
                timer = new Timer(100, this);
                this.ctrl = new WanderNShoot(GAME, this);
                break;
            case 3:
                colour = GOLD;
                im0 = Sprite.GOLD_UFOS[i];
                gold = true;
                black = false;
                radius = 20;
                maxSpeed *= 2;
                steerRate *= 3;
                magAcc *= 5;
                timer = new Timer(100, this);
                this.ctrl = new FleeNShoot(GAME, this);
                break;
            case 4:
                colour = BLACK;
                im0 = Sprite.BLACK_UFOS[i];
                hp = 3;
                gold = false;
                black = true;
                radius = 40;
                maxSpeed /= 1.5;
                steerRate /= 1.5;
                magAcc /= 2.5;
                timer = new Timer(100, this);
                this.ctrl = new SeekNShoot(GAME, this);
                break;
        }
        timer.start();
    }

    public PowerUp getPowerUp() { return powerUp; }
    public void setPowerUp(PowerUp powerUp) { this.powerUp = powerUp; }
    public Mine getMine() { return mine; }
    public void setMine(Mine mine) { this.mine = mine; }

    //Returns a UFO with a random position and colour
    public static UFO makeRandomUFO(Game game) {
        double x = Math.random() * FRAME_WIDTH;
        while (x > FRAME_WIDTH/2 - 1.5 * Earth.RADIUS && x < FRAME_WIDTH/2 + 1.5 * Earth.RADIUS) {
            x = Math.random() * FRAME_WIDTH;
        }

        double y = Math.random() * FRAME_HEIGHT;
        while (y > FRAME_HEIGHT/2 - 1.5 * Earth.RADIUS && y < FRAME_HEIGHT/2 + 1.5 * Earth.RADIUS) {
            y = Math.random() * FRAME_HEIGHT;
        }

        int type = -1;
        double i = Math.random();
        if (i > 0 && i <= 0.5) { type = 1; }
        if (i > 0.5 && i <= 0.8) { type = 2; }
        if (i > 0.8 && i <= 0.9) { type = 3; }
        if (i > 0.9 && i <= 1) { type = 4; }

        Vector2D v = new Vector2D(x, y);

        GAME = game;

        return new UFO(type, v);
    }

    //Drops a Mine from the ship's current position and direction
    public void mkMine() {
        SoundManager.fire();
        Vector2D vv = new Vector2D(velocity).mult(-1);
        Vector2D vd = new Vector2D(direction).mult(-1);
        mine = new Mine(new Vector2D(position), new Vector2D(vv));
        mine.position = new Vector2D(mine.position.addScaled(vd, radius+60));
        mine.velocity = new Vector2D(0, 0);
    }

    public void leaveGame() {
        SoundManager.spawn();
        timer.stop();
        i = 0;
        j = 0;
        dead = true;
    }

    public void hit() {
        timer.stop();
        super.hit();
    }

    public boolean canHit(GameObject other) {
        return !(other instanceof Earth || other instanceof UFO || other instanceof Satellite || other instanceof PowerUp);
    }

    public void actionPerformed(ActionEvent e) {
        if (Game.isACTIVE()) {
            i++;
            j++;
            if (i > 3) { i = 0; }

            if (gold) {
                if (j == 300) {
                    leaveGame();
                }
            }
            else if (black) {
                if (j == 600) {
                    leaveGame();
                }
            }
            else {
                if (j == 1200) {
                    leaveGame();
                }
            }
        }
    }

    public void draw(Graphics2D g) {
        if (colour == RED) {
            im0 = Sprite.RED_UFOS[i];
        }
        if (colour == BLUE) {
            im0 = Sprite.BLUE_UFOS[i];
        }
        if (colour == GOLD) {
            im0 = Sprite.GOLD_UFOS[i];
        }
        if (colour == BLACK) {
            im0 = Sprite.BLACK_UFOS[i];
        }
        g.drawImage(im0,(int) (position.x - radius), (int) (position.y - radius),
                (int) (2 * radius), (int) (2 * radius), null);
    }
}
