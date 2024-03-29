package ui;

import model.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //snake
    Position snakeHead;
    ArrayList<Position> snakeBody;

    //food
    Position food;
    Random random;

    //game logic
    int velocityX;
    int velocityY;
    Timer gameLoop;

    private Game game;
    boolean gameOver = false;

    public SnakeGame(Game game) {
        this.game = game;
        this.boardWidth = game.getBoardWidth();
        this.boardHeight = game.getBoardHeight();
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = game.getSnake().getHead();
        snakeBody = game.getSnake().getBody();

        food = new Position(10, 10);
        random = new Random();
        placeFood();

        velocityX = game.getSnake().getDx();
        velocityY = game.getSnake().getDy();

        //game timer
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //Grid Lines
        for (int i = 0; i < boardWidth / tileSize; i++) {
            //(x1, y1, x2, y2)
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }

        //Food
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
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over! Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood() {
        food.setPosX(random.nextInt(boardWidth / tileSize));
        food.setPosY(random.nextInt(boardHeight / tileSize));
    }

    public void move() {
        game.moveSnake(food, random);

        if (snakeHead.getPosX() * tileSize < 0
                || snakeHead.getPosX() * tileSize > boardWidth //passed left border or right border
                || snakeHead.getPosY() * tileSize < 0
                || snakeHead.getPosY() * tileSize > boardHeight) { //passed top border or bottom border
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.getSnake().turn(e);
        velocityX = game.getSnake().getDx();
        velocityY = game.getSnake().getDy();
    }

    //not needed
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}