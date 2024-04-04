package persistence;

import model.Event;
import model.EventLog;
import model.Restaurant;
import model.RestoList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// inspired by JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads RestoList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RestoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Restaurants loaded from file "));
        return parseRestoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses RestoList from JSON object and returns it
    private RestoList parseRestoList(JSONObject jsonObject) {
        RestoList rl = new RestoList();
        addRestaurants(rl, jsonObject);
        return rl;
    }

    // MODIFIES: rl
    // EFFECTS: parses restaurants from JSON object and adds them to RestoList
    private void addRestaurants(RestoList rl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
        for (Object json : jsonArray) {
            JSONObject nextRestaurant = (JSONObject) json;
            addRestaurant(rl, nextRestaurant);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses restaurant from JSON object and adds it to RestoList
    private void addRestaurant(RestoList rl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int rating = jsonObject.getInt("rating");
        String cuisine = jsonObject.getString("cuisine");
        String notes = jsonObject.getString("notes");
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setRating(rating);
        restaurant.setCuisine(cuisine);
        restaurant.setNotes(notes);
        rl.addRestaurant(restaurant);
    }
}
