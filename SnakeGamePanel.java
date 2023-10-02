import java.util.Random;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGamePanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 6; // how big objects in the game
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    private int speedDifficulty = 50; // speed of game
    private boolean gridDisplay = true;

    // snake coordinates
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int bodyParts = 10; // init snake

    private int appleX;
    private int appleY;
    private int applesEaten;

    private char direction = 'D'; // W, A, S, D
    private boolean running = false;
    private Timer timer;
    private Random random;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }

        repaint();
    }

    public SnakeGamePanel(int speedDifficulty, boolean gridDisplay) {
        this.speedDifficulty = speedDifficulty;
        this.gridDisplay = gridDisplay;
        random = new Random();
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyAdapter());

        startGame();
    }

    public void startGame() {
        addApple();
        running = true;
        timer = new Timer(speedDifficulty, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // grid
            if (gridDisplay) {
                for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                    g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                    g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }
            }

            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                // if true, then we're dealing with snake head
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.BLUE); // ligher green
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            // Drawing the score
            // gameScore(g);
        } else {
            gameOver(g);
        }
    }

    public void addApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
            // System.out.println(x[i] + " " + y[i]);
        }

        // snake head
        switch (direction) {
            case 'W':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'S':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'A':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'D':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

        // System.out.println("x: " + x[0] + "\ty: " + y[0]);
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts += 1; // increase more
            applesEaten++;
            addApple();
        }
    }

    public void checkCollisions() {
        // if head collides body
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        // if head collides left/right border
        if (x[0] < 0 || x[0] > SCREEN_WIDTH - 1) {
            running = false;
        }

        // if head collides up/down border
        if (y[0] < 0 || y[0] > SCREEN_HEIGHT - 1) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }

    }

    public void gameOver(Graphics g) {
        gameScore(g);

        Graphics2D g2D = (Graphics2D) g;
        // g2D.drawLine(600, 0, 0, 600);
        // g2D.drawLine(0, 0, 600, 600);
        g2D.setStroke(new BasicStroke());
        g2D.setPaint(Color.RED);
        g2D.drawRect(60, 235, 483, 80);
        g2D.setColor(Color.WHITE);
        g2D.fillRect(60 + 3, 235 + 3, 483 - 6, 80 - 6);

        String str = "GAME OVER";
        g.setColor(Color.RED);
        // g.setFont(new Font("Times New Roman", Font.BOLD, 75));
        g.setFont(new Font("Baskerville Old Face", Font.BOLD, 75));

        // line-up text on screen
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString(str, (SCREEN_WIDTH - fontMetrics.stringWidth(str)) / 2, SCREEN_HEIGHT / 2);
    }

    public void gameScore(Graphics g) {
        String str = "SCORE: " + applesEaten;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));

        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString(str, (SCREEN_WIDTH - fontMetrics.stringWidth(str)) / 2, g.getFont().getSize());
    }

    public class MyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'D')
                        direction = 'A';
                    break;
                case KeyEvent.VK_A:
                    if (direction != 'D')
                        direction = 'A';
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'A')
                        direction = 'D';
                    break;
                case KeyEvent.VK_D:
                    if (direction != 'A')
                        direction = 'D';
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'S')
                        direction = 'W';
                    break;
                case KeyEvent.VK_W:
                    if (direction != 'S')
                        direction = 'W';
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'W')
                        direction = 'S';
                    break;
                case KeyEvent.VK_S:
                    if (direction != 'W')
                        direction = 'S';
                    break;
            }
        }

    }

}
