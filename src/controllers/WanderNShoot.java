package controllers;


import game.*;
import objects.*;
import utilities.Vector2D;

/*
    AI Controller that randomly moves around, or stops and shoots in the direction of the PlayerShip
    (Used for the Red and Blue UFOs)
 */
public class WanderNShoot implements Controller {

    private boolean stop = false;
    private Action action = new Action();
    private Game game;
    private Ship ship;

    public WanderNShoot(Game game, Ship ship) {
        this.game = game;
        this.ship = ship;
    }

    public Action action() {
        boolean shoot = false;
        int turn = 0, thrust;

        GameObject target = Controllers.findTarget(ship, game.getObjects());
        Vector2D shipDirection = ship.getDirection(), shipPosition = ship.getPosition(),
                targetPosition = target.getPosition();

        double shootingDistance = shipPosition.dist(targetPosition);
        double shootingAngle = Controllers.aim(ship, target, false);

        //Randomly stops and shoots in direction of PlayerShip
        if (Math.random() < 0.005) { stop = true; }
        if (stop && target instanceof PlayerShip) {
            thrust = 0;
            if (shipDirection.angle() < shootingAngle - Math.PI / 16 ||
                    shipDirection.angle() > shootingAngle + Math.PI / 16) {
                if (shootingAngle > 0) {
                    turn = 1;
                } else {
                    turn = -1;
                }
            } else {
                shoot = true;
                stop = false;
            }

            action.shoot = shoot;
            action.thrust = thrust;
            action.turn = turn;
        }
        //Else moves around randomly
        else {
            action = new RandomMovement().action();
        }
        //If closest target is an Asteroid, the AI Ship flees
        if ((target instanceof Asteroid || target instanceof Mine) && shootingDistance < 250) {
            action = new FleeTarget(ship, target).action();
        }

        return action;
    }
}
