package objects;

import game.Game;
import utilities.SoundManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Class that determines what PowerUps are currently in play
 */
public class PowerUpEffect implements ActionListener {
    static boolean ON = false, BIG_BULLET = false, SPEED_BULLET = false, GOLD = false;
    static Timer TIMER;

    public PowerUpEffect(int type) {
        ON = true;
        TIMER = new Timer(15000, this);
        TIMER.restart();
        SoundManager.startShield();
        switch (type) {
            case PowerUp.BIG_BULLET:
                BIG_BULLET = true;
                Game.setSTATUS("BIG BULLET");
                break;
            case PowerUp.SPEED_BULLET:
                SPEED_BULLET = true;
                Game.setSTATUS("SPEED BULLET");
                break;
            case PowerUp.GOLD:
                GOLD = true;
                Game.setSTATUS("GOLD");
                break;
        }
    }

    public void actionPerformed(ActionEvent e) {
        stopPowerUp();
    }

    //Stops the current PowerUp
    public static void stopPowerUp() {
        SoundManager.stopShield();
        if (TIMER != null) { TIMER.stop(); }
        ON = false;
        BIG_BULLET = false;
        SPEED_BULLET = false;
        GOLD = false;
        Game.setSTATUS("NORMAL");
    }
}