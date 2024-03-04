package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {
    private Direction d1;

    @BeforeEach
    public void setup() {
        d1 = new Direction(1);
    }

    @Test
    public void findDxTest() {
        assertEquals(1, d1.findDx(d1.getDirection()));
        assertEquals(1, d1.getDx());
        d1.setDirection(-1);
        assertEquals(-1, d1.findDx(d1.getDirection()));
        assertEquals(-1, d1.getDx());
        d1.setDirection(2);
        assertEquals(0, d1.findDx(d1.getDirection()));
        assertEquals(0, d1.getDx());
        d1.setDirection(-2);
        assertEquals(0, d1.findDx(d1.getDirection()));
        assertEquals(0, d1.getDx());
    }

    @Test
    public void findDyTest() {
        assertEquals(0, d1.findDy(d1.getDirection()));
        assertEquals(0, d1.getDy());
        d1.setDirection(-1);
        assertEquals(0, d1.findDy(d1.getDirection()));
        assertEquals(0, d1.getDy());
        d1.setDirection(2);
        assertEquals(1, d1.findDy(d1.getDirection()));
        assertEquals(1, d1.getDy());
        d1.setDirection(-2);
        assertEquals(-1, d1.findDy(d1.getDirection()));
        assertEquals(-1, d1.getDy());
    }

    @Test
    public void setDirectionTest() {
        assertEquals(1, d1.getDirection());
        d1.setDirection(-1);
        assertEquals(-1, d1.getDirection());
        assertEquals(-1, d1.getDx());
        assertEquals(0, d1.getDy());
        d1.setDirection(2);
        assertEquals(2, d1.getDirection());
        assertEquals(0, d1.getDx());
        assertEquals(1, d1.getDy());
        d1.setDirection(-2);
        assertEquals(-2, d1.getDirection());
        assertEquals(0, d1.getDx());
        assertEquals(-1, d1.getDy());
    }
}
