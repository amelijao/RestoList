package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// RestoList is a list of Restaurants

public class RestoList implements Writable {

    private ArrayList<Restaurant> restaurants; // list of all the Restaurants

    public RestoList() {
        this.restaurants = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds new restaurant to the end of the list
    public void addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
    }

    // EFFECTS: returns all restaurants in list in the order they were added
    public ArrayList<Restaurant> getRestaurants() {
        return this.restaurants;
    }

    // EFFECTS: returns only restaurants of given cuisine type
    public ArrayList<Restaurant> filterCuisine(String cuisine) {
        ArrayList<Restaurant> filtered = new ArrayList<>();
        for (Restaurant r : this.restaurants) {
            if (r.getCuisine().equalsIgnoreCase(cuisine)) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    // EFFECTS: returns only restaurants of given rating
    public ArrayList<Restaurant> filterRating(int rating) {
        ArrayList<Restaurant> filtered = new ArrayList<>();
        for (Restaurant r : this.restaurants) {
            if (r.getRating() == rating) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("restaurants", restaurantsToJson());
        return json;
    }

    // EFFECTS: returns things in this Restolist as a JSON array
    private JSONArray restaurantsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Restaurant r : restaurants) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

}




