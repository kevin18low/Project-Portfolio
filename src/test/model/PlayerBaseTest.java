package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBaseTest {
    private PlayerBase pb;
    private Player p1;
    private Player p2;

    @BeforeEach
    public void setup() {
        pb = new PlayerBase();
        p1 = new Player("bob");
        p2 = new Player("jeff");
    }

    @Test
    public void addPlayerTest() {
        pb.addPlayer(p1);
        assertEquals(pb.getPlayers().size(), 1);
        pb.addPlayer(p2);
        pb.addPlayer(new Player("new player"));
        assertEquals(pb.getPlayers().size(), 3);
    }

    @Test
    public void getPlayerProfileTest() {
        pb.addPlayer(p1);
        pb.addPlayer(p2);
        assertEquals(pb.getPlayers().size(), 2);
        assertEquals(pb.getPlayerProfile("bob"), p1);
        assertEquals(pb.getPlayerProfile("jeff"), p2);
        assertEquals(pb.getPlayers().size(), 2);
        assertNull(pb.getPlayerProfile("p3"));
        assertEquals(pb.getPlayers().size(), 2);
    }

    @Test
    public void removePlayerTest() {
        ArrayList<Player> players = new ArrayList<>();
        pb.addPlayer(p1);
        pb.addPlayer(p2);
        players.add(p1);
        players.add(p2);
        assertEquals(pb.getPlayers().size(), 2);
        assertEquals(pb.getPlayers(), players);
        pb.removePlayer(p1);
        players.remove(p1);
        assertEquals(pb.getPlayers(), players);
        assertEquals(pb.getPlayers().size(), 1);
    }

    @Test
    public void sortAlphabeticallyTest() {
        ArrayList<Player> players = new ArrayList<>();
        pb.addPlayer(p2);
        pb.addPlayer(p1);
        players.add(p2);
        players.add(p1);
        assertEquals(pb.getPlayers(), players);
        pb.sortAlphabetically(pb.getPlayers());
        players.remove(p2);
        players.add(p2);
        assertEquals(pb.getPlayers(), players);
    }

}
