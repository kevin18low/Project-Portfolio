package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    private Snake s1;
    private Snake s2;
    private KeyEvent right;
    private KeyEvent left;
    private KeyEvent up;
    private KeyEvent down;
    private KeyEvent badInput;

    // Credit: StackOverflow
    @BeforeEach
    public void setup() {
        s1 = new Snake(new Direction(1), 50, 50, "green");
        s2 = new Snake(new Direction(-2), 15, 70, "blue");
        right = new KeyEvent(new JLabel("test"), KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        left = new KeyEvent(new JLabel("test"), KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_LEFT);
        up = new KeyEvent(new JLabel("test"), KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_UP);
        down = new KeyEvent(new JLabel("test"), KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        badInput = new KeyEvent(new JLabel("test"), KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_TAB);
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
        assertEquals(s1.getDirection(), -2);
        s1.turn(left);
        assertEquals(s1.getDirection(), -1);

        assertEquals(s2.getDirection(), -2);
        s2.turn(right);
        assertEquals(s2.getDirection(), 1);
        s2.turn(down);
        assertEquals(s2.getDirection(), 2);
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
        assertEquals(s1.keyDirection(up), -2);
        assertEquals(s1.keyDirection(down), 2);
        assertEquals(s1.keyDirection(badInput), 1);
        assertEquals(s2.keyDirection(right), 1);
        assertEquals(s2.keyDirection(left), -1);
        assertEquals(s2.keyDirection(up), -2);
        assertEquals(s2.keyDirection(down), 2);
        assertEquals(s2.keyDirection(badInput), -2);
    }
    @Test
    public void getDirectionTest() {
        assertEquals(s1.getDirection(), 1);
        assertEquals(s2.getDirection(), -2);
    }

    @Test
    public void getSnakeXTest() {
        assertEquals(s1.getSnakeX(), 50);
        assertEquals(s2.getSnakeX(), 15);
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
    public void getSnakeLengthTest() {
        assertEquals(s1.getSnakeLength(),0);
        s1.getBody().add(new Position(1,1));
        assertEquals(s1.getSnakeLength(),1);
        s1.getBody().add(new Position(2,1));
        assertEquals(s1.getSnakeLength(),2);
    }

    @Test
    public void getColorTest() {
        assertEquals(s1.getColor(), "green");
        assertEquals(s2.getColor(), "blue");
    }

    @Test
    public void getColorByNameTest() {
        try {
            assertEquals(s1.getColorByName(), Color.GREEN);
            assertEquals(s2.getColorByName(), Color.BLUE);
        } catch (Exception e) {
            // expected
        }
    }
}