package persistence;

import model.Restaurant;
import model.RestoList;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            RestoList rl = new RestoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRestoList() {
        try {
            RestoList rl = new RestoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRestoList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRestoList.json");
            rl = reader.read();
            assertEquals(0, rl.getRestaurants().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterFullRestoList() {
        try {
            RestoList rl = new RestoList();
            Restaurant testResto1 = new Restaurant();
            testResto1.setName("test1");
            testResto1.setRating(10);
            testResto1.setCuisine("cuisine");
            testResto1.setNotes("blah blah blah");

            Restaurant testResto2 = new Restaurant();
            testResto2.setName("test2");
            testResto2.setRating(1);
            testResto2.setCuisine("cuisine two");
            testResto2.setNotes("blah blah again");

            rl.addRestaurant(testResto1);
            rl.addRestaurant(testResto2);

            JsonWriter writer = new JsonWriter("./data/testWriterFullRestoList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFullRestoList.json");
            rl = reader.read();
            List<Restaurant> restaurants = rl.getRestaurants();
            assertEquals(2, restaurants.size());
            checkRestaurant("test1", 10, "cuisine", "blah blah blah", restaurants.get(0));
            checkRestaurant("test2", 1, "cuisine two", "blah blah again", restaurants.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
