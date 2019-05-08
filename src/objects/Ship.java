package objects;

import controllers.Action;
import controllers.Controller;
import utilities.SoundManager;
import utilities.Vector2D;

import javax.sound.sampled.Clip;

import static game.Constants.DT;

/*
    GameObject class that is a super class
    Defines common methods for Player and AI Ships
 */
public abstract class Ship extends GameObject {

    static final double DRAG = 0.01;

    int maxSpeed = 200;
    double steerRate = Math.PI;
    double magAcc = 100;

    long lastShot = System.currentTimeMillis();

    Vector2D direction;
    Controller ctrl;
    Bullet bullet = null;

    public Ship(Vector2D position, double radius, int hp, Clip deadSound) {
        super(position, new Vector2D(), radius, hp, deadSound);
        direction = new Vector2D(0, -1);
    }

    public Vector2D getDirection() { return direction; }
    public Bullet getBullet() { return bullet; }
    public void setBullet(Bullet bullet) { this.bullet = bullet; }

    public void update() {
        Action action = ctrl.action();
        direction = direction.rotate(action.turn * steerRate * DT);
        velocity = velocity.addScaled(direction, ((action.thrust * magAcc) - DRAG) * DT);
        if (velocity.mag() > maxSpeed) {
            velocity.x = maxSpeed * Math.cos(velocity.angle());
            velocity.y = maxSpeed * Math.sin(velocity.angle());
        }

        if (action.shoot) {
            if (System.currentTimeMillis() - lastShot > 1000/4 ||
                    (this instanceof PlayerShip && PowerUpEffect.GOLD && System.currentTimeMillis() - lastShot > 1000/8)) {
                mkBullet();
                action.shoot = false;
                lastShot = System.currentTimeMillis();
            }
        }
        super.update();
    }

    //Fires a Bullet from the ship's current position and direction
    public void mkBullet() {
        SoundManager.fire();
        bullet = new Bullet(new Vector2D(position), new Vector2D(velocity), this);
        bullet.position = new Vector2D(bullet.position.addScaled(direction, radius+30));
        bullet.velocity = new Vector2D(bullet.velocity.addScaled(direction, Bullet.SPEED));
    }


}
