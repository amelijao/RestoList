package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import model.RestoList;
import model.Restaurant;

import java.util.ArrayList;

import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;

//Graphical User Interface for RestoList Application
public class RestoListUI {

    private JFrame frame;
    private JPanel panel;
    private JPanel addingPanel;
    private JTextField addNameText;
    private JTextField addCuisineText;
    private JTextField addRatingText;
    private JTextField addNotesText;
    private JPanel listPanel;
    private JPanel filteringPanel;
    private JTextField addFilterCText;
    private JTextField addFilterRText;
    private JList<String> currentList;
    private JList<String> filterDisplayList;

    private RestoList restoList;

    private static final String JSON_STORE = "./data/restolist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: sets up frame and panels
    public RestoListUI() {

        restoList = new RestoList();
        currentList = new JList<>();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame();
        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(1, 4));
        addButtons();
        frame.add(panel);
        makeAddingPanel();
        makeFilteringPanel();
        makeDisplayPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3));
        frame.setTitle("RestoList GUI");
        frame.pack();
        frame.setVisible(true);
    }

    //EFFECTS: returns a JList of the current restaurant names
    private JList<String> restoListToJList() {

        ArrayList<String> rlist = new ArrayList<>();
        for (Restaurant r : restoList.getRestaurants()) {
            rlist.add(r.getName() + ", " + r.getCuisine() + ", " + r.getRating() + "/10, " + "notes: " + r.getNotes());
        }
        JList<String> displayList = new JList<>(rlist.toArray(new String[0]));
        return displayList;
    }

    //EFFECTS: Adds a panel with load, save, add, and view buttons
    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.add(new JButton(new LoadAction()));
        buttonPanel.add(new JButton(new SaveAction()));
        buttonPanel.add(new JButton(new AddAction()));
        buttonPanel.add(new JButton(new ViewAction()));
        panel.add(buttonPanel, BorderLayout.WEST);
    }

    //EFFECTS: sets up panel for adding restaurants
    private void makeAddingPanel() {
        addingPanel = new JPanel();
        JLabel addingPanelLabel = new JLabel("Enter new restaurant info:");
        addingPanel.setLayout(new BoxLayout(addingPanel, BoxLayout.PAGE_AXIS));
        addingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        addNameText = new JTextField();
        addCuisineText = new JTextField();
        addRatingText = new JTextField();
        addNotesText = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JLabel cuisineLabel = new JLabel("Cuisine:");
        JLabel ratingLabel = new JLabel("Rating:");
        JLabel notesLabel = new JLabel("Notes:");
        addingPanel.add(addingPanelLabel);
        addingPanel.add(nameLabel);
        addingPanel.add(addNameText);
        addingPanel.add(cuisineLabel);
        addingPanel.add(addCuisineText);
        addingPanel.add(ratingLabel);
        addingPanel.add(addRatingText);
        addingPanel.add(notesLabel);
        addingPanel.add(addNotesText);
        addingPanel.add(new JButton(new AddRestoAction()));
        frame.pack();
    }

    //EFFECTS: sets up panel for filtering restaurants
    private void makeFilteringPanel() {
        filteringPanel = new JPanel();
        JLabel filteringPanelLabel = new JLabel("Filter Restaurants by:");
        filteringPanel.setLayout(new GridLayout(8, 0));
        filteringPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        addFilterCText = new JTextField();
        addFilterRText = new JTextField();
        JLabel filterCuisineLabel = new JLabel("Desired Cuisine:");
        JLabel filterRatingLabel = new JLabel("Desired Rating /10:");
        filteringPanel.add(filteringPanelLabel);
        filteringPanel.add(filterCuisineLabel);
        filteringPanel.add(addFilterCText);
        filteringPanel.add(new JButton(new FilterCuisineAction()));
        filteringPanel.add(filterRatingLabel);
        filteringPanel.add(addFilterRText);
        filteringPanel.add(new JButton(new FilterRatingAction()));
        filterDisplayList = new JList<>();
        filteringPanel.add(filterDisplayList);
        panel.add(filteringPanel);
        frame.pack();
    }

    //EFFECTS: sets up display panel that shows current list and logo
    private void makeDisplayPanel() {
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(3, 0));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel listLabel = new JLabel("Click 'View Restaurants' for an updated list:");
        listPanel.add(listLabel);
        listPanel.add(currentList);
        JLabel logo = new JLabel();
        String img = "data/RestoListGrey.png";
        Image icon = new ImageIcon(img).getImage().getScaledInstance(300, 80, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(icon));
        listPanel.add(logo);
        panel.add(listPanel);
        frame.pack();
    }

    //LoadAction deals with the button for loading data from file
    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load Restaurants");
        }

        //EFFECTS: loads data from file and prints to console
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                restoList = jsonReader.read();
                System.out.println("Loaded restaurants from " + JSON_STORE);
            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    //SaveAction deals with the button for saving data to file
    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save Restaurants");
        }

        //EFFECTS: saves data to file and prints to console
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(restoList);
                jsonWriter.close();
                System.out.println("Saved restaurants to " + JSON_STORE);
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    //AddAction causes the panel for adding a new restaurant to pop up
    private class AddAction extends AbstractAction {

        AddAction() {
            super("Add New Restaurant");
        }

        //EFFECTS: adds adding panel to frame
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.add(addingPanel);
            frame.pack();
        }
    }

    //ViewAction deals with the button for viewing all restaurants
    private class ViewAction extends AbstractAction {

        ViewAction() {
            super("View Restaurants");
        }

        //EFFECTS: adds current list of restaurants to display panel
        @Override
        public void actionPerformed(ActionEvent e) {
            listPanel.remove(currentList);
            currentList = restoListToJList();
            listPanel.add(currentList);
            frame.pack();
        }
    }

    //FilterCuisineAction deals with the button for filtering restaurants
    private class FilterCuisineAction extends AbstractAction {

        FilterCuisineAction() {
            super("Filter by cuisine");
        }

        //EFFECTS: displays filtered list of restaurants with given cuisine
        @Override
        public void actionPerformed(ActionEvent e) {
            filteringPanel.remove(filterDisplayList);
            frame.pack();
            String cuisine = addFilterCText.getText();
            ArrayList<String> filtered = new ArrayList<>();
            for (Restaurant r : restoList.getRestaurants()) {
                if (r.getCuisine().equalsIgnoreCase(cuisine)) {
                    filtered.add(r.getName() + ", " + r.getCuisine());
                }
            }
            JList<String> newFilterDisplayList = new JList<>(filtered.toArray(new String[0]));
            filterDisplayList = newFilterDisplayList;
            filteringPanel.add(filterDisplayList);
            frame.pack();
        }
    }

    //FilterRatingAction deals with the button for filtering restaurants
    private class FilterRatingAction extends AbstractAction {

        FilterRatingAction() {
            super("Filter by rating");
        }

        //REQUIRES: user inputs an integer between 0 and 10
        //EFFECTS: displays filtered list of restaurants with given rating
        @Override
        public void actionPerformed(ActionEvent e) {
            filteringPanel.remove(filterDisplayList);
            frame.pack();
            int rating = Integer.parseInt(addFilterRText.getText());
            ArrayList<String> filtered = new ArrayList<>();
            for (Restaurant r : restoList.getRestaurants()) {
                if (r.getRating().equals(rating)) {
                    filtered.add(r.getName() + ", " + r.getRating() + "/10");
                }
            }
            JList<String> newFilterDisplayList = new JList<>(filtered.toArray(new String[0]));
            filterDisplayList = newFilterDisplayList;
            filteringPanel.add(filterDisplayList);
            frame.pack();
        }
    }

    //AddRestoAction deals with the button for adding a new restaurant
    private class AddRestoAction extends AbstractAction {

        AddRestoAction() {
            super("Add");
        }

        //EFFECTS: adds new restaurant with given name, cuisine, rating, and notes
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = addNameText.getText();
            String cuisine = addCuisineText.getText();
            int rating = Integer.parseInt(addRatingText.getText());
            String notes = addNotesText.getText();

            Restaurant newResto = new Restaurant();
            newResto.setName(name);
            newResto.setCuisine(cuisine);
            newResto.setRating(rating);
            newResto.setNotes(notes);

            restoList.addRestaurant(newResto);
        }
    }
}
