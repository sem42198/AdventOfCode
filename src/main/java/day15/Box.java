package main.java.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Box {
    public static final Box[] boxes = new Box[256];
    static {
        for (int i = 0; i < 256; i++) {
            Box.boxes[i] = new Box(i);
        }
    }

    public final int boxNum;
    private final LinkedHashMap<String, Integer> contents = new LinkedHashMap<>();

    public Box(int num) {
        this.boxNum = num;
    }

    public void remove(String label) {
        this.contents.remove(label);
    }

    public void put(String label, int focusingPower) {
        this.contents.put(label, focusingPower);
    }

    public static void doInstruction(String instruction) {
        if (instruction.endsWith("-")) {
            String label = instruction.substring(0, instruction.length() - 1);
            int boxNum = new Steps(label).getHash();
            Box.boxes[boxNum].remove(label);
        } else {
            String[] split = instruction.split("=");
            String label = split[0];
            int focusingPower = Integer.parseInt(split[1]);
            int boxNum = new Steps(label).getHash();
            Box.boxes[boxNum].put(label, focusingPower);
        }
    }

    public static int getFocusingPower(String instructions) {
        for (String instruction: instructions.split(",")) {
            Box.doInstruction(instruction);
        }
        int total = 0;
        for (int boxNum = 0; boxNum < Box.boxes.length; boxNum++) {
            Box box = Box.boxes[boxNum];
            ArrayList<Integer> values = new ArrayList<>(box.contents.sequencedValues());
            for (int position = 0; position < values.size(); position++) {
                int value = values.get(position);
                total += (boxNum + 1) * (position + 1) * value;
            }
        }
        return total;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("inputs/day15_input.txt");
        Scanner sc = new Scanner(input);
        System.out.println(Box.getFocusingPower(sc.nextLine()));
    }
}
