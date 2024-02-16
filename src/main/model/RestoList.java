package model;

import java.util.ArrayList;

// RestoList is a list of Restaurants

public class RestoList {

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

}




