package objects;

import utilities.SoundManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Class that determines whether a Shield is currently in play
 */
public class Shield implements ActionListener {
    
    static boolean ON = false;
    static Timer TIMER;

    public Shield() {
        ON = true;
        TIMER = new Timer(5000, this);
        TIMER.start();
        SoundManager.startShield();
    }

    public static boolean isON() { return ON; }    

    public void actionPerformed(ActionEvent e) {
        stopShield();
    }

    //Stops the current Shield
    public static void stopShield() {
        SoundManager.stopShield();
        if (TIMER != null) { TIMER.stop(); }
        ON = false;
    }
}
