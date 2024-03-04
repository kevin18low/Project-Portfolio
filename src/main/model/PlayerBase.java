package model;

import java.util.ArrayList;
import java.util.List;

// Represents all players that have played the game

public class PlayerBase {
    private List<Player> players;

    // Make a PlayerBase with an empty list of players
    public PlayerBase() {
        players = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds player to list of all players
    public void addPlayer(Player p) {
        players.add(p);
    }

    //MODIFIES: this
    //EFFECTS: adds a new player with given name to the list of existing players
    public void addNewPlayer(String name) {
        players.add(new Player(name));
    }

    //EFFECTS: returns player if player with given name already exists
    //         returns new player if name is not in list of players
    public Player getPlayerProfile(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        addNewPlayer(name);
        return players.get(players.size() - 1);
    }

    //*************** getters and setters **************

    public List<Player> getPlayers() {
        return players;
    }
}
