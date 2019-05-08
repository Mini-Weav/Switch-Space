package controllers;

import game.Game;
import objects.Shield;
import utilities.SoundManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keys extends KeyAdapter implements Controller {
    Action action;

    public Keys() {
        action = new Action();
    }

    public Action action() {
        //This is defined to comply with the standard interface
        return action;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                if (Game.isACTIVE()) {
                    SoundManager.startThrust();
                }
                action.thrust = 1;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = +1;
                break;
            case KeyEvent.VK_DOWN:
                action.shield = true;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
            case KeyEvent.VK_SHIFT:
                action.shift = true;
                break;
            //Pauses and unpauses the game
            case KeyEvent.VK_P:
                if (!Game.isGameOver()) {
                    if (Game.isACTIVE()) {
                        Game.setPrevStatus(Game.getSTATUS()) ;
                        Game.setACTIVE(false);
                        Game.setSTATUS("PAUSE");
                        SoundManager.stopMusic();
                        SoundManager.stopShield();
                        SoundManager.stopThrust();
                    }
                    else {
                        Game.setACTIVE(true);
                        Game.setSTATUS(Game.getPrevStatus());
                        if (Shield.isON()) {
                            SoundManager.startShield();
                        }
                        SoundManager.startMusic();
                    }
                    SoundManager.beep();
                }
                break;

            case KeyEvent.VK_ENTER:
                if (Game.isGameOver()) {
                    Game.setRESET(true);
                }
                break;
            case KeyEvent.VK_ESCAPE:
                Game.setQUIT(true);
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 0;
                SoundManager.stopThrust();
                break;
            case KeyEvent.VK_LEFT:
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                break;
            case KeyEvent.VK_DOWN:
                action.shield = false;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
            case KeyEvent.VK_SHIFT:
                action.shift = false;
                break;
        }
    }

}
