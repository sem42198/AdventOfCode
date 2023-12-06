package main.java.day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
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
        this.mappings.sort(Comparator.comparingLong(o -> o[1]));
        ArrayList<long[]> defaultMappings = new ArrayList<>();
        long prevEnd = 0;
        for (long[] mapping: this.mappings) {
            if (mapping[1] > prevEnd) {
                defaultMappings.add(new long[] {prevEnd, prevEnd, mapping[1] - prevEnd});
            }
            prevEnd = mapping[1] + mapping[2];
        }
        defaultMappings.add(new long[] {prevEnd, prevEnd, Long.MAX_VALUE - prevEnd});
        this.mappings.addAll(defaultMappings);
        this.mappings.sort(Comparator.comparingLong(o -> o[1]));

        resources.put(this.name, this);
    }
    private long getMapping(long from) {
        for (long[] mapping: mappings) {
            if (from >= mapping[1] && from < mapping[1] + mapping[2]) {
                return mapping[0] + (from - mapping[1]);
            }
        }
        return from;
    }

    private ArrayList<long[]> getMapping(long[] range, int mappingStartIndex) {
        long rangeStart = range[0];
        long rangeLength = range[1];
        ArrayList<long[]> ranges = new ArrayList<>();
        for (int i = mappingStartIndex; i < this.mappings.size(); i++) {
            long[] mapping = this.mappings.get(i);
            long mappingToStart = mapping[0];
            long mappingRangeStart = mapping[1];
            long mappingRangeLength = mapping[2];
            if (rangeStart >= mappingRangeStart && rangeStart < mappingRangeStart + mappingRangeLength) {
                long mappingTo = mappingToStart + (rangeStart - mappingRangeStart);
                long remainingMappingRange = mappingRangeLength - (rangeStart - mappingRangeStart);
                if (rangeLength <= remainingMappingRange) {
                    ranges.add(new long[] {mappingTo, rangeLength});
                } else {
                    ranges.add(new long[] {mappingTo, remainingMappingRange});
                    long[] remainingRange = {rangeStart + remainingMappingRange, rangeLength - remainingMappingRange};
                    ArrayList<long[]> remainingMapping = this.getMapping(remainingRange, i+ 1);
                    ranges.addAll(remainingMapping);
                }
                break;
            }
        }
        return ranges;
    }

    private ArrayList<long[]> getMapping(List<long[]> ranges) {
        ArrayList<long[]> mappings = new ArrayList<>();
        for (long[] range: ranges) {
            mappings.addAll(this.getMapping(range, 0));
        }
        return mappings;
    }

    private long getResourceTypeMapping(long from, String mapToType) {
        long mapping = this.getMapping(from);
        if (this.mapTo.equals(mapToType)) {
            return mapping;
        } else {
            return Resource.resources.get(this.mapTo).getResourceTypeMapping(mapping, mapToType);
        }
    }

    private ArrayList<long[]> getResourceTypeMapping(List<long[]> from, String mapToType) {
        ArrayList<long[]> mapping = this.getMapping(from);
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
        ArrayList<long[]> seedRanges = new ArrayList<>();
        String[] nums = matcher.group(1).split("\\s+");
        for (int i = 0; i < nums.length; i += 2) {
            long start = Long.parseLong(nums[i]);
            long range = Long.parseLong(nums[i + 1]);
            seedRanges.add(new long[] {start, range});
        }
        Resource seed = Resource.resources.get("seed");
        Long minimum = null;
        ArrayList<long[]> mappings = seed.getResourceTypeMapping(seedRanges, "location");
        for (long[] mapping: mappings) {
            if (minimum == null || mapping[0] < minimum) {
                minimum = mapping[0];
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
