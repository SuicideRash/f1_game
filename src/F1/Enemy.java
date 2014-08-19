package F1;

import javax.swing.*;
import java.awt.*;

public class Enemy {

    int x;
    int y;
    int v;
    Image img = new ImageIcon(getClass().getClassLoader().getResource("res/enemy.png")).getImage();

    Road road;

    public Rectangle getRect() {
        return new Rectangle(x, y, 90, 100);
    }

    public Enemy(int x, int y, int v, Road road) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.road = road;
    }

    public void move() {
        y = y + road.p.v - v;
    }

}
