package model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.lang.Math.*;
import java.util.Random;

// Represents the game board with a width and height, and boolean values indicating
// whether the game has been paused or is over
// Games in progress have a snake and score

public class Game {
    private int boardWidth;
    private int boardHeight;
    private int tileSize = 25;

    private Snake snake;
    private int score;

    private Food food;

    private boolean paused;
    private boolean gameOver;

    // Create a game with given width and height. Starts unpaused
    public Game(int width, int height, String color) {
        this.boardWidth = width;
        this.boardHeight = height;
        this.paused = false;
        this.gameOver = false;
        this.snake = new Snake(new Direction(1), 3, 3, 1, color);
        this.score = 0;
    }

    // Constructor for saving games in progress
    public Game(int width, int height, Snake s, int score, Food f) {
        this.boardWidth = width;
        this.boardHeight = height;
        this.snake = s;
        this.score = score;
        this.food = f;
    }

    // MODIFIES: this
    // EFFECTS: changes paused to true when "p" is pressed
    public void pauseGame(KeyStroke ks) {
        if (ks.getKeyType() == KeyType.Tab) {
            setPaused(true);
        }
    }

    public void moveSnake(Position food, Random random) {
        if (collision(getSnake().getHead(), food)) {
            getSnake().getBody().add(new Position(food.getPosX(), food.getPosY()));
            placeFood(food, random, boardWidth, boardHeight, tileSize);
        }

        //move snake body
        for (int i = getSnake().getBody().size() - 1; i >= 0; i--) {
            Position snakePart = getSnake().getBody().get(i);
            if (i == 0) { //right before the head
                snakePart.setPosX(getSnake().getHead().getPosX());
                snakePart.setPosY(getSnake().getHead().getPosY());
            } else {
                Position prevSnakePart = getSnake().getBody().get(i - 1);
                snakePart.setPosX(prevSnakePart.getPosX());
                snakePart.setPosY(prevSnakePart.getPosY());
            }
        }
        //move snake head
        getSnake().getHead().setPosX(getSnake().getHead().getPosX() + getSnake().getDx());
        getSnake().getHead().setPosY(getSnake().getHead().getPosY() + getSnake().getDy());

        //game over conditions
        for (int i = 0; i < getSnake().getBody().size(); i++) {
            Position snakePart = getSnake().getBody().get(i);

            //collide with snake head
            if (collision(getSnake().getHead(), snakePart)) {
                gameOver = true;
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: eat food. Length, score, and body length increase by 1
    public void placeFood(Position food, Random random, int boardWidth, int boardHeight, int tileSize) {
        if (collision(getSnake().getHead(), food)) {
            getSnake().getBody().add(new Position(food.getPosX(), food.getPosY()));
            food.setPosX(random.nextInt(boardWidth / tileSize));
            food.setPosY(random.nextInt(boardHeight / tileSize));
        }
    }

    public boolean collision(Position p1, Position p2) {
        return p1.getPosX() == p2.getPosX() && p1.getPosY() == p2.getPosY();
    }

    // EFFECTS: convert game to string
    public String toString() {
        return "Board width: " + boardWidth
                + ", height: " + boardHeight
                + ", snake length: " + snake.getSnakeLength()
                + ", direction: " + snake.getDirection()
                + ", head position: (" + snake.getSnakeX() + ", "
                + snake.getSnakeY() + ")";
    }

    //*************** getters and setters **************

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Snake getSnake() {
        return this.snake;
    }

    public int getScore() {
        return score;
    }

    public Food getFood() {
        return food;
    }
}
