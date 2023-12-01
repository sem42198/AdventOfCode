package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;




public class Day1 {

    private static int getCalibrationValue(String line) {
        int firstIndex = line.length();
        int lastIndex = 0;
        int first = -1;
        int last = -1;
        String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        for (int i = 0; i < 10; i++) {
            char digit = Character.forDigit(i, 10);
            String word = digits[i];

            int firstDigitOccurrence = line.indexOf(digit);
            if (firstDigitOccurrence != -1 && firstDigitOccurrence <= firstIndex) {
                firstIndex = firstDigitOccurrence;
                first = i;
            }

            int lastDigitOccurrence = line.lastIndexOf(digit);
            if (lastDigitOccurrence != -1 && lastDigitOccurrence >= lastIndex) {
                lastIndex = lastDigitOccurrence;
                last = i;
            }

            int firstWordOccurrence = line.indexOf(word);
            if (firstWordOccurrence != -1 && firstWordOccurrence <= firstIndex) {
                firstIndex = firstWordOccurrence;
                first = i;
            }

            int lastWordOccurrence = line.lastIndexOf(word);
            if (lastWordOccurrence != -1 && lastWordOccurrence >= lastIndex) {
                lastIndex = lastWordOccurrence;
                last = i;
            }
        }
        return first * 10 + last;
    }

    public static int getCalibrationValuesTotal(String[] lines) {
        int total = 0;
        for (String line: lines) {
            total += getCalibrationValue(line);
        }
        return total;
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Must give one input file argument");
        }
        File input = new File(args[0]);
        Scanner sc = new Scanner(input);
        int total = 0;
        while (sc.hasNextLine()) {
            total += getCalibrationValue(sc.nextLine());
        }
        System.out.println(total);
    }
}