package model;

import persistence.Writable;

// Represents a restaurant that has a name, rating (out of 10), cuisine type, and notes

import org.json.JSONObject;

public class Restaurant implements Writable {

    private String name;
    private int rating;
    private String cuisine;
    private String notes;

    // EFFECTS: produces new Restaurant
    public Restaurant() {
    }

    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES: rating should be between 0 and 10 inclusive
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return this.name;
    }

    public Integer getRating() {
        return this.rating;
    }

    public String getCuisine() {
        return this.cuisine;
    }

    public String getNotes() {
        return this.notes;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("rating", rating);
        json.put("cuisine", cuisine);
        json.put("notes", notes);
        return json;
    }
}
