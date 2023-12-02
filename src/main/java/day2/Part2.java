package main.java.day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Must give one input file argument");
        }
        File input = new File(args[0]);
        Scanner sc = new Scanner(input);
        int total = 0;
        while (sc.hasNextLine()) {
            Game game = new Game(sc.nextLine());
            total += game.minimumSetPower();
        }
        System.out.println(total);
    }
}
