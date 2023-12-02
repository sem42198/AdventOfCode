package test.java.day2;

import main.java.day2.Game;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void isPossible() {
        String[] games = {
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        };
        int total = 0;
        HashMap<String, Integer> configuration = new HashMap<>();
        configuration.put("red", 12);
        configuration.put("green", 13);
        configuration.put("blue", 14);
        for (String gameStr: games) {
            Game game = new Game(gameStr);
            if (game.isPossible(configuration)) {
                total += game.id;
            }
        }
        assertEquals(8, total);
    }

    @Test
    void minimumSetPower() {
        String[] games = {
                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        };
        int total = 0;
        for (String gameStr: games) {
            Game game = new Game(gameStr);
            total += game.minimumSetPower();
        }
        assertEquals(2286, total);
    }
}