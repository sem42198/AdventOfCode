package main.java.day2;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    public final int id;
    private final ArrayList<HashMap<String, Integer>> colorCounts = new ArrayList<>();

    public Game(String gameDef) {
        String[] splitDef = gameDef.split(": ");
        this.id = Integer.parseInt(splitDef[0].substring(5));
        for (String round : splitDef[1].split("; ")) {
            HashMap<String, Integer> roundMap = new HashMap<>();
            colorCounts.add(roundMap);
            for (String colorDef : round.split(", ")) {
                String[] colorSplit = colorDef.split(" ");
                String color = colorSplit[1];
                int count = Integer.parseInt(colorSplit[0]);
                roundMap.put(color, count);
            }
        }
    }

    public boolean isPossible(HashMap<String, Integer> configuration) {
        for (HashMap<String, Integer> round : this.colorCounts) {
            for (String color : round.keySet()) {
                int needed = round.get(color);
                int available = configuration.getOrDefault(color, 0);
                if (needed > available) {
                    return false;
                }
            }
        }
        return true;
    }

    public int minimumSetPower() {
        HashMap<String, Integer> minimumSet = new HashMap<>();
        for (HashMap<String, Integer> round : this.colorCounts) {
            for (String color : round.keySet()) {
                int needed = round.get(color);
                if (needed > minimumSet.getOrDefault(color, 0)) {
                    minimumSet.put(color, needed);
                }
            }
        }
        int red = minimumSet.getOrDefault("red", 0);
        int green = minimumSet.getOrDefault("green", 0);
        int blue = minimumSet.getOrDefault("blue", 0);
        return red * green * blue;
    }
}
