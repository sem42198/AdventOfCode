package main.java.day5;

import main.java.day4.Card;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resource {
    private static final HashMap<String, Resource> resources = new HashMap<>();
    public final String name;
    public final String mapTo;
    private final ArrayList<long[]> mappings = new ArrayList<>();

    public Resource(String title, Iterable<String> mappings) {
        Pattern titlePattern = Pattern.compile("([a-z]+)-to-([a-z]+)\\s+map:");
        Matcher titleMatcher = titlePattern.matcher(title);
        if (!titleMatcher.find()) throw new IllegalArgumentException();
        this.name = titleMatcher.group(1);
        this.mapTo = titleMatcher.group(2);
        Pattern mappingPattern = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\d+)");
        for (String mapping: mappings) {
            Matcher mappingMatcher = mappingPattern.matcher(mapping);
            if (!mappingMatcher.find()) throw new IllegalArgumentException();
            this.mappings.add(new long[] {Long.parseLong(mappingMatcher.group(1)),
                    Long.parseLong(mappingMatcher.group(2)), Long.parseLong(mappingMatcher.group(3))});
        }
        resources.put(this.name, this);
    }
    private long getMapping(long from) {
        for (long [] mapping: mappings) {
            if (from >= mapping[1] && from < mapping[1] + mapping[2]) {
                return mapping[0] + (from - mapping[1]);
            }
        }
        return from;
    }

    private long getResourceTypeMapping(long from, String mapToType) {
        long mapping = this.getMapping(from);
        if (this.mapTo.equals(mapToType)) {
            return mapping;
        } else {
            return Resource.resources.get(this.mapTo).getResourceTypeMapping(mapping, mapToType);
        }
    }

    public static long lowestLocationNumberForSeeds(String seedsString) {

        Pattern pattern = Pattern.compile("seeds:\\s+((?>\\d*\\s*)+)");
        Matcher matcher = pattern.matcher(seedsString);
        if (!matcher.find()) throw new IllegalArgumentException();
        ArrayList<Long> seeds = new ArrayList<>();
        for (String num: matcher.group(1).split("\\s+")) {
            seeds.add(Long.parseLong(num));
        }
        Resource seed = Resource.resources.get("seed");
        Long minimum = null;
        for (long seedNum: seeds) {
            long val = seed.getResourceTypeMapping(seedNum, "location");
            if (minimum == null || val < minimum) {
                minimum = val;
            }
        }
        return minimum;
    }

    public static long lowestLocationNumberForSeedPairs(String seedsPairsString) {
        Pattern pattern = Pattern.compile("seeds:\\s+((?>\\d*\\s*\\d*\\s*)+)");
        Matcher matcher = pattern.matcher(seedsPairsString);
        if (!matcher.find()) throw new IllegalArgumentException();
        HashSet<Long> seeds = new HashSet<>();
        String[] nums = matcher.group(1).split("\\s+");
        for (int i = 0; i < nums.length; i += 2) {
            long start = Long.parseLong(nums[i]);
            long range = Long.parseLong(nums[i + 1]);
            for (long j = start; j < start + range; j++) {
                seeds.add(j);
            }
        }
        Resource seed = Resource.resources.get("seed");
        Long minimum = null;
        for (long seedNum: seeds) {
            long val = seed.getResourceTypeMapping(seedNum, "location");
            if (minimum == null || val < minimum) {
                minimum = val;
            }
        }
        return minimum;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("inputs/day5_input.txt");
        Scanner sc = new Scanner(input);
        String seedsData = sc.nextLine();
        ArrayList<String> lines = new ArrayList<>();
        String title = null;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                if (title != null) {
                    new Resource(title, lines);
                }
                if (sc.hasNextLine()) {
                    lines = new ArrayList<>();
                    title = sc.nextLine();
                }
            } else {
                lines.add(line);
            }
        }
        new Resource(title, lines);
        System.out.println(Resource.lowestLocationNumberForSeedPairs(seedsData));
    }
}
