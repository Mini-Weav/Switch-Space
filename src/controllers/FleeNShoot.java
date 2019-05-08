package controllers;

import game.Game;
import objects.GameObject;
import objects.PlayerShip;
import objects.Ship;
import utilities.Vector2D;

/*
    AI Controller that searches for, then flees from objects it can hit
    If the PlayerShip is within a certain radius, it may shoot
    (Used for the Gold UFOs)
 */
public class FleeNShoot implements Controller {

    private Action action = new Action();
    private Game game;
    private Ship ship;

    public FleeNShoot(Game game, Ship ship) {
        this.game = game;
        this.ship = ship;
    }

    public Action action() {
        boolean shoot = false;
        int turn = 0, thrust;

        GameObject target = Controllers.findTarget(ship, game.getObjects());
        Vector2D shipPosition = ship.getPosition(), targetPosition = target.getPosition(),
                shipDirection = ship.getDirection();

        double shootingDistance = shipPosition.dist(targetPosition);
        double fleeingAngle = Controllers.aim(ship, target, true);

        //Rotates the AI Ship until it is within a certain range of the specified fleeing angle
        if (shipDirection.angle() < fleeingAngle - Math.PI / 32 ||
                shipDirection.angle() > fleeingAngle + Math.PI / 32) {
            if (fleeingAngle > 0) { turn = 1; }
            else { turn = -1; }
        }

        //If the PlayerShip is within a certain distance and angle of the AI Ship, it may shoot
        if (shootingDistance < 500) {
            thrust = 1;
            if (shipDirection.angle() > fleeingAngle - Math.PI / 32 ||
                    shipDirection.angle() < fleeingAngle + Math.PI / 32) {
                if (target instanceof PlayerShip) {
                    double i = Math.random();
                    shoot = i < 0.04;
                }
            }
        }
        else {
            thrust = 0;
        }

        action.shoot = shoot;
        action.thrust = thrust;
        action.turn = turn;

        return action;
    }
}