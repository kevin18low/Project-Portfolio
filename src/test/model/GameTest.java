package model;

import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.input.KeyStroke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class GameTest {
    private Game g1;
    private KeyStroke ks;

    @BeforeEach
    public void setup() {
        g1 = new Game(8, 8);
        ks = new KeyStroke(KeyType.Tab);
    }

    @Test
    public void pauseGameTest() {
        assertFalse(g1.isPaused());
        g1.pauseGame(ks);
        assertTrue(g1.isPaused());
    }

    @Test
    public void getBoardWidthTest() {
        assertEquals(g1.getBoardWidth(), 8);
    }

    @Test
    public void setBoardWidthTest() {
        assertEquals(g1.getBoardWidth(), 8);
        g1.setBoardWidth(5);
        assertEquals(g1.getBoardWidth(), 5);
    }

    @Test
    public void getBoardHeightTest() {
        assertEquals(g1.getBoardHeight(), 8);
    }

    @Test
    public void setBoardHeightTest() {
        assertEquals(g1.getBoardHeight(), 8);
        g1.setBoardHeight(5);
        assertEquals(g1.getBoardHeight(), 5);
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

}
