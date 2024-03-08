package persistence;

import model.Restaurant;
import model.RestoList;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RestoList rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRestoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRestoList.json");
        try {
            RestoList rl = reader.read();
            assertEquals(0, rl.getRestaurants().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFullRestoList() {
        JsonReader reader = new JsonReader("./data/testReaderFullRestoList.json");
        try {
            RestoList rl = reader.read();
            List<Restaurant> restaurants = rl.getRestaurants();
            assertEquals(2, restaurants.size());
            checkRestaurant("test1", 10, "cuisine", "blah blah blah", restaurants.get(0));
            checkRestaurant("test2", 1, "cuisine two", "blah blah again", restaurants.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
