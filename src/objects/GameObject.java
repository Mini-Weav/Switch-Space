package objects;

import utilities.SoundManager;
import utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static game.Constants.*;

public abstract class GameObject {

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    int hp;
    double radius;
    boolean dead;

    Vector2D position;
    Vector2D velocity;
    Clip deadSound;

    public GameObject(Vector2D position, Vector2D velocity, double radius, int hp, Clip deadSound) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
        this.hp = hp;
        this.deadSound = deadSound;
    }

    public Vector2D getPosition() { return position; }

    public boolean isDead() { return dead; }
    public void setDead(boolean dead) { this.dead = dead; }

    public void hit() {
        decHP();
        if (hp <= 0) {
            SoundManager.play(deadSound);
            dead = true;
        }
    }

    public boolean overlap(GameObject other) {
        return this.position.dist(other.position) <= (this.radius + other.radius);
    }

    public void collisionHandling(GameObject other) {
        //Checks if a subclass' collision handling is needed
        if (other instanceof BlackHole && overlap(other)) { other.collisionHandling(this); }
        else if (other instanceof PowerUp && overlap(other)) { other.collisionHandling(this); }
        else if (other instanceof Bullet && overlap(other)) { other.collisionHandling(this); }

        else if (this.canHit(other) && this.overlap(other)) {
            this.hit();
            other.hit();
        }
    }

    public void update() {
        position.addScaled(velocity, DT);
        //(FRAME_HEIGHT-40 to incorporate the HUD)
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT-40);
    }

    public abstract boolean canHit(GameObject other);
    public abstract void draw(Graphics2D g);

    public void decHP() {
        hp--;
    }
}
