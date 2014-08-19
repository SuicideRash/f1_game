package F1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    public static final int MAX_V = 50;
    public static final int MAX_LEFT = 50;
    public static final int MAX_RIGHT = 800;

    Image img = new ImageIcon(getClass().getClassLoader().getResource("res/player.png")).getImage();
    Image imgLeft = new ImageIcon(getClass().getClassLoader().getResource("res/player_left.png")).getImage();
    Image imgRight = new ImageIcon(getClass().getClassLoader().getResource("res/player_right.png")).getImage();
    int v = 0;
    int dv = 0;
    int s = 0;

    int x = 300;
    int y = 600;
    int dx = 0;

    int layer1 = 0;
    int layer2 = -1000;

    public Rectangle getRect() {
        return new Rectangle(x, y, 90, 100);
    }

    public void move() {
        s += v;
        v += dv;
        if (v < 0) v = 0;
        if (v > MAX_V) v = MAX_V;
        x += dx;
        if (x > MAX_RIGHT) x = MAX_RIGHT;
        if (x < MAX_LEFT) x = MAX_LEFT;
        if (layer2 + v >= 0) {
            layer1 = 0;
            layer2 = -1000;
        } else {
            layer1 += v;
            layer2 += v;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            dv = 1;
        }
        if (key == KeyEvent.VK_DOWN) {
            dv = -1;
        }
        if (key == KeyEvent.VK_LEFT) {
            if (v != 0) dx = -10;
        }
        if (key == KeyEvent.VK_RIGHT) {
            if (v != 0) dx = 10;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            dv = 0;
        }
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

}
