package controllers;

import static game.Constants.RANDOM_THRUST;
import static game.Constants.RANDOM_TURN;
import static game.Constants.DT;

/*
    AI Controller that randomly turns and thrusts
    (Used in Red and Blue UFOs)
 */
public class RandomMovement implements Controller {

    private static int I = 0;
    private Action action = new Action();

    public Action action() {
        int turn = 0, thrust = 0;
        I++;

        if ( I % RANDOM_THRUST*DT == 0 ) {
            double i = Math.random();
            if (i < 0.25) { thrust = 1; }
            else {thrust = 0; }
        }

        if ( I % RANDOM_TURN*DT == 0 ) {
            double i = Math.random();
            if (i < 0.45) { turn = -1; }
            else if ( i > 0.55) { turn = 1; }
            else {turn = 0; }
        }

        if ( I == 3000 ) { I = 0; }

        action.shoot = false;
        action.turn = turn;
        action.thrust = thrust;
        return action;
    }
}
