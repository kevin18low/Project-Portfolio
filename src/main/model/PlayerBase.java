package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents all players that have played the game

public class PlayerBase implements Writable {
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

    //EFFECTS: returns player if player with given name already exists
    //         returns new player if name is not in list of players
    public Player getPlayerProfile(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        // should not be reached
        return null;
    }

    // EFFECTS: put in JSON
    // Credit: JSonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Players", playersToJson());
        return json;
    }

    // EFFECTS: returns items as a JSON array
    // Credit: JSonSerializationDemo
    public JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player p : players) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

    //*************** getters and setters **************

    public List<Player> getPlayers() {
        return players;
    }
}
