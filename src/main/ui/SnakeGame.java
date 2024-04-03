package ui;

import model.*;
import ui.tabs.Tab;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

// Credit: YouTube- KennyYipCoding
// Runs a game of snake
public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private static final int boardWidth = 600;
    private static final int boardHeight = boardWidth;
    private int tileSize;

    private Position snakeHead;
    private ArrayList<Position> snakeBody;

    private Position food;
    private Random random;

    private int velocityX;
    private int velocityY;
    private Timer gameLoop;

    private Game game;
    private Player player;
    private Tab tab;
    private JButton save = new JButton("Save Game");
    private JButton pause = new JButton("Pause");

    // Create a snake game with given game for given player
    public SnakeGame(Game game, Player player, Tab tab) {
        this.game = game;
        this.player = player;
        this.tab = tab;
        this.tileSize = game.getTileSize();
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        addButtons();

        snakeHead = game.getSnake().getHead();
        snakeBody = game.getSnake().getBody();

        food = game.getFood();
        random = new Random();
        game.placeFood(random);

        velocityX = game.getSnake().getDx();
        velocityY = game.getSnake().getDy();

        //game timer
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    // MODIFIES: this
    // EFFECTS: adds save and pause buttons to game screen
    public void addButtons() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Save Game")) {
                    player.setGame(game);
                    game.setScore();
                    game.setFood(food);
                    tab.savePlayerBase();
                }
            }
        });
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Pause")) {
                    togglePause();
                }
            }
        });
        add(save);
        add(pause);
    }

    // MODIFIES: game
    // EFFECTS: pauses game
    public void togglePause() {
        game.setPaused(!game.isPaused());
        if (game.isPaused()) {
            gameLoop.stop(); // Pause the game loop
            pause.setFocusable(true);
        } else {
            gameLoop.start(); // Resume the game loop
            pause.setFocusable(false);
            requestFocusInWindow();
        }
    }

    // MODIFIES: this
    // EFFECTS: draw game
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // MODIFIES: this
    // EFFECTS: add colours to elements
    public void draw(Graphics g) {
        for (int i = 0; i < boardWidth / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }

        // food
        g.setColor(Color.red);
        g.fill3DRect(food.getPosX() * tileSize, food.getPosY() * tileSize, tileSize, tileSize, true);

        //Snake Head
        g.setColor(game.getSnake().getColorByName());
        g.fill3DRect(snakeHead.getPosX() * tileSize, snakeHead.getPosY() * tileSize,
                tileSize, tileSize, true);

        //Snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Position snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.getPosX() * tileSize, snakePart.getPosY() * tileSize,
                    tileSize, tileSize, true);
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.setColor(Color.white);
        if (game.isGameOver()) {
            g.setColor(Color.red);
            g.drawString("Game Over! Score: " + snakeBody.size(), tileSize - 16, tileSize);
        } else {
            g.drawString("Score: " + snakeBody.size(), tileSize - 16, tileSize);
        }
    }

    // MODIFIES: this
    // EFFECTS: move the snake
    public void move() {
        game.moveSnake(food, random);

        if (snakeHead.getPosX() * tileSize < 0
                || snakeHead.getPosX() * tileSize >= boardWidth // passed left border or right border
                || snakeHead.getPosY() * tileSize < 0
                || snakeHead.getPosY() * tileSize >= boardHeight) { // passed top border or bottom border
            game.setGameOver(true);
        }
    }

    // MODIFIES: game
    // EFFECTS: behaviour of button presses
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (game.isGameOver()) {
            game.setScore();
            player.addScore(game.getScore());
            player.setGame(new Game("green", 25));
            tab.savePlayerBase();
            gameLoop.stop();
        }
    }

    // MODIFIES: game
    // EFFECTS: turns snake when keys are pressed
    @Override
    public void keyPressed(KeyEvent e) {
        game.getSnake().turn(e);
    }

    // not needed
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}