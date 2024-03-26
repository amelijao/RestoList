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


public class RestoListUI {

    private JFrame frame;
    private JPanel panel;
    private JPanel addingPanel;
    private JLabel addingPanelLabel;
    private JTextField addNameText;
    private JTextField addCuisineText;
    private JTextField addRatingText;
    private JTextField addNotesText;
    private JScrollPane listPanel;
    private JLabel listLabel;

    private RestoList restoList;

    private static final String JSON_STORE = "./data/restolist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    public RestoListUI() {

        restoList = new RestoList();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame();
        panel = new JPanel();

        makeAddingPanel();

        listPanel = new JScrollPane();
        listLabel = new JLabel("Here are your current Restaurants:");

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(1, 3));
        addButtons();

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("RestoList GUI");
        frame.pack();
        frame.setVisible(true);

    }

    //makes a JList of the current restaurant names
    private JList<String> restoListToJList() {

        ArrayList<String> stringList = new ArrayList<>();
        for (Restaurant r : restoList.getRestaurants()) {
            stringList.add(r.getName() + ", " + r.getCuisine() + ", " + r.getRating() + "/10\n");
        }
        JList<String> displayList = new JList<>(stringList.toArray(new String[0]));
        return displayList;
    }

    //adds a panel displaying a list of already added restaurants
    private void displayList() {
        if (!restoList.getRestaurants().isEmpty()) {
            listPanel = new JScrollPane(restoListToJList());
            panel.add(listPanel);
        }
    }

    //Adds a panel with load, save, add, view, and filter buttons
    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));
        buttonPanel.add(new JButton(new LoadAction()));
        buttonPanel.add(new JButton(new SaveAction()));
        buttonPanel.add(new JButton(new AddAction()));
        buttonPanel.add(new JButton(new ViewAction()));
        buttonPanel.add(new JButton(new FilterAction()));
        panel.add(buttonPanel, BorderLayout.WEST);
    }

    //sets up panel for adding restaurants
    private void makeAddingPanel() {
        addingPanel = new JPanel();
        addingPanelLabel = new JLabel("Enter restaurant info below and click 'Add'");
        addingPanel.setLayout(new GridLayout(6, 0));
        addNameText = new JTextField("name");
        addCuisineText = new JTextField("cuisine");
        addRatingText = new JTextField("rating");
        addNotesText = new JTextField("notes");
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setLabelFor(addNameText);
        JLabel cuisineLabel = new JLabel("Cuisine:");
        cuisineLabel.setLabelFor(addCuisineText);
        JLabel ratingLabel = new JLabel("Rating:");
        ratingLabel.setLabelFor(addRatingText);
        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setLabelFor(addNotesText);
        addingPanel.add(addingPanelLabel);
        addingPanel.add(addNameText);
        addingPanel.add(addCuisineText);
        addingPanel.add(addRatingText);
        addingPanel.add(addNotesText);
        addingPanel.add(new JButton(new AddRestoAction()));
    }

    //LoadAction deals with the button for loading data from file
    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load Restaurants");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                restoList = jsonReader.read();
                System.out.println("Loaded restaurants from " + JSON_STORE); //prints to console
            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_STORE); //prints to console
            }
        }
    }

    //SaveAction deals with the button for saving data to file
    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save Restaurants");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(restoList);
                jsonWriter.close();
                System.out.println("Saved restaurants to " + JSON_STORE); //prints to console
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE); //prints to console
            }
        }
    }

    //AddAction causes the panel for adding a new restaurant to pop up
    private class AddAction extends AbstractAction {

        AddAction() {
            super("Add New Restaurant");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.add(addingPanel);
        }
    }

    //ViewAction deals with the button for viewing all restaurants
    private class ViewAction extends AbstractAction {

        ViewAction() {
            super("View Restaurants");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            listPanel.add(listLabel); //TODO: fix label for viewing restaurant list
            displayList();
        }
    }

    //FilterAction deals with the button for filtering restaurants
    private class FilterAction extends AbstractAction {

        FilterAction() {
            super("Filter Restaurants");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    //AddRestoAction deals with the button for adding a new restaurant
    private class AddRestoAction extends AbstractAction {

        AddRestoAction() {
            super("Add");
        }

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
