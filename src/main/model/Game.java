package model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.lang.Math.*;

// Represents the game board with a width and height, and boolean values indicating
// whether the game has been paused or is over
// Games in progress have a snake and score

public class Game {
    private int boardWidth;
    private int boardHeight;

    private Snake snake;
    private int score;

    private Food food;
    private int randX = (int)(10 * Math.random());
    private int randY = (int)(10 * Math.random());

    private boolean paused;
    private boolean gameOver;

    // Create a game with given width and height. Starts unpaused
    public Game(int width, int height, String color) {
        this.boardWidth = width;
        this.boardHeight = height;
        this.paused = false;
        this.gameOver = false;
        this.snake = new Snake(new Direction(1), 1, 1, 1, color);
        score = 0;
        this.food = new Food(randX, randY);
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

    // MODIFIES: this
    // EFFECTS: game changes after clock tick. Snake moves in current direction
    public void tick() {
        snake.move();

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
