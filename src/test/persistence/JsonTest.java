package persistence;

import model.Restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;

// inspired by JsonSerializationDemo
public class JsonTest {
    protected void checkRestaurant(String name, int rating, String cuisine, String notes, Restaurant restaurant) {
        assertEquals(name, restaurant.getName());
        assertEquals(rating, restaurant.getRating());
        assertEquals(cuisine, restaurant.getCuisine());
        assertEquals(notes, restaurant.getNotes());
    }
}
