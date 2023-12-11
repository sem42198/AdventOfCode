package main.java.day8;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void getSteps() {
        String nodes = """
                AAA = (BBB, CCC)
                BBB = (DDD, EEE)
                CCC = (ZZZ, GGG)
                DDD = (DDD, DDD)
                EEE = (EEE, EEE)
                GGG = (GGG, GGG)
                ZZZ = (ZZZ, ZZZ)
                """;
        Map map = new Map(List.of(nodes.split("\\n")));
        int steps = map.getSteps("RL", "AAA", "ZZZ");
        assertEquals(2, steps);
    }

    @Test
    void getStepsSample2() {
        String nodes = """
                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ)
                """;
        Map map = new Map(List.of(nodes.split("\\n")));
        int steps = map.getSteps("LLR", "AAA", "ZZZ");
        assertEquals(6, steps);
    }

    @Test
    void getStepsEndingWith() {
        String nodes = """
                11A = (11B, XXX)
                11B = (XXX, 11Z)
                11Z = (11B, XXX)
                22A = (22B, XXX)
                22B = (22C, 22C)
                22C = (22Z, 22Z)
                22Z = (22B, 22B)
                XXX = (XXX, XXX)
                """;
        Map map = new Map(List.of(nodes.split("\\n")));
        long steps = map.getStepsForAllEndingWith("LR", "A", "Z");
        assertEquals(6, steps);
    }
}