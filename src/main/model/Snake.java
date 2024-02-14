package model;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.lang.Math.abs;

// Represents a snake with a current direction, length, x position, y position, and color

public class Snake {
    private int direction; // -1,1 for left and right, -2,2 for down and up
    private int snakeLength;
    private int snakeX;
    private int snakeY;
    private Color color;

    public Snake(int direction, int x, int y, Color c) {
        this.direction = direction;
        this.snakeLength = 1;
        this.snakeX = x;
        this.snakeY = y;
        this.color = c;
    }

    // MODIFIES: this
    // EFFECTS: changes direction based on a key input. If not a possible turn, nothing changes
    public void turn(int key) {
        if (validTurn(key)) {
            direction = keyDirection(key);
        }
    }

    //EFFECTS: returns true if the turn is possible based on te current direction
    public boolean validTurn(int key) {
        return !(abs(keyDirection(key)) == abs(direction));
    }

    //EFFECTS: returns direction number corresponding to different arrow key inputs
    public int keyDirection(int key) {
        if (key == KeyEvent.VK_UP) {
            return 2;
        } else if (key == KeyEvent.VK_DOWN) {
            return -2;
        } else if (key == KeyEvent.VK_LEFT) {
            return -1;
        } else if (key == KeyEvent.VK_RIGHT) {
            return 1;
        } else {
            return this.direction;
        }
    }

    // MODIFIES: this, Food f
    // EFFECTS: returns true if snake has eaten food
    //          adds 1 to snakeLength and changes Food f eaten to true;
    public boolean ateFood(Food f) {
        if (snakeX == f.getFoodX() && snakeY == f.getFoodY()) {
            snakeLength++;
            f.setEaten(true);
            return true;
        }
        return false;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setSnakeLength(int snakeLength) {
        this.snakeLength = snakeLength;
    }

    public void setSnakeX(int snakeX) {
        this.snakeX = snakeX;
    }

    public void setSnakeY(int snakeY) {
        this.snakeY = snakeY;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDirection() {
        return direction;
    }

    public int getSnakeLength() {
        return snakeLength;
    }

    public int getSnakeX() {
        return snakeX;
    }

    public int getSnakeY() {
        return snakeY;
    }

    public Color getColor() {
        return color;
    }
}
