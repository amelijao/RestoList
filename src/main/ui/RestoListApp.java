package ui;

import model.RestoList;
import model.Restaurant;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

// RestoList console application, inspired by TellerApp and JsonSerializationDemo

public class RestoListApp {

    private Scanner input;
    private RestoList restoList;
    private static final String JSON_STORE = "./data/restolist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the RestoList app
    public RestoListApp() {
        restoList = new RestoList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runRestoList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runRestoList() {
        boolean stillRunning = true;
        String command;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        while (stillRunning) {
            displayMenu();
            command = input.next().toLowerCase();
            if (command.equals("q")) {
                stillRunning = false;
            } else {
                processCommand(command);
            }
        }
        System.out.print("\nSee ya later!\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command from menu
    private void processCommand(String command) {
        if (command.equals("a")) {
            addResto();
        } else if (command.equals("v")) {
            viewList();
        } else if (command.equals("f")) {
            filterList();
        } else if (command.equals("s")) {
            saveRestoList();
        } else if (command.equals("l")) {
            loadRestoList();
        } else {
            System.out.println("\nSelection not valid... Try again!");
        }
    }

    // REQUIRES: rating input must be integer
    // MODIFIES: restoList
    // EFFECTS: allows user to add a new restaurant to restoList (with name, rating, cuisine type, notes)
    private void addResto() {
        Restaurant resto = new Restaurant();
        System.out.print("\nRestaurant name? ");
        String name = input.next();
        resto.setName(name);

        System.out.print("\nWhat kind of cuisine? ");
        String cuisine = input.next();
        resto.setCuisine(cuisine);

        System.out.print("\nWhat would you rate it out of 10? Whole numbers only please! ");
        int rating = input.nextInt();
        resto.setRating(rating);

        System.out.print("\nAdd any notes here! ");
        String notes = input.next();
        resto.setNotes(notes);

        restoList.addRestaurant(resto);
        System.out.print("\nRestaurant " + resto.getName() + " added!\n");
    }

    // EFFECTS: displays all restaurants (names, ratings, cuisine type), gives option to view notes
    private void viewList() {
        if (!restoList.getRestaurants().isEmpty()) {
            System.out.print("\nHere are all your restaurants:\n");
            for (Restaurant r : restoList.getRestaurants()) {
                printRestoInfo(r);
            }
            System.out.print("\nTo view extra notes, enter n\n");
            String answer = input.next();
            if (answer.equals("n")) {
                viewNotes();
            }
        } else {
            System.out.print("\nNo restaurants yet! Try adding one first.\n");
        }
    }

    // EFFECTS: displays notes for selected restaurant
    private void viewNotes() {
        System.out.print("\nWhich restaurant's notes would you like to view? Enter the number:\n");
        int i = 1;
        for (Restaurant r : restoList.getRestaurants()) {
            System.out.print(i + "\t" + r.getName() + "\n");
            i++;
        }
        int answer = input.nextInt();
        Restaurant answerResto = restoList.getRestaurants().get(answer - 1);
        System.out.print(answerResto.getName() + " notes: " + answerResto.getNotes() + "\n");
    }

    // EFFECTS: gives user choice whether to filter by rating or cuisine type
    private void filterList() {
        if (!restoList.getRestaurants().isEmpty()) {
            System.out.print("\nEnter r to filter by rating, or c to filter by cuisine type: ");
            String answer = input.next();
            if (answer.equals("c")) {
                filterByCuisine();
            } else if (answer.equals("r")) {
                filterByRating();
            } else {
                System.out.print("\nInvalid input... back to menu!");
            }
        } else {
            System.out.print("\nNo restaurants yet! Try adding one first.\n");
        }
    }

    // EFFECTS: displays restaurants of selected cuisine type
    private void filterByCuisine() {
        System.out.print("\nEnter desired cuisine type: ");
        String answer = input.next();
        if (restoList.filterCuisine(answer).isEmpty()) {
            System.out.print("Nothing with this cuisine type yet!\n");
        } else {
            for (String s : restoList.filterCuisine(answer)) {
                System.out.print(s);
            }
        }
    }

    // EFFECTS: displays restaurants of selected rating
    private void filterByRating() {
        System.out.print("\nEnter desired rating: ");
        int answer = input.nextInt();
        if (restoList.filterRating(answer).isEmpty()) {
            System.out.print("Nothing with this rating yet!\n");
        } else {
            for (String s : restoList.filterRating(answer)) {
                System.out.print(s);
            }
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\ta -> Add restaurant");
        System.out.println("\tv -> View all restaurants");
        System.out.println("\tf -> Filter restaurants");
        System.out.println("\ts -> Save restaurants to file");
        System.out.println("\tl -> Load restaurants from file");
        System.out.println("\tq -> Quit");
    }

    //EFFECTS: prints a single restaurant's info (name, cuisine, rating)
    private void printRestoInfo(Restaurant r) {
        System.out.print(r.getName() + "\t Cuisine: " + r.getCuisine() + "\t Rating: " + r.getRating() + "/10\n");
    }

    // EFFECTS: saves the restolist to file
    private void saveRestoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(restoList);
            jsonWriter.close();
            System.out.println("Saved restaurants to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads restolist from file
    private void loadRestoList() {
        try {
            restoList = jsonReader.read();
            System.out.println("Loaded restaurants from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
