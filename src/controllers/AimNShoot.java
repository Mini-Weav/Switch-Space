package controllers;

import game.Game;
import objects.GameObject;
import objects.Ship;
import utilities.Vector2D;

/*
    AI Controller that searches for, then shoots in the direction of the player
    (Used for the Satellites)
 */
public class AimNShoot implements Controller {

    private Action action = new Action();
    private Game game;
    private Ship ship;

    public AimNShoot(Game game, Ship ship) {
        this.game = game;
        this.ship = ship;
    }

    public Action action() {
        boolean shoot = false;
        int turn = 0;

        GameObject target = Controllers.findPlayer(game.getObjects());
        Vector2D shipDirection = ship.getDirection(), shipPosition = ship.getPosition(),
                targetPosition = target.getPosition();

        double shootingDistance = shipPosition.dist(targetPosition);
        double shootingAngle = Controllers.aim(ship, target, false);

        //Rotates the AI ship until it is within a certain range of the specified shooting angle
        if (shipDirection.angle() < shootingAngle - Math.PI / 64 ||
                shipDirection.angle() > shootingAngle + Math.PI / 64) {
            if (shootingAngle > 0) { turn = 1; }
            else { turn = -1; }
        }
        //Chance of the AI ship shooting once it's direction is with the specified range
        else if (shootingDistance < 2000  ) {
            double i = Math.random();
            shoot = i < 0.02;
        }


        action.shoot = shoot;
        action.turn = turn;
        return action;
    }
}
