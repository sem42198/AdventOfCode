package main.java.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Steps {
    private final String steps;
    public Steps(String steps) {
        this.steps = steps;
    }

    public int getHash() {
        int curr = 0;
        for (int i = 0; i < this.steps.length(); i++) {
            char ch = this.steps.charAt(i);
            curr += ch;
            curr *= 17;
            curr %= 256;
        }
        return curr;
    }

    public static int getTotalValues(String stepSets) {
        int total = 0;
        for (String steps: stepSets.split(",")) {
            total += new Steps(steps).getHash();
        }
        return total;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("inputs/day15_input.txt");
        Scanner sc = new Scanner(input);
        System.out.println(Steps.getTotalValues(sc.nextLine()));
    }
}
