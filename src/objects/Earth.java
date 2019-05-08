package objects;

import game.Game;
import utilities.SoundManager;
import utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static game.Constants.FRAME_HEIGHT;
import static game.Constants.FRAME_WIDTH;

/*
    GameObject that is damaged by Asteroids. If it's HP drops to zero, it's Game Over
 */
public class Earth extends GameObject {

    static final int RADIUS = 60;
    static final Clip DEAD_SOUND = SoundManager.earthExplode;

    public Earth() {
        super(new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2),new Vector2D(0,0),RADIUS, Game.getEarthHp(), DEAD_SOUND);
    }

    public static int getRADIUS() { return RADIUS; }

    public void hit() {
        Game.decHP();
        super.hit();
    }

    public void incHP() { hp += Game.getHpIncrease(); }

    public boolean canHit(GameObject other) { return other instanceof Asteroid; }

    public void draw(Graphics2D g) { }

}

