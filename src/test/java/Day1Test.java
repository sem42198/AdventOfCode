package test.java;

import main.java.Day1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    @Test
    void getCalibrationValuesTotal() {
        String[] inputs = {"1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet"};
        assertEquals(142, Day1.getCalibrationValuesTotal(inputs));
    }

    @Test
    void getCalibrationValuesTotalPart2() {
        String[] inputs = {"two1nine", "eightwothree", "abcone2threexyz", "xtwone3four", "4nineeightseven2",
                "zoneight234", "7pqrstsixteen"};
        assertEquals(281, Day1.getCalibrationValuesTotal(inputs));
    }
}