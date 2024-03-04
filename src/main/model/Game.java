package model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

// Represents the game board with a width and height, and boolean values indicating
// whether the game has been paused or is over

public class Game {
    private int boardWidth;
    private int boardHeight;

    private boolean paused;
    private boolean gameOver;

    public Game(int width, int height) {
        this.boardWidth = width;
        this.boardHeight = height;
        this.paused = false;
        this.gameOver = false;
    }

    //MODIFIES: this
    //EFFECTS: changes paused to true when "p" is pressed
    public void pauseGame(KeyStroke ks) {
        if (ks.getKeyType() == KeyType.Tab) {
            setPaused(true);
        }
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
}
