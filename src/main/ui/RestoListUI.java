package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import model.RestoList;
import model.Restaurant;

import java.util.ArrayList;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;


public class RestoListUI {

    private JFrame frame;
    private JPanel panel;
    private JScrollPane listPanel;
    private Label listLabel;

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
        listPanel = new JScrollPane();
        listLabel = new Label("Here are your current Restaurants:");
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(1, 2));
        addButtons();

        frame.add(panel, BorderLayout.WEST);
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
            frame.add(listPanel, BorderLayout.CENTER);
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

    //AddAction deals with the button for adding a new restaurant
    private class AddAction extends AbstractAction {

        AddAction() {
            super("Add New Restaurant");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

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
}
