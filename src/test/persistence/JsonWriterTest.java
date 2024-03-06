package persistence;

import model.Player;
import model.PlayerBase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Credit: JsonSerializationDemo
public class JsonWriterTest {
    private PlayerBase pb;

    @BeforeEach
    public void setup() {
        pb = new PlayerBase();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPlayerBase() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlayerBase.json");
            writer.open();
            writer.write(pb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlayerBase.json");
            pb = reader.read();
            assertEquals(0, pb.getPlayers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlayerBase() {
        try {
            pb.addPlayer(new Player("kevin"));
            pb.addPlayer(new Player("bob"));
            pb.addPlayer(new Player("john"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlayerBase.json");
            writer.open();
            writer.write(pb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlayerBase.json");
            pb = reader.read();
            List<Player> players = pb.getPlayers();
            assertEquals(3, players.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
