package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
public class GameTest {
    private Game g1;
    private Snake s1;

    @BeforeEach
    public void setup() {
        g1 = new Game("green", 25);
        s1 = g1.getSnake();
    }

    @Test
    public void isPausedTest() {
        assertFalse(g1.isPaused());
        g1.setPaused(true);
        assertTrue(g1.isPaused());
    }
    @Test
    public void setPausedTest() {
        assertFalse(g1.isPaused());
        g1.setPaused(true);
        assertTrue(g1.isPaused());
    }

    @Test
    public void isGameOverTest() {
        assertFalse(g1.isGameOver());
        g1.setGameOver(true);
        assertTrue(g1.isGameOver());
    }
    @Test
    public void setGameOverTest() {
        assertFalse(g1.isGameOver());
        g1.setGameOver(true);
        assertTrue(g1.isGameOver());
    }

    @Test
    public void getSnakeTest() {
        assertEquals(g1.getSnake(), s1);
    }

    @Test
    public void getScoreTest() {
        assertEquals(g1.getScore(), 0);
    }

    @Test
    public void setScoreTest() {
        assertEquals(g1.getScore(), 0);
        s1.getBody().add(new Position(1,1));
        s1.getBody().add(new Position(2,1));
        g1.setScore();
        assertEquals(g1.getScore(), 2);
    }

    @Test
    public void toStringTest() {
        assertEquals(g1.toString(), "Board size: 600, tile size: 25, snake length: 0, direction: 1, " +
                "head position: (3, 3)");

    }

    @Test
    public void collisionTest() {
        assertTrue(g1.collision(new Position(1, 1), new Position(1, 1)));
        assertFalse(g1.collision(new Position(3, 1), new Position(1, 1)));
    }

    @Test
    public void placeFoodTest() {
        assertEquals(g1.getFood().getPosX(), 10);
        assertEquals(g1.getFood().getPosY(), 10);
        g1.placeFood(new Random());
        assertFalse(g1.getFood().getPosY() == 10);
        assertFalse(g1.getFood().getPosX() == 10);
    }

    @Test
    public void moveSnakeTest() {
        Random random = new Random();
        assertEquals(g1.getSnake().getSnakeLength(), 0);
        g1.moveSnake(new Position(3, 3), random);
        assertEquals(g1.getSnake().getSnakeLength(), 1);
        g1.moveSnake(new Position(20, 20), random);
        assertEquals(g1.getSnake().getSnakeLength(), 1);
    }
}
