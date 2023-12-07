package test.java.day7;

import main.java.day7.Hand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void getTotalWinnings() {
        String[] data = {"32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483"};
        assertEquals(6440, Hand.getTotalWinnings(List.of(data), false));
    }

    @Test
    void getTotalWinningsWithJoker() {
        String[] data = {"32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483"};
        assertEquals(5905, Hand.getTotalWinnings(List.of(data), true));
    }
}