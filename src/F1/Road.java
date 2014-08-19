package F1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Road extends JPanel implements ActionListener, Runnable {

    Timer mainTimer = new Timer(20, this);

    Image img = new ImageIcon(getClass().getClassLoader().getResource("res/road.jpg")).getImage();
    Player p = new Player();


    Thread enemiesFactory = new Thread(this);
    List<Enemy> enemies = new ArrayList<Enemy>();

    public Road() {
        mainTimer.start();
        enemiesFactory.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    @Override
    public void run() {
        while (true) {
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(2000));
                enemies.add(new Enemy(rand.nextInt(750) + 50, -100, rand.nextInt(50), this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyKeyAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            p.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            p.keyReleased(e);
        }

    }

    public void paint(Graphics g) {
        g = (Graphics2D) g;
        g.drawImage(img, 0, p.layer1, null);
        g.drawImage(img, 0, p.layer2, null);

        Double v = (200.0 / Player.MAX_V) * p.v;
        g.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.ITALIC, 20);
        g.setFont(font);
        g.drawString("Speed:", 10, 30);
        g.drawString(String.valueOf(v) + "km/h", 10, 50);
        if (p.dx < 0) {
            g.drawImage(p.imgLeft, p.x, p.y, null);
        } else if (p.dx > 0) {
            g.drawImage(p.imgRight, p.x, p.y, null);
        } else {
            g.drawImage(p.img, p.x, p.y, null);
        }

        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (e.y >= 2000 || e.y <= -1000) {
                i.remove();
            } else {
                e.move();
                g.drawImage(e.img, e.x, e.y, null);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        p.move();
        repaint();
        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy enemy = i.next();
            enemy.move();
        }
        testCollisionWithEnemies();
        testWin();
    }

    private void testWin() {
        if (p.s >= 50000) {
            JOptionPane.showMessageDialog(null, "You win!!!");
            System.exit(0);
        }
    }

    private void testCollisionWithEnemies() {
        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (p.getRect().intersects(e.getRect())) {
                JOptionPane.showMessageDialog(null, "You lose!!!");
                System.exit(1);
            }
        }
    }
}
