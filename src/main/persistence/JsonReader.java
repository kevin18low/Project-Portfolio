package persistence;

import model.Player;
import model.PlayerBase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Credit: JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
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
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses PlayerBase from JSON object and returns it
    private PlayerBase parsePlayerBase(JSONObject jsonObject) {
        //String name = jsonObject.getString("name");
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
        pb.addPlayer(player);
    }
}