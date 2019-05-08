package utilities;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class Sprite {
	public static Image RED_ASTEROID,
			RED_UFO1, RED_UFO2, RED_UFO3, RED_UFO4,
			RED_SHIP, RED_THRUST1, RED_THRUST2,
			RED_SATELLITE1, RED_SATELLITE2, RED_SATELLITE3, RED_SATELLITE4, RED_SATELLITE5,
            BLUE_ASTEROID,
			BLUE_UFO1, BLUE_UFO2, BLUE_UFO3, BLUE_UFO4,
			BLUE_SHIP, BLUE_THRUST1, BLUE_THRUST2,
			BLUE_SATELLITE1, BLUE_SATELLITE2, BLUE_SATELLITE3, BLUE_SATELLITE4, BLUE_SATELLITE5,
            GOLD_UFO1, GOLD_UFO2, GOLD_UFO3, GOLD_UFO4,
			GOLD_SHIP, GOLD_THRUST1, GOLD_THRUST2,
			BLACK_UFO1, BLACK_UFO2, BLACK_UFO3, BLACK_UFO4,
			BLACK_MINE1, BLACK_MINE2, BLACK_MINE3, BLACK_MINE4, BLACK_MINE5,
			BLACK_MINE6, BLACK_MINE7, BLACK_MINE8, BLACK_MINE9, BLACK_MINE10,
			BLACK_HOLE1, BLACK_HOLE2, BLACK_HOLE3,
			SHIELD, LIFE, BIG_BULLET, SPEED_BULLET, GOLD,
            SPACE, TITLE, EARTH, INSTRUCTIONS, HIGH_SCORES;

	static {
		try {
			RED_ASTEROID = ImageManager.loadImage("redAsteroid");

			RED_UFO1 = ImageManager.loadImage("redUFO/redUFO1");
			RED_UFO2 = ImageManager.loadImage("redUFO/redUFO2");
			RED_UFO3 = ImageManager.loadImage("redUFO/redUFO3");
			RED_UFO4 = ImageManager.loadImage("redUFO/redUFO4");

			RED_SHIP = ImageManager.loadImage("redShip");
            RED_THRUST1 = ImageManager.loadImage("redThrust/redThrust1");
			RED_THRUST2 = ImageManager.loadImage("redThrust/redThrust2");

			RED_SATELLITE1 = ImageManager.loadImage("redSatellite/redSatellite1");
			RED_SATELLITE2 = ImageManager.loadImage("redSatellite/redSatellite2");
			RED_SATELLITE3 = ImageManager.loadImage("redSatellite/redSatellite3");
			RED_SATELLITE4 = ImageManager.loadImage("redSatellite/redSatellite4");
			RED_SATELLITE5 = ImageManager.loadImage("redSatellite/redSatellite5");

            BLUE_ASTEROID = ImageManager.loadImage("blueAsteroid");

			BLUE_UFO1 = ImageManager.loadImage("blueUFO/blueUFO1");
			BLUE_UFO2 = ImageManager.loadImage("blueUFO/blueUFO2");
			BLUE_UFO3 = ImageManager.loadImage("blueUFO/blueUFO3");
			BLUE_UFO4 = ImageManager.loadImage("blueUFO/blueUFO4");

            BLUE_SHIP = ImageManager.loadImage("blueShip");
            BLUE_THRUST1 = ImageManager.loadImage("blueThrust/blueThrust1");
			BLUE_THRUST2 = ImageManager.loadImage("blueThrust/blueThrust2");

			BLUE_SATELLITE1 = ImageManager.loadImage("blueSatellite/blueSatellite1");
			BLUE_SATELLITE2 = ImageManager.loadImage("blueSatellite/blueSatellite2");
			BLUE_SATELLITE3 = ImageManager.loadImage("blueSatellite/blueSatellite3");
			BLUE_SATELLITE4 = ImageManager.loadImage("blueSatellite/blueSatellite4");
			BLUE_SATELLITE5 = ImageManager.loadImage("blueSatellite/blueSatellite5");

            GOLD_UFO1 = ImageManager.loadImage("goldUFO/goldUFO1");
			GOLD_UFO2 = ImageManager.loadImage("goldUFO/goldUFO2");
			GOLD_UFO3 = ImageManager.loadImage("goldUFO/goldUFO3");
			GOLD_UFO4 = ImageManager.loadImage("goldUFO/goldUFO4");

			GOLD_SHIP = ImageManager.loadImage("goldShip");
			GOLD_THRUST1 = ImageManager.loadImage("goldThrust/goldThrust1");
			GOLD_THRUST2 = ImageManager.loadImage("goldThrust/goldThrust2");

            BLACK_UFO1 = ImageManager.loadImage("blackUFO/blackUFO1");
			BLACK_UFO2 = ImageManager.loadImage("blackUFO/blackUFO2");
			BLACK_UFO3 = ImageManager.loadImage("blackUFO/blackUFO3");
			BLACK_UFO4 = ImageManager.loadImage("blackUFO/blackUFO4");

			BLACK_MINE1 = ImageManager.loadImage("blackMine/blackMine1");
			BLACK_MINE2 = ImageManager.loadImage("blackMine/blackMine2");
			BLACK_MINE3 = ImageManager.loadImage("blackMine/blackMine3");
			BLACK_MINE4 = ImageManager.loadImage("blackMine/blackMine4");
			BLACK_MINE5 = ImageManager.loadImage("blackMine/blackMine5");
			BLACK_MINE6 = ImageManager.loadImage("blackMine/blackMine6");
			BLACK_MINE7 = ImageManager.loadImage("blackMine/blackMine7");
			BLACK_MINE8 = ImageManager.loadImage("blackMine/blackMine8");
			BLACK_MINE9 = ImageManager.loadImage("blackMine/blackMine9");
			BLACK_MINE10 = ImageManager.loadImage("blackMine/blackMine9");

			BLACK_HOLE1 = ImageManager.loadImage("blackHole/blackHole1");
			BLACK_HOLE2 = ImageManager.loadImage("blackHole/blackHole2");
			BLACK_HOLE3 = ImageManager.loadImage("blackHole/blackHole3");

			SHIELD = ImageManager.loadImage("shieldStar");
			LIFE = ImageManager.loadImage("lifeStar");
			BIG_BULLET = ImageManager.loadImage("bigBulletStar");
			SPEED_BULLET = ImageManager.loadImage("speedBulletStar");
			GOLD = ImageManager.loadImage("goldStar");

			SPACE = ImageManager.loadImage("space");
            EARTH = ImageManager.loadImage("earth");
            TITLE = ImageManager.loadImage("title");
			INSTRUCTIONS = ImageManager.loadImage("instructions");
			HIGH_SCORES = ImageManager.loadImage("highScores");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Image[] RED_UFOS = {RED_UFO1, RED_UFO2, RED_UFO3, RED_UFO4},
			RED_THRUSTS = {RED_THRUST1, RED_THRUST2},
			RED_SATELLITES = {RED_SATELLITE1, RED_SATELLITE2, RED_SATELLITE3, RED_SATELLITE4, RED_SATELLITE5},
			BLUE_UFOS = {BLUE_UFO1, BLUE_UFO2, BLUE_UFO3, BLUE_UFO4},
			BLUE_THRUSTS = {BLUE_THRUST1, BLUE_THRUST2},
			BLUE_SATELLITES = {BLUE_SATELLITE1, BLUE_SATELLITE2, BLUE_SATELLITE3, BLUE_SATELLITE4, BLUE_SATELLITE5},
			GOLD_UFOS = {GOLD_UFO1, GOLD_UFO2, GOLD_UFO3, GOLD_UFO4},
			GOLD_THRUSTS = {GOLD_THRUST1, GOLD_THRUST2},
			BLACK_UFOS = {BLACK_UFO1, BLACK_UFO2, BLACK_UFO3, BLACK_UFO4},
			BLACK_MINES = {BLACK_MINE1, BLACK_MINE2, BLACK_MINE3, BLACK_MINE4, BLACK_MINE5,
					BLACK_MINE6, BLACK_MINE7, BLACK_MINE8, BLACK_MINE9, BLACK_MINE10},
			BLACK_HOLES = {BLACK_HOLE1, BLACK_HOLE2, BLACK_HOLE3};

	public Image image;
	public Vector2D position;
	public Vector2D direction;
	public double width;
	public double height;

	public Sprite(Image image, Vector2D s, Vector2D direction, double width,
				  double height) {
		super();
		this.image = image;
		this.position = s;
		this.direction = direction;
		this.width = width;
		this.height = height;
	}

	public double getRadius() {
		return (width + height) / 4.0;
	}

	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double((position.x - width / 2), position.y - height / 2, width,
				height);
	}

	public void draw(Graphics2D g) {
		double imW = image.getWidth(null);
		double imH = image.getHeight(null);
		AffineTransform t = new AffineTransform();
		t.rotate(direction.angle(), 0, 0);
		t.scale(width / imW, height / imH);
		t.translate(-imW / 2.0, -imH / 2.0);
		AffineTransform t0 = g.getTransform();
		g.translate(position.x, position.y);
		g.drawImage(image, t, null);
		g.setTransform(t0);
	}

}
