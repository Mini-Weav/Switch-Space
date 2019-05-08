package objects;

import utilities.SoundManager;
import utilities.Sprite;
import utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static game.Constants.*;

/*
    GameObjects that are obstacles to both the Player and AI ships, as well as Earth
    Can be red or blue
 */
public class Asteroid extends GameObject {

    static final int RADIUS = 60, HP = 1;
    static final double MAX_SPEED = 100, MAX_STEER_RATE = 4 * Math.PI;
    static final Clip DEAD_SOUND = SoundManager.asteroidExplode;

    int nPoints = 0;
    double turn, steerRate, maxRadius = radius + 1, minRadius = radius - radius/4;
    int[] pX, pY;

    List<Asteroid> spawnedAsteroids = null;
    Image im0;
    Color colour;
    Vector2D direction;

    public Asteroid(Vector2D v, Vector2D velocity, double r) {
        super(v, velocity, r, HP, DEAD_SOUND);
        direction = new Vector2D(0, -1);

        //Creates a random polygon within the specified number of vertices
        while (nPoints < MIN_POINTS || nPoints > MAX_POINTS) {
            nPoints = (int) (Math.random() * MAX_POINTS);
        }
        pX = new int[nPoints];
        pY = new int[nPoints];
        for (int i = 0; i < nPoints; i++) {
            double theta = Math.PI * 2 * (i + Math.random())/ nPoints;
            double radius = minRadius + Math.random() * (maxRadius - minRadius);
            double x = radius * Math.cos(theta);
            double y = radius * Math.sin(theta);
            pX[i] = (int) x;
            pY[i] = (int) y;
        }

        //Randomly assigns the Asteroid's rotation speed and direction
        turn = Math.random();
        steerRate = Math.random() * MAX_STEER_RATE;
        if (Math.random() < 0.5) { turn *= -1; }

        //Randomly assigns the Asteroid's colour
        if (Math.random() < 0.5) {
            colour = BLUE;
            im0 = Sprite.BLUE_ASTEROID;
        }
        else {
            colour = RED;
            im0 = Sprite.RED_ASTEROID;
        }
    }

    public List<Asteroid> getSpawnedAsteroids() { return spawnedAsteroids; }
    public void setSpawnedAsteroids(List<Asteroid> spawnedAsteroids) { this.spawnedAsteroids = spawnedAsteroids; }

    //Returns an Asteroid with a random positions and velocity
    public static Asteroid makeRandomAsteroid() {
        double x, y;

        //Ensures Asteroids do not spawn on or near the Earth
        do { x = Math.random() * FRAME_WIDTH; }
        while (x > FRAME_WIDTH/2 - 1.5 * Earth.RADIUS && x < FRAME_WIDTH/2 + 1.5 * Earth.RADIUS);
        do { y = Math.random() * FRAME_HEIGHT; }
        while (y > FRAME_HEIGHT/2 - 1.5 * Earth.RADIUS && y < FRAME_HEIGHT/2 + 1.5 * Earth.RADIUS);
        Vector2D v = new Vector2D(x, y);

        //Randomly assigns the Asteroid's speed
        double vx = Math.random();
        if (Math.random() < 0.5) { vx*=-MAX_SPEED; }
        else { vx*=MAX_SPEED; }
        double vy = Math.random();
        if (Math.random() < 0.5) { vy*=-MAX_SPEED; }
        else { vy*=MAX_SPEED; }
        Vector2D vv = new Vector2D(vx, vy);

        return new Asteroid(v, vv, RADIUS);
    }

    //Returns a smaller asteroid with random velocities from a larger asteroids position
    public Asteroid spawnAsteroid() {
        Vector2D v = new Vector2D(position.x, position.y);

        //Randomly assigns the Asteroid's speed
        double vx = Math.random();
        if (Math.random() < 0.5) { vx *= -MAX_SPEED; }
        else { vx *= MAX_SPEED; }
        double vy = Math.random();
        if (Math.random() < 0.5) { vy *= -MAX_SPEED; }
        else { vy *= MAX_SPEED; }
        Vector2D vv = new Vector2D(vx, vy);

        return new Asteroid(v, vv, radius/2);
    }

    public void hit() {
        //If the asteroid is big/medium, three smaller asteroids are spawned when it is hit
        if (this.radius > 15) {
            spawnedAsteroids = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                spawnedAsteroids.add(spawnAsteroid());
            }
        }
        super.hit();
    }

    public boolean canHit(GameObject other) { return !(other instanceof Asteroid); }

    public void update() {
        super.update();
        direction = direction.rotate(turn * steerRate * DT);
    }

    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.setColor(colour);
        Polygon polygon = new Polygon(pX, pY, nPoints);
        g.setPaint(new TexturePaint((BufferedImage) im0, polygon.getBounds2D()));
        g.fill(polygon);
        g.setTransform(at);
    }
}

