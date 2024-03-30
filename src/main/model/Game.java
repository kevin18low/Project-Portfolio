package model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.lang.Math.*;
import java.util.Random;

// Represents the game board with a certain tile size and boolean values indicating
// whether the game has been paused or is over
// Games in progress have a snake, score, and food location

public class Game {
    private int boardWidth = 600;
    private int boardHeight = boardWidth;
    private int tileSize;

    private Snake snake;
    private int score;

    private Position food;

    private boolean paused;
    private boolean gameOver;

    // Create a game with given width and height. Starts unpaused
    public Game(String color, int tileSize) {
        this.paused = false;
        this.gameOver = false;
        this.snake = new Snake(new Direction(1), 3, 3, 1, color);
        this.food = new Position(10, 10);
        this.score = 0;
        this.tileSize = tileSize;
    }

    // Constructor for saving games in progress
    public Game(int width, int height, Snake s, int score, Position f, int tileSize) {
        this.gameOver = false;
        this.snake = s;
        this.score = score;
        this.food = f;
        this.tileSize = tileSize;
    }

    // MODIFIES: this
    // EFFECTS: changes paused to true when "p" is pressed
    public void pauseGame(KeyStroke ks) {
        if (ks.getKeyType() == KeyType.Tab) {
            setPaused(true);
        }
    }

    // MODIFIES: this
    // EFFECTS; moves the snake, relocates food, or ends game if conditions are met
    public void moveSnake(Position food, Random random) {
        if (collision(getSnake().getHead(), food)) {
            getSnake().getBody().add(new Position(food.getPosX(), food.getPosY()));
            placeFood(food, random, tileSize);
        }

        for (int i = getSnake().getBody().size() - 1; i >= 0; i--) {
            Position snakePart = getSnake().getBody().get(i);
            if (i == 0) {
                snakePart.setPosX(getSnake().getHead().getPosX());
                snakePart.setPosY(getSnake().getHead().getPosY());
            } else {
                Position prevSnakePart = getSnake().getBody().get(i - 1);
                snakePart.setPosX(prevSnakePart.getPosX());
                snakePart.setPosY(prevSnakePart.getPosY());
            }
        }
        getSnake().getHead().setPosX(getSnake().getHead().getPosX() + getSnake().getDx());
        getSnake().getHead().setPosY(getSnake().getHead().getPosY() + getSnake().getDy());

        for (int i = 0; i < getSnake().getBody().size(); i++) {
            Position snakePart = getSnake().getBody().get(i);

            if (collision(getSnake().getHead(), snakePart)) {
                gameOver = true;
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: place food somewhere random
    public void placeFood(Position food, Random random, int tileSize) {
        int maxX = (boardWidth / tileSize) - 4;
        int maxY = (boardHeight / tileSize) - 4;
        food.setPosX(random.nextInt(maxX));
        food.setPosY(random.nextInt(maxY));
    }

    // EFFECTS: checks if the snake head hits anything
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

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

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

    public void setScore() {
        this.score = snake.getSnakeLength();
    }

    public Position getFood() {
        return food;
    }

    public void setFood(Position food) {
        this.food = food;
    }
}
