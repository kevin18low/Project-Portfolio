package model;

import com.googlecode.lanterna.input.KeyStroke;

import java.util.*;

import static java.lang.Math.abs;

// Represents a snake with a current direction, length, x position, y position, and color

public class Snake {
    private Direction direction;
    private int snakeLength;
    private Position head;
    private List<Position> body;
    private int snakeX;
    private int snakeY;
    private String color;

    // Make a snake object going in given direction at position (x, y) with length and colour c
    public Snake(Direction direction, int x, int y, int length, String c) {
        this.direction = direction;
        this.snakeLength = length;
//        this.snakeX = x;
//        this.snakeY = y;
        this.head = new Position(x, y);
        this.body = new ArrayList<>();
        this.color = c;
    }

    // Constructor for saving snake in progress
    public Snake(Direction direction, Position head, List<Position> body, int length, String c) {
        this.direction = direction;
        this.snakeLength = length;
        this.head = head;
        this.body = body;
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
        if (head.getPosX() == f.getFoodX() && head.getPosY() == f.getFoodY()) {
            snakeLength++;
            f.setEaten(true);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: entire snake position changes. Head moves, and all body parts
    public void move() {
        if (body.size() > 0) {
            body.remove(body.size() - 1);
        }
        body.add(0, head);
        if (direction.getDx() == 0) {
            head.setPosY(head.getPosY() + direction.getDy());
        } else {
            head.setPosX(head.getPosX() + direction.getDx());
        }

    }

    // MODIFIES: this
    // EFFECTS: eat food. Length, score, and body length increase by 1
    public void eatFood() {
        // eat
    }

    //*************** getters and setters **************
    public Position getHead() {
        return head;
    }

    public List<Position> getBody() {
        return body;
    }

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
