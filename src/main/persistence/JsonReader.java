package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Credit: JsonSerializationDemo

// Represents a reader that reads PlayerBase from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads PlayerBase from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PlayerBase read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayerBase(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses PlayerBase from JSON object and returns it
    private PlayerBase parsePlayerBase(JSONObject jsonObject) {
        PlayerBase pb = new PlayerBase();
        addPlayers(pb, jsonObject);
        return pb;
    }

    // MODIFIES: pb
    // EFFECTS: parses Players from JSON object and adds them to PlayerBase
    private void addPlayers(PlayerBase pb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(pb, nextPlayer);
        }
    }

    // MODIFIES: pb
    // EFFECTS: parses Player from JSON object and adds it to PlayerBase
    private void addPlayer(PlayerBase pb, JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        Player player = new Player(name);
        addGame(jsonObject, player);
        pb.addPlayer(player);
    }

    // MODIFIES: p
    // EFFECTS: adds a game to the player's profile to be saved
    public void addGame(JSONObject jsonObject, Player p) {
        int width = jsonObject.getInt("Width");
        int height = jsonObject.getInt("Height");
        String c = jsonObject.getString("Color");
        int length = jsonObject.getInt("Length");
        int d = jsonObject.getInt("Direction");
        int x = jsonObject.getInt("X");
        int y = jsonObject.getInt("Y");
        int score = jsonObject.getInt("Score");
        Game g = new Game(width, height, new Snake(new Direction(d), x, y, length, c), score);
        p.setGame(g);
    }
}