package game;

import utilities.JEasyFrameFull;

import java.awt.*;

public class Constants {

    public static final int MENU_HEIGHT = 480;
    public static final int MENU_WIDTH = 640;
    public static final Dimension MENU_SIZE = new Dimension( Constants.MENU_WIDTH, Constants.MENU_HEIGHT );
    public static int FRAME_HEIGHT = JEasyFrameFull.HEIGHT;
    public static int FRAME_WIDTH = JEasyFrameFull.WIDTH;
    public static final Dimension FRAME_SIZE = new Dimension( Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT );

    //Sleep time between two frames in milliseconds
    public static final int DELAY = 10;
    //Sleep time between two frames in seconds
    public static final double DT = DELAY / 1000.0;

    //Score needed to increment the number of lives
    public static final int ONE_UP = 10000;

    //Rate that a random thrust/turn value is assigned
    public static final double RANDOM_THRUST = 2;
    public static final double RANDOM_TURN = 3;

    public static final Color RED = Color.red;
    public static final Color BLUE = Color.blue;
    public static final Color GOLD = new Color(255,215,0);
    public static final Color BLACK = new Color(80,80,80);

    //Minimum and maximum amount of vertices on an asteroid
    public static final int MIN_POINTS = 7;
    public static final int MAX_POINTS = 15;

}
