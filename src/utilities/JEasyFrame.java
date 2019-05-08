package utilities;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lmweav on 15/01/2016.
 */
public class JEasyFrame extends JFrame {
    public Component comp;
    public JEasyFrame(Component comp, String title) {
        super(title);
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        this.setVisible(true);

        //Make the frame fixed size and in centre of the screen
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
}
