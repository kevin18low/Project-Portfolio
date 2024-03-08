package model;

import com.googlecode.lanterna.input.KeyStroke;

import static java.lang.Math.abs;

// Represents a snake with a current direction, length, x position, y position, and color

public class Snake {
    private Direction direction;
    private int snakeLength;
    private int snakeX;
    private int snakeY;
    private String color;

    // Make a snake object going in given direction at position (x, y) with length and colour c
    public Snake(Direction direction, int x, int y, int length, String c) {
        this.direction = direction;
        this.snakeLength = length;
        this.snakeX = x;
        this.snakeY = y;
        this.color = c;
    }

    // MODIFIES: this
    // EFFECTS: change direction according to key pressed
    //          if not a possible turn, nothing happens
    public void turn(KeyStroke ks) {
        if (validTurn(ks)) {
            direction.setDirection(keyDirection(ks));
        }
    }

    //EFFECTS: returns true if the turn is possible based on te current direction
    public boolean validTurn(KeyStroke ks) {
        return !(abs(keyDirection(ks)) == abs(direction.getDirection()));
    }

    // EFFECTS: return direction according to inputted key
    public int keyDirection(KeyStroke ks) {
        switch (ks.getKeyType()) {
            case ArrowUp:
                return 2;
            case ArrowDown:
                return -2;
            case ArrowRight:
                return 1;
            case ArrowLeft:
                return -1;

        }
        return direction.getDirection();
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

    //*************** getters and setters **************

    public int getDirection() {
        return direction.getDirection();
    }

    public int getDx() {
        return direction.getDx();
    }

    public int getDy() {
        return direction.getDy();
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

    public void setColor(String color) {
        this.color = color;
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

    public String getColor() {
        return color;
    }
}
