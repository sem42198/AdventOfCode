package main.java.day8;

import main.java.day6.Race;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

record Node(String id, String left, String right) {
}

public class Map {
    HashMap<String, Node> nodes = new HashMap<>();

    public Map(Iterable<String> nodeDefs) {
        Pattern p = Pattern.compile("(..[A-Z]) = \\((..[A-Z]), (..[A-Z])\\)");
        for (String nodeDef: nodeDefs) {
            Matcher m = p.matcher(nodeDef);
            if (!m.find()) {
                throw new IllegalArgumentException();
            }
            String id = m.group(1);
            String left = m.group(2);
            String right = m.group(3);
            nodes.put(id, new Node(id, left, right));
        }
    }

    public int getSteps(String instructions, String start, String end) {
        String curr = start;
        int steps = 0;
        while (!curr.endsWith(end)) {
            Node currNode = this.nodes.get(curr);
            char instruction = instructions.charAt(steps % instructions.length());
            if (instruction == 'L') {
                curr = currNode.left();
            } else {
                curr = currNode.right();
            }
            steps++;
        }
        return steps;
    }

    public long getStepsForAllEndingWith(String instructions, String start, String end) {
        List<Integer> vals = this.nodes.keySet().stream().filter(s -> s.endsWith(start)).map(
                s -> this.getSteps(instructions, s, end)).toList();
        // Get LCM
        int max = vals.stream().max(Comparator.comparingInt(o -> o)).get();
        for (long i = max; true; i += max) {
            final long multiple = i;
            if (vals.stream().allMatch(val -> multiple % val == 0)) {
                return multiple;
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("inputs/day8_input.txt");
        Scanner sc = new Scanner(input);
        String instructions = sc.nextLine();
        ArrayList<String> lines = new ArrayList<>();
        sc.nextLine();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        Map map = new Map(lines);
        System.out.println(map.getStepsForAllEndingWith(instructions, "A", "Z"));
    }
}
