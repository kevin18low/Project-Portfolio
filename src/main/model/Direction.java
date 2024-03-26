package model;

import static java.lang.Math.*;

// Represents the direction that the snake is facing, and the speed it is going at in
// the x and y directions

public class Direction {
    private int direction;
    private int dx;
    private int dy;

    // Create a direction representing x or y directions with a respective dx and dy
    public Direction(int direction) {
        this.direction = direction;
        this.dx = findDx(direction);
        this.dy = findDy(direction);
    }

    // EFFECTS: gets dx based on current direction
    public int findDx(int direction) {
        if (abs(direction) == 1) {
            return direction;
        } else {
            return 0;
        }
    }

    // EFFECTS: gets dy based on current direction
    public int findDy(int direction) {
        if (abs(direction) == 2) {
            return direction / (abs(direction));
        } else {
            return 0;
        }
    }

    //*************** getters and setters **************

    public int getDirection() {
        return this.direction;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }

    public void setDirection(int dir) {
        this.direction = dir;
        this.dx = findDx(dir);
        this.dy = findDy(dir);
    }

}
