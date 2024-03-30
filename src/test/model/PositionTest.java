package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    private Position p1;

    @BeforeEach
    public void setup() {
        p1 = new Position(10, 25);
    }

    @Test
    public void getPosXTest() {
        assertEquals(p1.getPosX(), 10);
    }

    @Test
    public void getPosYTest() {
        assertEquals(p1.getPosY(), 25);
    }

    @Test
    public void setPosXTest() {
        assertEquals(p1.getPosX(), 10);
        p1.setPosX(15);
        assertEquals(p1.getPosX(), 15);
    }

    @Test
    public void setPosYTest() {
        assertEquals(p1.getPosY(), 25);
        p1.setPosY(30);
        assertEquals(p1.getPosY(), 30);
    }
}
