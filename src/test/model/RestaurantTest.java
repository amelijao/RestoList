package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant testResto;

    @BeforeEach
    void runBefore() {
        testResto = new Restaurant();
    }

    @Test
    void testRestaurantConstructor() {
        assertNull(testResto.getName());
        assertEquals(0, testResto.getRating());
        assertNull(testResto.getCuisine());
        assertNull(testResto.getNotes());
    }

    @Test
    void testGetSetName() {
        testResto.setName("test");
        assertEquals("test", testResto.getName());
        testResto.setName("retest");
        assertEquals("retest", testResto.getName());
    }

    @Test
    void testGetSetCuisine() {
        testResto.setCuisine("food");
        assertEquals("food", testResto.getCuisine());
        testResto.setCuisine("food again");
        assertEquals("food again", testResto.getCuisine());
    }

    @Test
    void testGetSetRating() {
        testResto.setRating(0);
        assertEquals(0, testResto.getRating());
        testResto.setRating(10);
        assertEquals(10, testResto.getRating());
        testResto.setRating(5);
        assertEquals(5, testResto.getRating());
    }

    @Test
    void testGetSetNotes() {
        testResto.setNotes("note test 1");
        assertEquals("note test 1", testResto.getNotes());
        testResto.setNotes("again another note test");
        assertEquals("again another note test", testResto.getNotes());
    }

}