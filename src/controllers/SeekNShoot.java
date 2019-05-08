package controllers;

import game.*;
import objects.*;
import utilities.Vector2D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/*
    AI Controller that searches for, pursues, and then shoots in the direction of the player
    (Used for the Black UFOs)
 */
public class SeekNShoot implements Controller, ActionListener{

    private controllers.Action action = new controllers.Action();
    private Timer timer;
    private Game game;
    private Ship ship;

    public SeekNShoot(Game game, Ship ship) {
        this.game = game;
        this.ship = ship;
        timer = new Timer(10000, this);
        timer.start();
    }

    public controllers.Action action() {
        boolean shoot = false;
        int turn = 0, thrust = 1;

        List<GameObject> objects = game.getObjects();
        GameObject target = Controllers.findTarget(ship, objects);

        Vector2D shipDirection = ship.getDirection(), shipPosition = ship.getPosition(),
                targetPosition = target.getPosition();

        double shootingDistance = shipPosition.dist(targetPosition);
        double shootingAngle = Controllers.aim(ship, target, false);

        //If closest target is an Asteroid, the AI Ship flees
        if ((target instanceof Asteroid || target instanceof Mine) && shootingDistance < 100) {
            action = new FleeTarget(ship, target).action();
        }
        //Else, rotates the AI Ship until it is within a certain range of the specified shooting angle
        else {
            target = Controllers.findPlayer(objects);
            shootingAngle = Controllers.aim(ship, target, false);
        }
        //If the target is the PlayerShip, it rotates until the shooting angle is within a specified angle
        //then shoots randomly
        if (target instanceof PlayerShip) {
            if (shipDirection.angle() < shootingAngle - Math.PI / 32 ||
                    shipDirection.angle() > shootingAngle + Math.PI / 32) {
                if (shootingAngle > 0) { turn = 1; }
                else { turn = -1; }
            }

            else {
                double i = Math.random();
                shoot = i < 0.01;
            }
        }

        action.shoot = shoot;
        action.thrust = thrust;
        action.turn = turn;

        return action;
    }



    public void actionPerformed(ActionEvent e) {
        if (!ship.isDead()) {
            ((UFO)ship).mkMine();
            timer.restart();
        }
    }
}

