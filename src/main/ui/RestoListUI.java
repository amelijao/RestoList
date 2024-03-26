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
    private JPanel listPanel;
    private JLabel listLabel;
    private JPanel filteringPanel;
    private JLabel filteringPanelLabel;
    private JTextField addFilterCText;
    private JTextField addFilterRText;
    private JList<String> currentList;
    private JList<String> filterDisplayList;

    private RestoList restoList;

    private static final String JSON_STORE = "./data/restolist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    public RestoListUI() {

        restoList = new RestoList();
        currentList = new JList<>();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame();
        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(1, 3));
        addButtons();

        frame.add(panel);
        makeAddingPanel();
        makeFilteringPanel();
        makeDisplayPanel();
        frame.add(listPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3));
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

    //Adds a panel with load, save, add, view, and filter buttons
    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));
        buttonPanel.add(new JButton(new LoadAction()));
        buttonPanel.add(new JButton(new SaveAction()));
        buttonPanel.add(new JButton(new AddAction()));
        buttonPanel.add(new JButton(new ViewAction()));
        //buttonPanel.add(new JButton(new FilterAction()));
        panel.add(buttonPanel, BorderLayout.WEST);
    }

    //sets up panel for adding restaurants
    private void makeAddingPanel() {
        addingPanel = new JPanel();
        addingPanelLabel = new JLabel("New restaurant:");
        addingPanel.setLayout(new BoxLayout(addingPanel, BoxLayout.PAGE_AXIS));
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
        frame.pack();
    }

    //sets up panel for filtering restaurants
    private void makeFilteringPanel() {
        filteringPanel = new JPanel();
        filteringPanelLabel = new JLabel("filter restaurants by cuisine or rating:");
        filteringPanel.setLayout((new BoxLayout(filteringPanel, BoxLayout.PAGE_AXIS)));
        filteringPanel.add(filteringPanelLabel);
        addFilterCText = new JTextField("enter cuisine");
        addFilterRText = new JTextField("enter rating");
        filteringPanel.add(addFilterCText);
        filteringPanel.add(new JButton(new FilterCuisineAction()));
        filteringPanel.add(addFilterRText);
        filteringPanel.add(new JButton(new FilterRatingAction()));
        filterDisplayList = new JList<>();
        filteringPanel.add(filterDisplayList);
        frame.add(filteringPanel);
        frame.pack();
    }

    //sets up display panel
    private void makeDisplayPanel() {
        listPanel = new JPanel();
        listPanel.add(currentList);
        listLabel = new JLabel("Here are your current restaurants:");
        listPanel.add(listLabel);
        frame.pack();
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
            frame.pack();
        }
    }

    //ViewAction deals with the button for viewing all restaurants
    private class ViewAction extends AbstractAction {

        ViewAction() {
            super("View Restaurants");
        }

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

        @Override
        public void actionPerformed(ActionEvent e) {
            filteringPanel.remove(filterDisplayList);
            String cuisine = addFilterCText.getText();
            ArrayList<String> filtered = new ArrayList<>();
            for (Restaurant r : restoList.getRestaurants()) {
                if (r.getCuisine().equalsIgnoreCase(cuisine)) {
                    filtered.add(r.getName() + ", " + r.getCuisine());
                }
            }
            JList<String> filterDisplayList = new JList<>(filtered.toArray(new String[0]));
            filteringPanel.add(filterDisplayList);
            frame.pack();
        }
    }

    //FilterRatingAction deals with the button for filtering restaurants
    private class FilterRatingAction extends AbstractAction {

        FilterRatingAction() {
            super("Filter by rating");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            filteringPanel.remove(filterDisplayList);
            int rating = Integer.parseInt(addFilterRText.getText());
            ArrayList<String> filtered = new ArrayList<>();
            for (Restaurant r : restoList.getRestaurants()) {
                if (r.getRating().equals(rating)) {
                    filtered.add(r.getName() + ", " + r.getRating() + "/10");
                }
            }
            JList<String> filterDisplayList = new JList<>(filtered.toArray(new String[0]));
            filteringPanel.add(filterDisplayList);
            frame.pack();
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
