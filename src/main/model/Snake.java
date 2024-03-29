package model;

import com.googlecode.lanterna.input.KeyStroke;

import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.Color;
import java.lang.reflect.Field;

import static java.lang.Math.abs;

// Represents a snake with a current direction, length, x position, y position, and color

public class Snake {
    private Direction direction;
    private int snakeLength;
    private Position head;
    private ArrayList<Position> body;
    private int snakeX;
    private int snakeY;
    private String color;

    // Make a snake object going in given direction at position (x, y) with length and colour c
    public Snake(Direction direction, int x, int y, int length, String c) {
        this.direction = direction;
        this.snakeLength = length;
        this.head = new Position(x, y);
        this.body = new ArrayList<>();
        this.color = c;
    }

    // Constructor for saving snake in progress
    public Snake(Direction direction, Position head, ArrayList<Position> body, int length, String c) {
        this.direction = direction;
        this.snakeLength = length;
        this.head = head;
        this.body = body;
        this.color = c;
    }

    // MODIFIES: this
    // EFFECTS: change direction according to key pressed
    //          if not a possible turn, nothing happens
    public void turn(KeyEvent ke) {
        if (validTurn(ke)) {
            direction.setDirection(keyDirection(ke));
        }
    }

    //EFFECTS: returns true if the turn is possible based on te current direction
    public boolean validTurn(KeyEvent ke) {
        return !(abs(keyDirection(ke)) == abs(direction.getDirection()));
    }

    // EFFECTS: return direction according to inputted key
    public int keyDirection(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_UP:
                return -2;
            case KeyEvent.VK_DOWN:
                return 2;
            case KeyEvent.VK_RIGHT:
                return 1;
            case KeyEvent.VK_LEFT:
                return -1;

        }
        return direction.getDirection();
    }

    //*************** getters and setters **************
    public Position getHead() {
        return head;
    }

    public ArrayList<Position> getBody() {
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

    public Color getColorByName() {
        try {
            Field field = Color.class.getField(color);
            return (Color) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
