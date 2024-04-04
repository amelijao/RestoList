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
        EventLog.getInstance().logEvent(new Event("Added " + restaurant.getName() + " to RestoList "));
    }

    // EFFECTS: returns all restaurants in list in the order they were added
    public ArrayList<Restaurant> getRestaurants() {
        EventLog.getInstance().logEvent(new Event("Viewed updated RestoList "));
        return this.restaurants;
    }

    public ArrayList<String> filterCuisine(String cuisine) {
        ArrayList<String> filtered = new ArrayList<>();
        for (Restaurant r : this.restaurants) {
            if (r.getCuisine().equalsIgnoreCase(cuisine)) {
                filtered.add(r.getName() + ", " + r.getCuisine());
            }
        }
        EventLog.getInstance().logEvent(new Event("Restaurants filtered by cuisine: " + cuisine + " "));
        return filtered;
    }

    // EFFECTS: returns list of strings describing restaurants of given rating
    public ArrayList<String> filterRating(int rating) {
        ArrayList<String> filtered = new ArrayList<>();
        for (Restaurant r : this.restaurants) {
            if (r.getRating().equals(rating)) {
                filtered.add(r.getName() + ", " + r.getRating() + "/10");
            }
        }
        EventLog.getInstance().logEvent(new Event("Restaurants filtered by rating: " + rating + " "));
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




