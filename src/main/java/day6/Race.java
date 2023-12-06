package main.java.day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Race {

    long duration;
    long record;

    public Race(long duration, long record) {
        this.duration = duration;
        this.record = record;
    }

    public long getNumSolutions() {
        double solution1 = (-this.duration + Math.sqrt(Math.pow(this.duration, 2) - 4 * this.record)) / -2;
        double solution2 = (-this.duration - Math.sqrt(Math.pow(this.duration, 2) - 4 * this.record)) / -2;
        long rangeStart = (long) Math.floor(Math.min(solution1, solution2)) + 1;
        long rangeEnd = (long) Math.ceil(Math.max(solution1, solution2));
        return rangeEnd - rangeStart;
    }

    public static long getNumSolutionsProduct(String times, String recordDistances) {
        Pattern timesPattern = Pattern.compile("Time:\\s+(\\d+(?>\\s+\\d+)*)");
        Matcher timesMatcher = timesPattern.matcher(times);
        if (!timesMatcher.find()) {
            throw new IllegalArgumentException();
        }
        String[] timeStrs = timesMatcher.group(1).split("\\s+");
        Pattern distancePattern = Pattern.compile("Distance:\\s+(\\d+(?>\\s+\\d+)*)");
        Matcher distanceMatcher = distancePattern.matcher(recordDistances);
        if (!distanceMatcher.find()) {
            throw new IllegalArgumentException();
        }
        String[] distanceStrs = distanceMatcher.group(1).split("\\s+");
        long product = 1;
        for (int i = 0; i < timeStrs.length; i++) {
            int duration = Integer.parseInt(timeStrs[i]);
            int record = Integer.parseInt(distanceStrs[i]);
            Race race = new Race(duration, record);
            product *= race.getNumSolutions();
        }
        return product;
    }

    public static long getNumSolutionsProductSingleRace(String times, String recordDistances) {
        Pattern timesPattern = Pattern.compile("Time:\\s+(\\d+(?>\\s+\\d+)*)");
        Matcher timesMatcher = timesPattern.matcher(times);
        if (!timesMatcher.find()) {
            throw new IllegalArgumentException();
        }
        String time = timesMatcher.group(1).replaceAll("\\s+", "");
        Pattern distancePattern = Pattern.compile("Distance:\\s+(\\d+(?>\\s+\\d+)*)");
        Matcher distanceMatcher = distancePattern.matcher(recordDistances);
        if (!distanceMatcher.find()) {
            throw new IllegalArgumentException();
        }
        String distance = distanceMatcher.group(1).replaceAll("\\s+", "");
        Race race = new Race(Long.parseLong(time), Long.parseLong(distance));
        return race.getNumSolutions();
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("inputs/day6_input.txt");
        Scanner sc = new Scanner(input);
        long result = Race.getNumSolutionsProductSingleRace(sc.nextLine(), sc.nextLine());
        System.out.println(result);
    }
}
