package persistence;

import model.PlayerBase;
import model.Player;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Credit: JsonSerializationDemo
public class JsonReaderTest {
    
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PlayerBase pb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPlayerBase() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlayerBase.json");
        try {
            PlayerBase pb = reader.read();
            assertEquals(0, pb.getPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlayerBase() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlayerBase.json");
        try {
            PlayerBase pb = reader.read();
            List<Player> players = pb.getPlayers();
            assertEquals(4, players.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
