package objects;

import game.Constants;
import game.Game;
import utilities.SoundManager;
import utilities.Sprite;
import utilities.Vector2D;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    GameObjects that are projected by Black UFOs that destroys other GameObjects
    Blows up after 10 seconds
 */
public class Mine extends GameObject implements ActionListener {

    static final int RADIUS = 20, HP = 1;
    static final Color COLOUR = Constants.BLACK;
    static final Clip DEAD_SOUND = SoundManager.mineExplode;

    int i; //int i used to both determine the frame of animation and track the object's lifetime
    Image im0;
    Timer timer;
    Vector2D direction;

    public Mine(Vector2D v, Vector2D velocity) {
        super(v, velocity, RADIUS, HP, DEAD_SOUND);

        i = 0;

        direction = new Vector2D(0, -1);

        timer = new Timer(1000, this);
        timer.start();

        im0 = Sprite.BLACK_MINES[i];
    }

    public void hit() {
        timer.stop();
        super.hit();
    }

    public boolean canHit(GameObject other) { return !(other instanceof Earth); }

    public void update() {
        direction = direction.rotate(Math.PI / 64);
    }

    public void actionPerformed(ActionEvent e) {
        if (!dead && Game.isACTIVE()) {
            i++;
            im0 = Sprite.BLACK_MINES[i];
            if (i > 0 && i < 8) { SoundManager.play(SoundManager.mineTimer1); }
            if (i == 8) { SoundManager.play(SoundManager.mineTimer2); }
            if (i == 9) { this.hit(); }
        }
    }

    public void draw(Graphics2D g) {
        Sprite sprite = new Sprite(im0, position, direction, radius*2, radius*2);
        sprite.draw(g);
    }
}