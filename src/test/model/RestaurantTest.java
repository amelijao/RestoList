package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant testResto1;
    private Restaurant testResto2;
    private Restaurant testResto3;
    private Restaurant testResto4;
    private RestoList testList;

    @BeforeEach
    void runBefore() {
        testResto1 = new Restaurant("test1");
        testResto2 = new Restaurant("test2");
        testResto3 = new Restaurant("test3");
        testResto4 = new Restaurant("test4");
        testList = new RestoList();
    }

    @Test
    void testRestaurantConstructor() {
        assertEquals("test1", testResto1.getName());
    }

    @Test
    void testGettersAndSetters() {
        testResto1.setCuisine("Mexican");
        testResto1.setRating(9);
        testResto1.setNotes("good but pricy");
        assertEquals("Mexican", testResto1.getCuisine());
        assertEquals(9, testResto1.getRating());
        assertEquals("good but pricy", testResto1.getNotes());
    }

    @Test
    void testListConstructor() {
        assertTrue(testList.getRestaurants().isEmpty());
    }

    @Test
    void testAddRestaurant() {
        assertTrue(testList.getRestaurants().isEmpty());
        testList.addRestaurant(testResto1);
        assertFalse(testList.getRestaurants().isEmpty());
        assertEquals(1, testList.getRestaurants().size());
        testList.addRestaurant(testResto2);
        assertEquals(2, testList.getRestaurants().size());
        assertEquals(testResto1, testList.getRestaurants().get(0));
        assertEquals(testResto2, testList.getRestaurants().get(1));
    }

    @Test
    void testFilterCuisine() {
        testResto1.setCuisine("Aa");
        testResto2.setCuisine("Bb");
        testResto3.setCuisine("Bb");
        testResto4.setCuisine("Aa");
        testList.addRestaurant(testResto1);
        testList.addRestaurant(testResto2);
        testList.addRestaurant(testResto3);
        testList.addRestaurant(testResto4);
        assertEquals(2, testList.filterCuisine("Aa").size());
        assertEquals(testResto1, testList.filterCuisine("Aa").get(0));
        assertEquals(testResto4, testList.filterCuisine("Aa").get(1));
        assertEquals(2, testList.filterCuisine("Bb").size());
        assertEquals(testResto2, testList.filterCuisine("Bb").get(0));
        assertEquals(testResto3, testList.filterCuisine("Bb").get(1));
    }

    @Test
    void testFilterCuisineCaseDifferences() {
        testResto1.setCuisine("Aa");
        testResto2.setCuisine("aa");
        testResto3.setCuisine("AA");
        testResto4.setCuisine("aA");
        testList.addRestaurant(testResto1);
        testList.addRestaurant(testResto2);
        testList.addRestaurant(testResto3);
        testList.addRestaurant(testResto4);
        assertEquals(4, testList.filterCuisine("aa").size());
        assertEquals(4, testList.filterCuisine("Aa").size());
        assertEquals(4, testList.filterCuisine("AA").size());
        assertEquals(4, testList.filterCuisine("aA").size());
    }

    @Test
    void testFilterRating() {
        testResto1.setRating(1);
        testResto2.setRating(8);
        testResto3.setRating(8);
        testResto4.setRating(1);
        testList.addRestaurant(testResto1);
        testList.addRestaurant(testResto2);
        testList.addRestaurant(testResto3);
        testList.addRestaurant(testResto4);
        assertEquals(2, testList.filterRating(1).size());
        assertEquals(testResto1, testList.filterRating(1).get(0));
        assertEquals(testResto4, testList.filterRating(1).get(1));
        assertEquals(2, testList.filterRating(8).size());
        assertEquals(testResto2, testList.filterRating(8).get(0));
        assertEquals(testResto3, testList.filterRating(8).get(1));
    }




}