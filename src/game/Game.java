package game;

//Switch-Space

import controllers.Keys;
import objects.*;
import utilities.JEasyFrame;
import utilities.JEasyFrameFull;
import utilities.SoundManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static game.Constants.DELAY;

public class Game {

    static final int N_INITIAL_ASTEROIDS = 1, N_MAX_SATELLITES = 2;
    static int SCORE, EARTH_HP, SHIELDS, LIVES, ONE_UP, LEVEL, MULTIPLIER,HP_INCREASE, DIFFICULTY = 1, N_MAX_UFOS,
            UFOS = 0, SATELLITES = 0;
    static boolean ACTIVE, GAME_OVER, EARTH, RESET, START, QUIT, BLACK_HOLE;
    static String STATUS, PREV_STATUS;

    List<GameObject> objects;
    Keys ctrl;
    PlayerShip ship;
    Earth earth;

    public Game() {
        setDifficultyLevel(DIFFICULTY);

        objects = new ArrayList<>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) { objects.add(Asteroid.makeRandomAsteroid()); }

        ctrl = new Keys();
        earth = new Earth();
        objects.add(earth);
        ship = new PlayerShip(ctrl);
        objects.add(ship);

        SoundManager.startMusic();

        SCORE = 0;
        ONE_UP = 0;
        LEVEL = 1;
        STATUS = "NORMAL";
        ACTIVE = true;
        GAME_OVER = false;
        EARTH = true;
        RESET = false;
        QUIT = false;
    }

    public static int getEarthHp() { return EARTH_HP; }
    public static int getSHIELDS() { return SHIELDS; }
    public static int getHpIncrease() { return HP_INCREASE; }
    public static boolean isGameOver() { return GAME_OVER; }
    public static boolean isACTIVE() { return ACTIVE; }
    public static void setACTIVE(boolean ACTIVE) { Game.ACTIVE = ACTIVE; }
    public static void setRESET(boolean RESET) { Game.RESET = RESET; }
    public static void setQUIT(boolean QUIT) { Game.QUIT = QUIT; }
    public static String getPrevStatus() { return PREV_STATUS; }
    public static void setPrevStatus(String prevStatus) { PREV_STATUS = prevStatus; }
    public static String getSTATUS() { return STATUS; }
    public static void setSTATUS(String STATUS) { Game.STATUS = STATUS; }
    public List<GameObject> getObjects() { return objects; }

    public static void main(String[] args) throws Exception {
        SoundManager.loadSounds();

        //Creates Menu
        Menu menu = new Menu();
        SoundManager.levelUp();
        new JEasyFrame(menu,"CE218 Assignment");

        while (true) {
            //Creates a new Game
            if (START) {
                Game game = new Game();
                View view = new View(game);
                JEasyFrameFull frame = new JEasyFrameFull(view);
                frame.addKeyListener(game.ctrl);
                //Runs the Game
                while (!QUIT) {
                    assert game != null;
                    game.update();
                    view.repaint();
                    Thread.sleep(DELAY);
                    //Creates a new Game
                    if (RESET) {
                        game = new Game();
                        view = new View(game);
                        JEasyFrameFull oldFrame = frame;
                        frame = new JEasyFrameFull(view);
                        frame.addKeyListener(game.ctrl);
                        oldFrame.dispose();
                    }
                    //Quits the Game, and resets all Game values
                    if (QUIT) {
                        frame.dispose();
                        for (GameObject object: game.objects) {
                            object.setDead(true);
                        }
                        SoundManager.levelUp();
                        SoundManager.stopMusic();
                        SoundManager.stopShield();
                        game.objects.clear();
                        START = false;
                        Shield.stopShield();
                        PowerUpEffect.stopPowerUp();
                        PlayerShip.setCOLOUR(null);
                        game = null;
                        view = null;
                        if (!GAME_OVER) { writeScore(SCORE); }
                    }
                }
            }
            menu.repaint();
        }
    }

    public void update() {
        if (ACTIVE) {
            int n = 0;
            boolean asteroid = false;
            for (int i = 0; i<objects.size()-1; i++) {
                for (int j = 1+n; j<objects.size(); j++) {
                    objects.get(i).collisionHandling(objects.get(j));
                }
                n++;
            }

            //Resets variables that counts the number of various GameObject types
            N_MAX_UFOS = LEVEL/3;
            UFOS = 0;
            SATELLITES = 0;
            BLACK_HOLE = false;

            List<GameObject> alive = new ArrayList<>();
            for (GameObject object : objects) {
                object.update();
                if (!object.isDead()) {
                    alive.add(object);
                }
                if (ship.getBullet() != null) {
                    alive.add(ship.getBullet());
                    ship.setBullet(null);
                }
                //Creates GameObjects spawned from UFOs if values aren't null
                //and increments the variable that counts UFOs
                if (object instanceof UFO) {
                    UFOS++;
                    if (((UFO) object).getBullet() != null) {
                        alive.add(((UFO) object).getBullet());
                        ((UFO) object).setBullet(null);
                    }
                    if (((UFO) object).getMine() != null) {
                        alive.add(((UFO) object).getMine());
                        ((UFO) object).setMine(null);
                    }
                    if (((UFO) object).getPowerUp() != null) {
                        alive.add(((UFO) object).getPowerUp());
                        ((UFO) object).setPowerUp(null);
                    }
                }
                //Creates GameObjects spawned from Satellites if values aren't null
                //and increments the variable that counts Satellites
                if (object instanceof Satellite) {
                    SATELLITES++;
                    if (((Satellite) object).getBullet() != null) {
                        alive.add(((Satellite) object).getBullet());
                        ((Satellite) object).setBullet(null);
                    }
                    if (((Satellite) object).getPowerUp() != null) {
                        alive.add(((Satellite) object).getPowerUp());
                        ((Satellite) object).setPowerUp(null);
                    }
                }
                //Creates GameObjects spawned from Asteroids if values aren't null
                if (object instanceof Asteroid) {
                    asteroid = true;
                    if (((Asteroid) object).getSpawnedAsteroids() != null) {
                        alive.addAll(((Asteroid) object).getSpawnedAsteroids());
                        ((Asteroid) object).setSpawnedAsteroids(null);
                    }
                }
                if (object instanceof BlackHole) { BLACK_HOLE = true; }

                //What to do if the PlayerShip or Earth dies
                if (earth.isDead()) {
                    GAME_OVER = true;
                    EARTH = false;
                }
                if (ship.isDead() && LIVES > 0) {
                    ship = new PlayerShip(ctrl);
                    alive.add(ship);
                }
                if (ship.isDead() && LIVES <= 0) {
                    GAME_OVER = true;
                }
            }
            synchronized (Game.class) {
                objects.clear();
                objects.addAll(alive);
            }

            //Increments number of Lives by one, if a certain score is attained
            if (ONE_UP > Constants.ONE_UP) {
                incLIVES();
                ONE_UP -= Constants.ONE_UP;
            }

            //If there are no asteroids, the level increases
            //and Earth's HP is incremented by an amount depending on the difficulty
            if (!asteroid) {
                SoundManager.levelUp();
                LEVEL++;
                earth.incHP();
                incHP();
                new Shield();
                for (int i = 0; i < LEVEL; i++) {
                    objects.add(Asteroid.makeRandomAsteroid());
                }
            }

            //Randomly creates UFOs once level 3 is reached
            //The maximum number of UFOs at a given time increases every 3 levels
            if (UFOS < N_MAX_UFOS && Math.random() < 0.001) {
                objects.add(UFO.makeRandomUFO(this));
            }
            //Randomly creates Satellites once level 4 is reached
            //The maximum number of Satellites at a given time is 2
            if (SATELLITES < N_MAX_SATELLITES && LEVEL > 3 && Math.random() < 0.001) {
                objects.add(Satellite.makeRandomSatellite(this));
            }
            //Randomly creates a pair of BlackHoles once level 2 is reached
            //The maximum number of BlackHoles at a given time is 2
            if (!BLACK_HOLE && LEVEL > 1 && Math.random() < 0.001) {
                BlackHole[] blackHoles = BlackHole.makeRandomBlackHoles();
                Collections.addAll(objects, blackHoles);
            }

            //If GameOver, Game values are reset
            if (GAME_OVER) {
                ACTIVE = false;
                for (GameObject object: objects) {
                    object.setDead(true);
                }
                SoundManager.stopMusic();
                Shield.stopShield();
                PowerUpEffect.stopPowerUp();
                PlayerShip.setCOLOUR(null);
                STATUS = "GAME OVER";
                writeScore(SCORE);
            }
        }
    }

    public static void incScore(int n) {
        SCORE +=n*MULTIPLIER;
        ONE_UP +=n*MULTIPLIER;
    }

    //Writes the score of a finished (game over or quit) game to the highScores.txt file
    public static void writeScore(int n) {
        FileWriter fw;

        try {
            fw = new FileWriter("highScores.txt",true);

            try { fw.write("\n"+n); }
            catch (IOException e) { System.out.println("Error writing file."); }

            try { fw.close(); }
            catch (IOException e) { System.out.println("Error closing file."); }
        }
        catch (IOException e) { System.out.println("Error opening file."); }
    }

    public static void incLIVES() {
        SoundManager.oneUp();
        LIVES++;
    }
    public static void decLIVES() { if (LIVES >0) { LIVES--; } }

    public static void incSHIELDS() { SHIELDS++; }
    public static void decSHIELDS() { if (SHIELDS >0) { SHIELDS--; } }

    public static void incHP() { EARTH_HP += HP_INCREASE; }
    public static void decHP() { if (EARTH_HP >0) { EARTH_HP--; } }

    //Sets various Game parameters depending on the selected difficulty level
    public static void setDifficultyLevel(int n) {
        switch (n) {
            //Easy
            case 0:
                SHIELDS = 5;
                EARTH_HP = 20;
                LIVES = 5;
                HP_INCREASE = 3;
                MULTIPLIER = 1;
                break;
            //Medium
            case 1:
                SHIELDS = 3;
                EARTH_HP = 15;
                LIVES = 3;
                HP_INCREASE = 2;
                MULTIPLIER = 2;
                break;
            //Hard
            case 2:
                SHIELDS = 0;
                EARTH_HP = 10;
                LIVES = 1;
                HP_INCREASE = 1;
                MULTIPLIER = 3;
                break;
        }
    }
}
