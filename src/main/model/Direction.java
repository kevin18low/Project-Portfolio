package model;

import static java.lang.Math.*;

public class Direction {
    private int direction;
    private int dx;
    private int dy;

    public Direction(int direction) {
        this.direction = direction;
        this.dx = findDx(direction);
        this.dy = findDy(direction);
    }

    public int findDx(int direction) {
        if (abs(direction) == 1) {
            return direction;
        } else {
            return 0;
        }
    }

    public int findDy(int direction) {
        if (abs(direction) == 2) {
            return direction / (abs(direction));
        } else {
            return 0;
        }
    }

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
