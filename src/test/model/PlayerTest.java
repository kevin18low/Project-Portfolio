package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player p1;
    private Game g1;

    @BeforeEach
    public void setup() {
        p1 = new Player("p1");
        g1 = new Game("black", 25);
    }

    @Test
    public void bodyToJsonTest() {
        p1.setGame(g1);
        g1.getSnake().getBody().add(new Position(1,1));
        g1.getSnake().getBody().add(new Position(2,1));
        assertEquals(p1.bodyToJson().length(), 2);
        g1.getSnake().getBody().add(new Position(3,1));
        assertEquals(p1.bodyToJson().length(), 3);
    }

    @Test
    public void addScoreTest() {
        ArrayList<Integer> scores = new ArrayList<>();
        p1.addScore(100);
        assertEquals(p1.getScores().size(), 1);
        assertEquals(p1.getScores().get(0), 100);
        p1.addScore(150);
        assertEquals(p1.getScores().size(), 2);
        assertEquals(p1.getScores().get(1), 100);
        p1.addScore(10);
        p1.addScore(20);
        p1.addScore(5);

        scores.add(150);
        scores.add(100);
        scores.add(20);
        scores.add(10);
        scores.add(5);
        assertEquals(p1.getScores(), scores);

        p1.addScore(7);
        scores.remove(scores.size()-1);
        scores.add(7);
        assertEquals(p1.getScores(), scores);
    }

    @Test
    public void scoresToStringTest() {
        p1.addScore(10);
        p1.addScore(20);
        p1.addScore(30);
        String scores = "10, 20, 30, ";
        assertEquals(p1.scoresToString(), scores);
    }

    @Test
    public void setNameTest() {
        assertEquals(p1.getName(), "p1");
        p1.setName("new player");
        assertEquals(p1.getName(), "new player");
    }

    @Test
    public void getGameTest() {
        p1.setGame(g1);
        assertEquals(p1.getGame(), g1);
    }

    @Test
    public void setGameTest() {
        p1.setGame(g1);
        assertEquals(p1.getGame(), g1);
    }
}

