package test.java.day13;

import main.java.day13.Pattern;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PatternTest {

    @Test
    void getTotalLinesOfReflection() {
        String input = """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
                                
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
                """;
        Stream<Pattern> patterns = Arrays.stream(input.split("\n\n")).map(s -> s.split("\n")).map(
                arr -> new Pattern(List.of(arr)));
        int result = Pattern.getTotalLinesOfReflection(patterns.toList());
        assertEquals(405, result);
    }

    @Test
    void getTotalLinesOfReflectionWithSmudge() {
        String input = """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
                                
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
                """;
        Stream<Pattern> patterns = Arrays.stream(input.split("\n\n")).map(s -> s.split("\n")).map(
                arr -> new Pattern(List.of(arr)));
        int result = Pattern.getTotalLinesOfReflectionWithSmudge(patterns.toList());
        assertEquals(400, result);
    }
}