package main.java.day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Must give one input file argument");
        }
        File input = new File(args[0]);
        Scanner sc = new Scanner(input);
        int total = 0;
        HashMap<String, Integer> configuration = new HashMap<>();
        configuration.put("red", 12);
        configuration.put("green", 13);
        configuration.put("blue", 14);
        while (sc.hasNextLine()) {
            Game game = new Game(sc.nextLine());
            if (game.isPossible(configuration)) {
                total += game.id;
            }
        }
        System.out.println(total);
    }
}
