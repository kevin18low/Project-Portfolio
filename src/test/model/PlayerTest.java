package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player p1;

    @BeforeEach
    public void setup() {
        p1 = new Player("p1");
    }

    @Test
    public void addScoreTest() {
        assertEquals(p1.getScores().size(), 1);
        p1.addScore(100);
        assertEquals(p1.getScores().size(), 2);
        assertEquals(p1.getScores().get(1), 100);
        p1.addScore(150);
        assertEquals(p1.getScores().size(), 3);
        assertEquals(p1.getScores().get(2), 150);
    }
}
