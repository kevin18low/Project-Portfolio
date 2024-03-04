package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBaseTest {
    private PlayerBase pb;
    private Player p1;
    private Player p2;

    @BeforeEach
    public void setup() {
        pb = new PlayerBase();
        p1 = new Player("p1");
        p2 = new Player("p2");
    }

    @Test
    public void addPlayerTest() {
        pb.addPlayer(p1);
        assertEquals(pb.getPlayers().size(), 1);
        pb.addPlayer(p2);
        assertEquals(pb.getPlayers().size(), 2);
    }
    @Test
    public void newPlayerProfileTest() {
        pb.addPlayer(p1);
        assertEquals(pb.getPlayers().size(), 1);
        pb.newPlayerProfile("new player");
        pb.addPlayer(p2);
        assertEquals(pb.getPlayers().size(), 3);
    }

    @Test
    public void getPlayerProfileTest() {
        pb.addPlayer(p1);
        pb.addPlayer(p2);
        assertEquals(pb.getPlayers().size(), 2);
        assertEquals(pb.getPlayerProfile("p1"), p1);
        assertEquals(pb.getPlayerProfile("p2"), p2);
        assertEquals(pb.getPlayers().size(), 2);
        assertNull(pb.getPlayerProfile("p3"));
        assertEquals(pb.getPlayers().size(), 2);
    }

}
