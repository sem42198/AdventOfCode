package test.java.day5;

import main.java.day5.Resource;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {
    @Test
    void lowestLocationNumberForSeeds() {
        String data = """
                seeds: 79 14 55 13
                                
                seed-to-soil map:
                50 98 2
                52 50 48
                                
                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15
                                
                fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4
                                
                water-to-light map:
                88 18 7
                18 25 70
                                
                light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13
                                
                temperature-to-humidity map:
                0 69 1
                1 0 69
                                
                humidity-to-location map:
                60 56 37
                56 93 4
                """;
        String[] chunks = data.split("\\n\\n");
        String seedsInfo = chunks[0];
        for (int i = 1; i < chunks.length; i++) {
            String chunk = chunks[i];
            String[] lines = chunk.split("\\n");
            new Resource(lines[0], Arrays.stream(lines, 1, lines.length).toList());
        }
        assertEquals(35, Resource.lowestLocationNumberForSeeds(seedsInfo));
    }

    @Test
    void lowestLocationNumberForSeedPairs() {
        String data = """
                seeds: 79 14 55 13
                                
                seed-to-soil map:
                50 98 2
                52 50 48
                                
                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15
                                
                fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4
                                
                water-to-light map:
                88 18 7
                18 25 70
                                
                light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13
                                
                temperature-to-humidity map:
                0 69 1
                1 0 69
                                
                humidity-to-location map:
                60 56 37
                56 93 4
                """;
        String[] chunks = data.split("\\n\\n");
        String seedsInfo = chunks[0];
        for (int i = 1; i < chunks.length; i++) {
            String chunk = chunks[i];
            String[] lines = chunk.split("\\n");
            new Resource(lines[0], Arrays.stream(lines, 1, lines.length).toList());
        }
        assertEquals(46, Resource.lowestLocationNumberForSeedPairs(seedsInfo));
    }
}