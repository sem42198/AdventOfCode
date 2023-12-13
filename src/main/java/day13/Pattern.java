package main.java.day13;

import main.java.day11.Galaxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pattern {
    private final ArrayList<String> lines = new ArrayList<>();
    public Pattern(List<String> lines) {
        this.lines.addAll(lines);
    }

    public int getHorizontalLineOfReflection(int allowedSmudges) {
        for (int row = 1; row < this.lines.size(); row++) {
            int smudges = 0;
            for (int i = row, j = row - 1; i < this.lines.size() && j >= 0; i++, j--) {
                for (int loc = 0; loc < this.lines.get(i).length(); loc++) {
                    if (this.lines.get(i).charAt(loc) != this.lines.get(j).charAt(loc)) {
                        smudges++;
                        if (smudges > allowedSmudges) {
                            break;
                        }
                    }
                }
                if (smudges > allowedSmudges) {
                    break;
                }
            }
            if (smudges == allowedSmudges) {
                return row;
            }
        }
        return 0;
    }

    public int getVerticalLineOfReflection(int allowedSmudges) {
        for (int col = 1; col < this.lines.get(0).length(); col++) {
            int smudges = 0;
            for (int i = col, j = col - 1; i < this.lines.get(0).length() && j >= 0; i++, j--) {
                for (String line: this.lines) {
                    if (line.charAt(i) != line.charAt(j)) {
                        smudges++;
                        if (smudges > allowedSmudges) {
                            break;
                        }
                    }
                }
                if (smudges > allowedSmudges) {
                    break;
                }
            }
            if (smudges == allowedSmudges) {
                return col;
            }
        }
        return 0;
    }

    public static int getTotalLinesOfReflection(Iterable<Pattern> patterns) {
        int total = 0;
        for (Pattern pattern: patterns) {
            int lor = pattern.getVerticalLineOfReflection(0) + 100 * pattern.getHorizontalLineOfReflection(0);
            total += lor;
        }
        return total;
    }

    public static int getTotalLinesOfReflectionWithSmudge(Iterable<Pattern> patterns) {
        int total = 0;
        for (Pattern pattern: patterns) {
            int lor = pattern.getVerticalLineOfReflection(1) + 100 * pattern.getHorizontalLineOfReflection(1);
            total += lor;
        }
        return total;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("inputs/day13_input.txt");
        Scanner sc = new Scanner(input);
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Pattern> patterns = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                patterns.add(new Pattern(lines));
                lines.clear();
            } else {
                lines.add(line);
            }
        }
        patterns.add(new Pattern(lines));
        System.out.println(Pattern.getTotalLinesOfReflectionWithSmudge(patterns));
    }
}
