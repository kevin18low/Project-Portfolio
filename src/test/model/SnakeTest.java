package model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    private Snake s1;
    private Snake s2;
    private KeyStroke right;
    private KeyStroke left;
    private KeyStroke up;
    private KeyStroke down;
    private KeyStroke badInput;

    @BeforeEach
    public void setup() {
        s1 = new Snake(new Direction(1), 50, 50, 1,"green");
        s2 = new Snake(new Direction(-2), 15, 70, 10, "blue");
        right = new KeyStroke(KeyType.ArrowRight);
        left = new KeyStroke(KeyType.ArrowLeft);
        up = new KeyStroke(KeyType.ArrowUp);
        down = new KeyStroke(KeyType.ArrowDown);
        badInput = new KeyStroke(KeyType.Delete);
    }

    @Test
    public void turnTestSameDirection() {
        assertEquals(s1.getDirection(), 1);
        s1.turn(right);
        assertEquals(s1.getDirection(), 1);
        assertEquals(s2.getDirection(), -2);
        s2.turn(down);
        assertEquals(s2.getDirection(), -2);
    }

    @Test
    public void turnTestOppositeDirection() {
        assertEquals(s1.getDirection(), 1);
        s1.turn(left);
        assertEquals(s1.getDirection(), 1);
        assertEquals(s2.getDirection(), -2);
        s2.turn(up);
        assertEquals(s2.getDirection(), -2);
    }

    @Test
    public void turnTestValidTurn() {
        assertEquals(s1.getDirection(), 1);
        s1.turn(up);
        assertEquals(s1.getDirection(), 2);
        s1.turn(left);
        assertEquals(s1.getDirection(), -1);
        assertEquals(s2.getDirection(), -2);
        s2.turn(right);
        assertEquals(s2.getDirection(), 1);
        s2.turn(down);
        assertEquals(s2.getDirection(), -2);
    }

    @Test
    public void validTurnTestInvalidTurns() {
        assertFalse(s1.validTurn(right));
        assertFalse(s1.validTurn(left));
        s1.turn(down);
        assertFalse(s1.validTurn(up));

        assertFalse(s2.validTurn(up));
        assertFalse(s2.validTurn(down));
        s2.turn(left);
        assertFalse(s2.validTurn(right));
    }

    @Test
    public void validTurnTestValidTurns() {
        assertTrue(s1.validTurn(up));
        assertTrue(s1.validTurn(down));
        s1.turn(down);
        assertTrue(s1.validTurn(right));

        assertTrue(s2.validTurn(right));
        assertTrue(s2.validTurn(left));
        s2.turn(right);
        assertTrue(s2.validTurn(down));
    }

    @Test
    public void keyDirectionTest() {
        assertEquals(s1.keyDirection(right), 1);
        assertEquals(s1.keyDirection(left), -1);
        assertEquals(s1.keyDirection(up), 2);
        assertEquals(s1.keyDirection(down), -2);
        assertEquals(s1.keyDirection(badInput), 1);
        assertEquals(s2.keyDirection(right), 1);
        assertEquals(s2.keyDirection(left), -1);
        assertEquals(s2.keyDirection(up), 2);
        assertEquals(s2.keyDirection(down), -2);
        assertEquals(s2.keyDirection(badInput), -2);
    }

    @Test
    public void ateFoodTest() {
        assertTrue(s1.ateFood(f1));
        assertFalse(s1.ateFood(f2));
        assertFalse(s2.ateFood(f2));
        assertFalse(s2.ateFood(f3));
    }

    @Test
    public void getDirectionTest() {
        assertEquals(s1.getDirection(), 1);
        assertEquals(s2.getDirection(), -2);
    }

    @Test
    public void setSnakeXTest() {
        assertEquals(s1.getSnakeX(), 50);
        assertEquals(s2.getSnakeX(), 15);
        s1.setSnakeX(30);
        s2.setSnakeX(2);
        assertEquals(s1.getSnakeX(), 30);
        assertEquals(s2.getSnakeX(), 2);
    }

    @Test
    public void getSnakeXTest() {
        assertEquals(s1.getSnakeX(), 50);
        assertEquals(s2.getSnakeX(), 15);
    }

    @Test
    public void setSnakeYTest() {
        assertEquals(s1.getSnakeY(), 50);
        assertEquals(s2.getSnakeY(), 70);
        s1.setSnakeY(30);
        s2.setSnakeY(2);
        assertEquals(s1.getSnakeY(), 30);
        assertEquals(s2.getSnakeY(), 2);
    }

    @Test
    public void getSnakeYTest() {
        assertEquals(s1.getSnakeY(), 50);
        assertEquals(s2.getSnakeY(), 70);
    }

    @Test
    public void getDxTest() {
        assertEquals(s1.getDx(), 1);
        assertEquals(s2.getDx(), 0);
    }

    @Test
    public void getDyTest() {
        assertEquals(s1.getDy(), 0);
        assertEquals(s2.getDy(), -1);
    }

    @Test
    public void setSnakeLengthTest() {
        assertEquals(s1.getSnakeLength(),1);
        assertEquals(s2.getSnakeLength(),10);
        s1.setSnakeLength(3);
        s2.setSnakeLength(8);
        assertEquals(s1.getSnakeLength(),3);
        assertEquals(s2.getSnakeLength(),8);
    }

    @Test
    public void getSnakeLengthTest() {
        assertEquals(s1.getSnakeLength(),1);
        assertEquals(s2.getSnakeLength(),10);
        s1.setSnakeLength(3);
        s2.setSnakeLength(8);
        assertEquals(s1.getSnakeLength(),3);
        assertEquals(s2.getSnakeLength(),8);
    }

    @Test
    public void setColorTest() {
        assertEquals(s1.getColor(), "green");
        assertEquals(s2.getColor(), "blue");
        s1.setColor("red");
        s2.setColor("pink");
        assertEquals(s1.getColor(), "red");
        assertEquals(s2.getColor(), "pink");
    }

    @Test
    public void getColorTest() {
        assertEquals(s1.getColor(), "green");
        assertEquals(s2.getColor(), "blue");
        s1.setColor("red");
        s2.setColor("pink");
        assertEquals(s1.getColor(), "red");
        assertEquals(s2.getColor(), "pink");
    }
}