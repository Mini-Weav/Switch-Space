package game;

import utilities.SoundManager;
import utilities.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import static game.Constants.*;

/*
    The Menu screen
    Used to view high scores and instructions, set the difficulty, and start the game
 */
public class Menu extends JComponent {

    static int SCREEN = 0;
    static JPanel buttons = new JPanel();
    static JButton PLAY = new JButton("Play");
    static JButton HIGH_SCORES = new JButton("High Scores");
    static JButton HOW_TO_PLAY = new JButton("How to Play");
    static JButton EASY = new JButton("Easy");
    static JButton MEDIUM = new JButton("Medium");
    static JButton HARD = new JButton("Hard");
    static Title title = new Title();
    static ArrayList<Integer> SCORES;

    public Menu() {
        setLayout(new BorderLayout());

        buttons.setLayout(new GridLayout(2, 3));
        buttons.add(PLAY);
        buttons.add(HIGH_SCORES);
        buttons.add(HOW_TO_PLAY);
        buttons.add(EASY);
        buttons.add(MEDIUM);
        buttons.add(HARD);

        for (int i = 0; i < buttons.getComponentCount(); i++) {
            buttons.getComponent(i).setBackground(Color.BLUE);
            buttons.getComponent(i).setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            buttons.getComponent(i).setForeground(Color.RED);
            buttons.getComponent(i).setFocusable(false);
        }

        buttons.getComponent(4).setBackground(Color.RED);
        buttons.getComponent(4).setForeground(Color.BLUE);

        PLAY.addActionListener(new ButtonListener());
        HIGH_SCORES.addActionListener(new ButtonListener());
        HOW_TO_PLAY.addActionListener(new ButtonListener());
        EASY.addActionListener(new ButtonListener());
        MEDIUM.addActionListener(new ButtonListener());
        HARD.addActionListener(new ButtonListener());

        this.add(buttons, BorderLayout.SOUTH);
        this.add(title, BorderLayout.CENTER);

        title.setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));
    }

    public void paintComponent(Graphics g0) {
        for (int i = 0; i < buttons.getComponentCount(); i++) {
            buttons.getComponent(i).repaint();
        }
        title.repaint();
    }

    public Dimension getPreferredSize() {
        return MENU_SIZE;
    }
}

class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        SoundManager.beep();
        String button = ((JButton) e.getSource()).getText();

        if (button.equals("Play")) {
            Game.START = true;
            Menu.SCREEN = 0;
        }
        if (button.equals("How to Play")) {
            Menu.SCREEN = 1;
        }
        if (button.equals("High Scores")) {
            int score;
            Menu.SCORES = new ArrayList<>();
            String string;

            InputStream in;
            InputStreamReader inr;
            BufferedReader br;

            //Reads the highScores.txt file and puts the values in an ArrayList of Integers
            try {
                in = new FileInputStream("highScores.txt");
                inr = new InputStreamReader(in);
                br = new BufferedReader(inr);
                try {
                    while ((string = br.readLine()) != null) {
                        try {
                            score = Integer.parseInt(string);
                            Menu.SCORES.add(score);
                            Collections.sort(Menu.SCORES, Collections.reverseOrder());
                        }
                        catch (NumberFormatException ignored) {}
                    }
                }
                catch (IOException f) { System.out.println("Error reading file."); }
                try { in.close(); }
                catch (IOException f) { System.out.println("Error closing file."); }
            }
            catch (FileNotFoundException f) { System.out.println("Error opening file."); }

            Menu.SCREEN =2;
        }
        if (button.equals("Easy")) {
            highlightButton(true, false, false);
            Game.DIFFICULTY = 0;
        }
        if (button.equals("Medium")) {
            highlightButton(false, true, false);
            Game.DIFFICULTY = 1;
        }
        if (button.equals("Hard")) {
            highlightButton(false, false, true);
            Game.DIFFICULTY = 2;
        }
    }

    //Paints the selected difficulty button in red and other difficulty buttons in blue
    private void highlightButton(boolean easy, boolean medium, boolean hard) {
        if (easy) {
            Menu.buttons.getComponent(3).setBackground(Color.RED);
            Menu.buttons.getComponent(3).setForeground(Color.BLUE);
        }
        else {
            Menu.buttons.getComponent(3).setBackground(Color.BLUE);
            Menu.buttons.getComponent(3).setForeground(Color.RED);
        }
        if (medium) {
            Menu.buttons.getComponent(4).setBackground(Color.RED);
            Menu.buttons.getComponent(4).setForeground(Color.BLUE);
        }
        else {
            Menu.buttons.getComponent(4).setBackground(Color.BLUE);
            Menu.buttons.getComponent(4).setForeground(Color.RED);
        }
        if (hard) {
            Menu.buttons.getComponent(5).setBackground(Color.RED);
            Menu.buttons.getComponent(5).setForeground(Color.BLUE);
        }
        else {
            Menu.buttons.getComponent(5).setBackground(Color.BLUE);
            Menu.buttons.getComponent(5).setForeground(Color.RED);
        }
    }
}

/*
    The image displayed above the buttons
 */
class Title extends JComponent {
    Image im0 = Sprite.SPACE, im1 = Sprite.TITLE, im2 = Sprite.INSTRUCTIONS, im3 = Sprite.HIGH_SCORES;
    AffineTransform bgTransf;

    public Title() {
        double imWidth = im1.getWidth(null);
        double imHeight = im1.getHeight(null);
        double stretchx = (imWidth > MENU_WIDTH? 1 : MENU_WIDTH/imWidth);
        double stretchy = (imHeight > MENU_HEIGHT? 1 : MENU_HEIGHT/imHeight);
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
    }

    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.drawImage(im0, bgTransf, null);
        //Home screen
        if (Menu.SCREEN == 0) {
            g.drawImage(im1, (MENU_WIDTH - im1.getWidth(null)) / 2, (MENU_HEIGHT - im1.getHeight(null)-100) / 2,
                    im1.getWidth(null), im1.getHeight(null), null);
        }
        //Instructions screen
        if (Menu.SCREEN == 1) {
            g.drawImage(im2, (MENU_WIDTH - im2.getWidth(null) + 8) / 2, (MENU_HEIGHT - im2.getHeight(null)-65) / 2,
                    im2.getWidth(null), im2.getHeight(null), null);
        }
        //High Scores screen
        if (Menu.SCREEN == 2) {
            g.drawImage(im3, (MENU_WIDTH - im3.getWidth(null) + 8) / 2, (MENU_HEIGHT - im3.getHeight(null)-65) / 2,
                    im3.getWidth(null), im3.getHeight(null), null);
            g.setColor(Color.RED);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
            if (Menu.SCORES.isEmpty()) {
                g.drawString("No Scores", 40, 120);
            }
            else {
                for (int i = 0; i < Menu.SCORES.size(); i++) {
                    if (i < 6) {
                        g.drawString((i+1)+". "+Menu.SCORES.get(i).toString(),40,120+i*50);
                    }
                    else if (i < 12) {
                        g.drawString((i+1)+". "+Menu.SCORES.get(i).toString(),340,120+(i-6)*50);
                    }
                }
            }
        }

    }
}

