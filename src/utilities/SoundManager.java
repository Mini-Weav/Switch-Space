/*
	Music:
		Credit: Shalpin
		Source: http://opengameart.org/content/keith
	Sound Effects:
		Credit: Kenney.nl
		Source: http://opengameart.org/content/63-digital-sound-effects-lasers-phasers-space-etc
*/

package utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {

	static int nBullet = 0;

	final static String path = "sounds/";

	public final static Clip[] bullets = new Clip[15];

	public final static Clip asteroidExplode = getClip("Explosion18");
    public final static Clip beep = getClip("Blip_Select14");
    public final static Clip earthExplode = getClip("Explosion15");
	public final static Clip fire = getClip("Laser_Shoot4");
	public final static Clip levelUp = getClip("Powerup18");
	public final static Clip mineExplode = getClip("Explosion20");
	public final static Clip mineTimer1 = getClip("Blip_Select");
	public final static Clip mineTimer2 = getClip("Blip_Select18");
	public final static Clip music = getClip("keith");
	public final static Clip noSound = getClip("default");
    public final static Clip oneUp = getClip("Powerup5");
    public final static Clip shield = getClip("Powerup2");
	public final static Clip shift = getClip("Powerup");
	public final static Clip shipExplode = getClip("Explosion11");
	public final static Clip spawn = getClip("Powerup14");
	public final static Clip thrust = getClip("Explosion16");

	public final static Clip[] clips = {asteroidExplode, fire, levelUp, mineExplode, mineTimer1,
			mineTimer2, oneUp, shift, shipExplode, spawn, thrust};
	
 static {
		for (int i = 0; i < bullets.length; i++)
			bullets[i] = getClip("Laser_Shoot4");
	}

    public static void loadSounds() throws Exception {
        for (Clip clip : clips) {
            clip.open();
        }
    }

	public static void play(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}

	private static Clip getClip(String filename) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
					+ filename + ".wav"));
			clip.open(sample);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	public static void fire() {
		Clip clip = bullets[nBullet];
		clip.setFramePosition(0);
		clip.start();
		nBullet = (nBullet + 1) % bullets.length;
	}

	public static void startMusic() {
		music.loop(-1);
	}
	public static void stopMusic() {
		music.stop();
	}

	public static void startThrust() {
		thrust.loop(-1);
	}
	public static void stopThrust() {
		thrust.stop();
	}

    public static void startShield() {
        shield.loop(-1);
    }
	public static void stopShield() {
		shield.stop();
	}

    public static void beep() { play(beep); }

	public static void levelUp() { play(levelUp); }

    public static void oneUp() { play(oneUp); }

	public static void shift() { play(shift); }

    public static void spawn() { play(spawn); }

}
