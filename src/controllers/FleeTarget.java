package controllers;

import objects.GameObject;
import objects.Ship;
import utilities.Vector2D;

/*
    AI Controller that flees from a supplied GameObject
    Used in Red, Blue, and Black UFOs to avoid Asteroids
 */
public class FleeTarget implements Controller {

    private Action action = new Action();
    private Ship ship;
    private GameObject target;

    public FleeTarget(Ship ship, GameObject target) {
        this.ship = ship;
        this.target = target;
    }

    public Action action() {
        int turn = 0, thrust = 1;

        Vector2D shipDirection = ship.getDirection();

        double fleeingAngle = Controllers.aim(ship, target, true);

        //Rotates the AI Ship until it is within a certain range of the specified fleeing angle
        if (shipDirection.angle() < fleeingAngle - Math.PI / 32 ||
                shipDirection.angle() > fleeingAngle + Math.PI / 32) {
            if (fleeingAngle > 0) { turn = 1; }
            else { turn = -1; }
        }

        action.shoot = false;
        action.thrust = thrust;
        action.turn = turn;


        return action;
    }
}
