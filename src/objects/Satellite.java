package objects;


import controllers.AimNShoot;
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
    Stationary AI Ship
 */
public class Satellite extends Ship implements ActionListener {

    static final int RADIUS = 32, HP = 5;
    static final Clip DEAD_SOUND = SoundManager.asteroidExplode;

    static Game GAME;

    int i;
    Color colour;
    Image im0;
    Timer timer;
    PowerUp powerUp;

    public Satellite(int type, Vector2D v) {
        super(v, RADIUS, HP, DEAD_SOUND);

        i = 0;
        steerRate = 0.5 * Math.PI;

        SoundManager.spawn();

        switch (type) {
            case 1:
                colour = RED;
                im0 = Sprite.RED_SATELLITES[i];
                break;
            case 2:
                colour = BLUE;
                im0 = Sprite.BLUE_SATELLITES[i];
                break;
        }
        timer = new Timer(200, this);
        this.ctrl = new AimNShoot(GAME, this);
        timer.start();
    }

    public PowerUp getPowerUp() { return powerUp; }
    public void setPowerUp(PowerUp powerUp) { this.powerUp = powerUp; }

    //Returns a Satellite with a random position and colour
    public static Satellite makeRandomSatellite(Game game) {
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
        if (i > 0.5 && i < 1) { type = 2; }

        Vector2D v = new Vector2D(x, y);

        GAME = game;

        return new Satellite(type, v);
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
            if (i > 4) { i = 0; }
        }
    }

    public void draw(Graphics2D g) {
        if (colour == RED) {
            im0 = Sprite.RED_SATELLITES[i];
        }
        if (colour == BLUE) {
            im0 = Sprite.BLUE_SATELLITES[i];
        }
        Sprite sprite = new Sprite(im0, position, direction, radius*2, radius*2);
        sprite.draw(g);
    }
}