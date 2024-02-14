package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    private Snake s1;
    private Snake s2;
    private Food f1;
    private Food f2;
    private Food f3;

    @BeforeEach
    public void setup() {
        s1 = new Snake(1, 50, 50, Color.green);
        s2 = new Snake(-2, 15, 70, Color.blue);
        f1 = new Food(50, 50);
        f2 = new Food(15, 45);
        f3 = new Food(95, 70);
    }

    @Test
    public void turnTestSameDirection() {
        assertEquals(s1.getDirection(), 1);
        s1.turn(KeyEvent.VK_RIGHT);
        assertEquals(s1.getDirection(), 1);
        assertEquals(s2.getDirection(), -2);
        s2.turn(KeyEvent.VK_DOWN);
        assertEquals(s2.getDirection(), -2);
    }

    @Test
    public void turnTestOppositeDirection() {
        assertEquals(s1.getDirection(), 1);
        s1.turn(KeyEvent.VK_LEFT);
        assertEquals(s1.getDirection(), 1);
        assertEquals(s2.getDirection(), -2);
        s2.turn(KeyEvent.VK_UP);
        assertEquals(s2.getDirection(), -2);
    }

    @Test
    public void turnTestValidTurn() {
        assertEquals(s1.getDirection(), 1);
        s1.turn(KeyEvent.VK_UP);
        assertEquals(s1.getDirection(), 2);
        s1.turn(KeyEvent.VK_LEFT);
        assertEquals(s1.getDirection(), -1);
        assertEquals(s2.getDirection(), -2);
        s2.turn(KeyEvent.VK_RIGHT);
        assertEquals(s2.getDirection(), 1);
        s2.turn(KeyEvent.VK_DOWN);
        assertEquals(s2.getDirection(), -2);
    }

    @Test
    public void validTurnTestInvalidTurns() {
        assertFalse(s1.validTurn(KeyEvent.VK_RIGHT));
        assertFalse(s1.validTurn(KeyEvent.VK_LEFT));
        s1.turn(KeyEvent.VK_DOWN);
        assertFalse(s1.validTurn(KeyEvent.VK_UP));

        assertFalse(s2.validTurn(KeyEvent.VK_UP));
        assertFalse(s2.validTurn(KeyEvent.VK_DOWN));
        s2.turn(KeyEvent.VK_LEFT);
        assertFalse(s2.validTurn(KeyEvent.VK_RIGHT));
    }

    @Test
    public void validTurnTestValidTurns() {
        assertTrue(s1.validTurn(KeyEvent.VK_UP));
        assertTrue(s1.validTurn(KeyEvent.VK_DOWN));
        s1.turn(KeyEvent.VK_DOWN);
        assertTrue(s1.validTurn(KeyEvent.VK_RIGHT));

        assertTrue(s2.validTurn(KeyEvent.VK_RIGHT));
        assertTrue(s2.validTurn(KeyEvent.VK_LEFT));
        s2.turn(KeyEvent.VK_RIGHT);
        assertTrue(s2.validTurn(KeyEvent.VK_DOWN));
    }

    @Test
    public void keyDirectionTest() {
        assertEquals(s1.keyDirection(KeyEvent.VK_RIGHT), 1);
        assertEquals(s1.keyDirection(KeyEvent.VK_LEFT), -1);
        assertEquals(s1.keyDirection(KeyEvent.VK_UP), 2);
        assertEquals(s1.keyDirection(KeyEvent.VK_DOWN), -2);
    }

    @Test
    public void ateFoodTest() {
        assertTrue(s1.ateFood(f1));
        assertFalse(s1.ateFood(f2));
        assertFalse(s2.ateFood(f2));
        assertFalse(s2.ateFood(f3));
    }
}
