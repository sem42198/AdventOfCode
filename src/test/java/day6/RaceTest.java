package test.java.day6;

import main.java.day6.Race;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RaceTest {

    @Test
    void getNumSolutionsProduct() {
        long val = Race.getNumSolutionsProduct("Time:      7  15   30", "Distance:  9  40  200");
        assertEquals(288, val);
    }

    @Test
    void getNumSolutionsProductSingleRace() {
        long val = Race.getNumSolutionsProductSingleRace("Time:      7  15   30", "Distance:  9  40  200");
        assertEquals(71503, val);
    }
}