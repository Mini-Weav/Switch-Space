package controllers;

import objects.Bullet;
import objects.GameObject;
import objects.PlayerShip;
import objects.Ship;
import utilities.Vector2D;

public class Controllers {

    //Returns the closest GameObject (which isn't a bullet) that a Ship can hit
    public static GameObject findTarget(Ship ship, Iterable<GameObject> gameObjects) {
        GameObject target = null;
        double closestDistance = Double.MAX_VALUE;
        for (GameObject object : gameObjects) {
            if (ship.canHit(object) && !(object instanceof Bullet)) {
                double distance = ship.getPosition().dist(object.getPosition());
                if (distance < closestDistance) {
                    target = object;
                    closestDistance = distance;
                }
            }
        }
        return target;
    }

    //Returns the PlayerShip
    public static GameObject findPlayer(Iterable<GameObject> gameObjects) {
        GameObject target = null;
        for (GameObject object : gameObjects) {
            if (object instanceof PlayerShip) {
                target = object;
            }
        }
        return target;
    }

    //Returns the angle between two GameObjects
    //A boolean argument is supplied to decide if an object wants to look at, or away from the other
    public static double aim(Ship ship, GameObject target, boolean flee) {
        Vector2D position = new Vector2D(ship.getPosition());
        Vector2D direction = position.subtract(target.getPosition());
        if (!flee) {
            direction.mult(-1);
        }
        return direction.angle();
    }
}
