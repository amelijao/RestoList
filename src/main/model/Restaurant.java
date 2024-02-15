package model;


// Represents a restaurant that has a name, rating (out of 10), cuisine type, and notes

public class Restaurant {

    private String name;
    private int rating;
    private String cuisine;
    private String notes;

    // EFFECTS: produces new Restaurant with a name
    public Restaurant(String name) {
        this.name = name;
    }


    // REQUIRES: rating should be between 0 and 10
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

}
