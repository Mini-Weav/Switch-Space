package objects;

import game.Game;
import utilities.SoundManager;
import utilities.Sprite;
import utilities.Vector2D;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static game.Constants.FRAME_HEIGHT;
import static game.Constants.FRAME_WIDTH;

/*
    Pair of GameObjects that warp other GameObjects
 */
public class BlackHole extends GameObject implements ActionListener{

    static final int RADIUS = 30, HP = 1;
    static final Clip DEAD_SOUND = SoundManager.noSound;

    static Image IM_0;

    int i, j, turn; //int i used to determine the frame of animation, int j used to track the object's lifetime
    Timer timer;
    Vector2D direction;
    BlackHole exitBlackHole = null; //Each BlackHole is associated with another, forming an entrance/exit relationship

    public BlackHole(Vector2D v) {
        super(v, new Vector2D(), RADIUS, HP, DEAD_SOUND);

        i = 0;
        j = 0;

        direction = new Vector2D(0, -1);

        timer = new Timer(250, this);
        timer.start();

        SoundManager.spawn();

        IM_0 = Sprite.BLACK_HOLES[i];
    }

    //Override GameObjects hit() method, so BlackHoles are only 'dead' once the time limit is reached
    public void hit() {}

    public void update() {
        direction = direction.rotate(turn * Math.PI / 64);
    }

    //Returns a list of two BlackHoles with random positions
    public static BlackHole[] makeRandomBlackHoles() {
        BlackHole[] blackHoles = new BlackHole[2];
        for (int i = 0; i < 2; i++) {
            double x, y;

            //Ensures BlackHoles do not spawn on or near the Earth, or by the edges of the frame
            do { x = Math.random() * FRAME_WIDTH; }
            while (x > FRAME_WIDTH/2 - 1.5 * Earth.RADIUS && x < FRAME_WIDTH/2 + 1.5 * Earth.RADIUS
                    || (x < 100 || x > FRAME_WIDTH - 100));

            do { y = Math.random() * FRAME_HEIGHT; }
            while (y > FRAME_HEIGHT/2 - 1.5 * Earth.RADIUS && y < FRAME_HEIGHT/2 + 1.5 * Earth.RADIUS
                    || (y < 100 || y > FRAME_HEIGHT - 140));

            Vector2D v = new Vector2D(x, y);
            BlackHole blackHole = new BlackHole(v);
            blackHoles[i] = blackHole;
        }

        blackHoles[0].exitBlackHole = new BlackHole(blackHoles[1].position);
        blackHoles[1].exitBlackHole = new BlackHole(blackHoles[0].position);

        blackHoles[0].turn = 1;
        blackHoles[1].turn = -1;

        //If the created BlackHoles are too close to each other
        // new BlackHoles are created by recursively calling this function
        if (blackHoles[0].position.dist(blackHoles[1].position) < 6 * RADIUS) {
            blackHoles = makeRandomBlackHoles();
        }

        return blackHoles;
    }

    //When a GameObject hits a BlackHole, it's position is changed to that of the other associated BlackHole
    public void collisionHandling(GameObject other) {
        if (canHit(other) && this.overlap(other)) {
            Vector2D newPosition = new Vector2D(this.exitBlackHole.position);
            other.position = newPosition.addScaled(other.velocity,
                    (radius+2*other.radius)/Math.hypot(other.velocity.x, other.velocity.y));
        }
    }

    public boolean canHit(GameObject other) { return !(other instanceof Earth || other instanceof BlackHole); }

    public void actionPerformed(ActionEvent e) {
        if (Game.isACTIVE()) {
            i++;
            j++;
            if (i > 2) { i = 0; }
            if (j == 240) {
                SoundManager.spawn();
                timer.stop();
                this.dead = true;
            }
        }
    }

    public void draw(Graphics2D g) {
        IM_0 = Sprite.BLACK_HOLES[i];
        Sprite sprite = new Sprite(IM_0, position, direction, radius*2, radius*2);
        sprite.draw(g);
    }

}
