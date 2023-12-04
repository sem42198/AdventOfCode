package main.java.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Must give one input file argument");
        }
        File input = new File(args[0]);
        Scanner sc = new Scanner(input);
        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        Schematic schematic = new Schematic(lines);
        System.out.println(schematic.getGearRatioTotal());
    }
}
