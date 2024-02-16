package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestoListTest {

    private Restaurant testResto1;
    private Restaurant testResto2;
    private Restaurant testResto3;
    private Restaurant testResto4;
    private RestoList testList;

    @BeforeEach
    void runBefore() {
        testResto1 = new Restaurant();
        testResto2 = new Restaurant();
        testResto3 = new Restaurant();
        testResto4 = new Restaurant();
        testList = new RestoList();
    }

    @Test
    void testListConstructor() {
        assertTrue(testList.getRestaurants().isEmpty());
    }

    @Test
    void testGetRestaurants() {
        assertTrue(testList.getRestaurants().isEmpty());
        testList.addRestaurant(testResto1);
        assertEquals(testResto1, testList.getRestaurants().get(0));
        testList.addRestaurant(testResto2);
        assertEquals(testResto2, testList.getRestaurants().get(1));
        testList.addRestaurant(testResto3);
        assertEquals(testResto3, testList.getRestaurants().get(2));
        testList.addRestaurant(testResto4);
        assertEquals(testResto4, testList.getRestaurants().get(3));
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
