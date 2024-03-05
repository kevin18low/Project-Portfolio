package model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.awt.*;

// Represents the game board with a width and height, and boolean values indicating
// whether the game has been paused or is over

public class Game {
    private int boardWidth;
    private int boardHeight;

    private Snake snake;
    private int score;

    private boolean paused;
    private boolean gameOver;

    // Create a game with given width and height. Starts unpaused
    public Game(int width, int height, Color color) {
        this.boardWidth = width;
        this.boardHeight = height;
        this.paused = false;
        this.gameOver = false;
        this.snake = new Snake(new Direction(1), 1, 1, color);
        score = 0;
    }

    //MODIFIES: this
    //EFFECTS: changes paused to true when "p" is pressed
    public void pauseGame(KeyStroke ks) {
        if (ks.getKeyType() == KeyType.Tab) {
            setPaused(true);
        }
    }

    public String toString() {
        return "Current game: "
                + "Board of width " + boardWidth
                + ", height of " + boardHeight
                + ", snake of length " + snake.getSnakeLength()
                + " with direction " + snake.getDirection()
                + ", position (" + snake.getSnakeX() + ", "
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
}
